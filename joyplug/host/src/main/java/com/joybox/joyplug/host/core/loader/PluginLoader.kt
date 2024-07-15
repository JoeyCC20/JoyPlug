package com.joybox.joyplug.host.core.loader

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import com.joybox.joyplug.host.core.PluginRecord
import com.joybox.joyplug.host.core.loader.dao.InstalledPluginsDataBase
import com.joybox.joyplug.host.core.loader.dao.PluginDao
import com.joybox.joyplug.host.core.loader.dao.PluginEntity
import com.joybox.joyplug.utils.logd
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.ref.WeakReference
import java.util.Collections
import java.util.StringTokenizer
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

class PluginLoader(context : Context) {
    private val pluginLoaderContext: PluginLoaderContext by lazy { PluginLoaderContext(context) }

    companion object {
        @Volatile
        private var INSTANCE : PluginLoader? = null

        fun getInstance(context : Context) : PluginLoader {
            return INSTANCE ?: synchronized(this) {
                val instance = PluginLoader(context)
                INSTANCE = instance
                return instance
            }
        }
    }

    private var mProviderContainer = mutableListOf<WeakReference<IPluginApkProvider>>()
    private val mTaskQueueHigh = LinkedBlockingQueue<ApkRecord>(20)
    private val mTaskQueueMedium = LinkedBlockingQueue<ApkRecord>(20)
    private val mTaskQueueLow = LinkedBlockingQueue<ApkRecord>(20)
    private val mExecutor = Executors.newFixedThreadPool(2)

    private var handlerThread : HandlerThread = HandlerThread("PluginLoader")
    private var workHandler : Handler

    init {
        handlerThread.start()
        workHandler = Handler(handlerThread.looper)
    }

    private val mInstalledPluginMap = Collections.synchronizedMap(mutableMapOf<String, PluginRecord>())
    private lateinit var mLoadStateListener : () -> Unit

    private var mApkProvideNotification = object : ApkProvideNotification {
        override fun notifyApkReady(apkRecord: ApkRecord) {
            when (apkRecord.priority) {
                PluginPriority.HIGH -> mTaskQueueHigh.offer(apkRecord)
                PluginPriority.MEDIUM -> mTaskQueueMedium.offer(apkRecord)
                PluginPriority.LOW -> mTaskQueueLow.offer(apkRecord)
            }
            processRawDexTask()
        }

        override fun notifyDexBatchReady(apkRecordList: List<ApkRecord>) {
            for (dexRecord in apkRecordList) {
                when (dexRecord.priority) {
                    PluginPriority.HIGH -> mTaskQueueHigh.offer(dexRecord)
                    PluginPriority.MEDIUM -> mTaskQueueMedium.offer(dexRecord)
                    PluginPriority.LOW -> mTaskQueueLow.offer(dexRecord)
                }
            }
            processRawDexTask()
        }
    }

    private val mPluginDao: PluginDao by lazy {
        InstalledPluginsDataBase.getDatabase(
            pluginLoaderContext.getContext()
        ).pluginDao()
    }

    /********** public ***********/

    fun addDexProvider(dexProvider: IPluginApkProvider) : PluginLoader {
        mProviderContainer.add(WeakReference(dexProvider))
        return this
    }

    fun start(onLoaded : () -> Unit) {
        mLoadStateListener = onLoaded
        for (dexProviderRef in mProviderContainer) {
            dexProviderRef.get()?.onGetAsync(mApkProvideNotification)
        }
        loadAllInstalled()
    }

    fun acquirePlugin(pluginPackageName: String, result: (PluginRecord?) -> Unit) {
        val pluginRecord = mInstalledPluginMap[pluginPackageName]

        if (pluginRecord != null) {
            result.invoke(pluginRecord)
        } else {
            mExecutor.submit {
                result.invoke(loadFromDex(pluginPackageName))
            }
        }
    }

    fun getInstalledPlugin(packageName : String, useDB : Boolean = true) : PluginRecord? {
        val cachePluginRecord = mInstalledPluginMap[packageName]
        if (cachePluginRecord != null) {
            return cachePluginRecord
        }
        if (useDB) {
            val dbPluginEntity = mPluginDao.findById(packageName)
            if (dbPluginEntity != null) {
                val pluginRecord = PluginRecord(dbPluginEntity)
                pluginRecord.setup(pluginLoaderContext.getContext())
                return pluginRecord
            }
        }
        return null
    }

    /********** private ***********/
//    inner class WorkHandler(looper: Looper) : Handler(looper) {
//        val MSG_PRELO
//
//        override fun handleMessage(msg: Message) {
//            super.handleMessage(msg)
//
//        }
//    }

    private fun loadFromDex(pluginPackageName: String) : PluginRecord? {
        try {
            getInstalledPlugin(pluginPackageName)?.let { return it }

            File(pluginLoaderContext.getDexRootDir()).listFiles()?.forEach { pluginDir ->
                if (pluginDir.name == pluginPackageName) {
                    try {
                        val fileList = pluginDir.listFiles()
                        if (fileList == null || fileList.isEmpty()) {
                            return null
                        }
                        fileList.sortByDescending { it.name }
                        val dexFile = fileList.first { it.isFile && it.name.endsWith(".apk") }
                        // file name format: 1_0.apk
                        val tokenizer = StringTokenizer(dexFile.name.substringBeforeLast("."), "_")
                        val version = tokenizer.nextToken().toInt()
                        val priority = tokenizer.nextToken().toInt()

                        val apkRecord = ApkRecord(
                            pluginPackageName,
                            version,
                            PluginPriority.entries[priority],
                            dexFile.absolutePath,
                            "", // TODO should read from a manifest, which is attached to corresponding plugin apk
                            System.currentTimeMillis()
                        )
                        installDex(apkRecord)?.let { pluginRecord ->
                            mPluginDao.insert(pluginRecord)
                            pluginRecord.setup(pluginLoaderContext.getContext())
                            mInstalledPluginMap[pluginRecord.mPackageName] = pluginRecord
                            return pluginRecord
                        } ?: run { return null}

                    } catch (e : Throwable) {
                        e.printStackTrace()
                    }
                }
            }
        } catch (e : Throwable) {
            e.printStackTrace()
        }

        return null
    }

    private fun processRawDexTask() {
        mExecutor.submit {
            while (!mTaskQueueHigh.isEmpty()) {
                installDex(mTaskQueueHigh.poll())
            }
            while (!mTaskQueueMedium.isEmpty()) {
                installDex(mTaskQueueMedium.poll())
            }
            while (!mTaskQueueLow.isEmpty()) {
                installDex(mTaskQueueLow.poll())
            }
            loadAllInstalled()
        }
    }

    private fun installDex(apkRecord: ApkRecord?) : PluginRecord? {
        apkRecord ?: run {
            return null
        }
        val pluginDexPath = pluginLoaderContext.getPluginDexPath(apkRecord)
        val pluginDexFile = File(pluginDexPath)
        if (!pluginDexFile.exists()) {
            FileInputStream(apkRecord.dexPath).use { input ->
                FileOutputStream(pluginDexPath).use { output ->
                    val inBuffer = input.buffered()
                    val outBuffer = output.buffered()
                    inBuffer.copyTo(outBuffer)
                    outBuffer.flush()
                }
            }
            pluginDexFile.setWritable(false)
        }
        val pluginRecord = PluginRecord()
        pluginRecord.mVersion = apkRecord.version
        pluginRecord.mPackageName = apkRecord.packageName
        pluginRecord.mPriority = apkRecord.priority.ordinal
        pluginRecord.mApkDir = pluginDexPath
        pluginRecord.mBaseDir = pluginLoaderContext.getPluginDir(apkRecord)
        pluginRecord.mLibDir = pluginLoaderContext.getPluginLibDir(apkRecord)
        pluginRecord.mOdexDir = pluginRecord.mBaseDir
        pluginRecord.mAppDir = pluginLoaderContext.getPluginAppDir(apkRecord)
        pluginRecord.mDataDir = pluginLoaderContext.getPluginDataDir(apkRecord)
        pluginRecord.mEntryClazz = apkRecord.entryClazz
        pluginRecord.mTimestamp = apkRecord.timestamp

        try {
            mPluginDao.insert(pluginRecord)
        } catch (e : Throwable) {
            e.printStackTrace()
        }

        return pluginRecord
    }

    private fun loadAllInstalled() {
        mExecutor.submit {
            val pluginEntityList : List<PluginEntity> = mPluginDao.findAll()
            for (pluginEntity in pluginEntityList) {
                val dbPluginRecord = mInstalledPluginMap[pluginEntity.mPackageName]
                if (dbPluginRecord != null && dbPluginRecord.mVersion >= pluginEntity.mVersion) {
                    continue
                }
                val pluginRecord = PluginRecord()
                pluginRecord.cloneFrom(pluginEntity)
                pluginRecord.setup(pluginLoaderContext.getContext())
                mInstalledPluginMap[pluginRecord.mPackageName] = pluginRecord
            }
            mLoadStateListener.invoke()
        }
    }
}

fun PluginRecord.setup(context : Context) {
    val packageManager = context.packageManager
    val packageInfo = packageManager.getPackageArchiveInfo(mApkDir, 0)
    packageInfo ?: return

    packageInfo.applicationInfo?.publicSourceDir = mApkDir
    packageInfo.applicationInfo.sourceDir = mApkDir;
    packageInfo.applicationInfo?.let {
        mResources = packageManager.getResourcesForApplication(it)
    }
    mPackageName = packageInfo.packageName
    mDataDir = context.applicationInfo?.dataDir + File.separator + packageInfo.packageName
    val applicationInfo = ApplicationInfo(context.applicationInfo)
    applicationInfo.sourceDir = mApkDir
    applicationInfo.nativeLibraryDir = mLibDir
    applicationInfo.dataDir = mDataDir
    applicationInfo.packageName = packageInfo.packageName
    applicationInfo.theme = packageInfo.applicationInfo?.theme!!
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        applicationInfo.appComponentFactory = packageInfo.applicationInfo?.appComponentFactory
    }
    mApplicationInfo = applicationInfo
    try {
        mClassLoader = PluginClassLoader(mApkDir, File(mOdexDir), mLibDir, this::class.java.classLoader)
    } catch (e : Throwable) {
        e.printStackTrace()
    }

    logd("PluginClassLoader done")
}