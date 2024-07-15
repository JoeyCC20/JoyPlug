package com.joybox.joyplug.host_sample

import android.app.Application
import android.util.Log
import com.joybox.joyplug.host.core.PluginManager
import com.joybox.joyplug.host.core.loader.ApkProvideNotification
import com.joybox.joyplug.host.core.loader.ApkRecord
import com.joybox.joyplug.host.core.loader.IPluginApkProvider
import com.joybox.joyplug.host.core.loader.PluginPriority
import com.joybox.joyplug.services.NetService
import org.json.JSONArray
import java.io.File
import java.io.FileOutputStream
import java.net.URL

/**
 * Application for Host Sample
 */
class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Step 1: add a apk provider, which provides some plugin apks that 'Host' will
        // install and load later. When apk is ready, build a 'ApkRecord' and call 'notifyApkReady()'
        PluginManager.addPluginApkProvider(applicationContext, object :
            IPluginApkProvider {
            override fun onGetAsync(notification: ApkProvideNotification) {
                // Activity Plugin Sample Apk
                provideApk("activity_plugin.apk") { apkPath ->
                    apkPath?.let {
                        val apkRecord = ApkRecord(
                            "com.joybox.joyplug.activity_plugin_sample",
                            1,
                            PluginPriority.HIGH,
                            it,
                            "",
                            System.currentTimeMillis()
                        )

                        notification.notifyApkReady(apkRecord)
                    }
                }

                // View Plugin Sample Apk
                provideApk("view_plugin.apk") { apkPath ->
                    apkPath?.let {
                        val apkRecord = ApkRecord(
                            "com.joybox.joyplug.view_plugin_sample",
                            1,
                            PluginPriority.HIGH,
                            it,
                            "",
                            System.currentTimeMillis()
                        )
                        notification.notifyApkReady(apkRecord)
                    }
                }

                // View (RecycleList) Plugin Sample Apk
                provideApk("view_plugin2.apk") { apkPath ->
                    apkPath?.let {
                        val apkRecord = ApkRecord(
                            "com.joybox.joyplug.view_plugin_sample2",
                            1,
                            PluginPriority.HIGH,
                            it,
                            "",
                            System.currentTimeMillis()
                        )
                        notification.notifyApkReady(apkRecord)
                    }
                }
            }
        })

        PluginManager.init(applicationContext) {
            Log.i("PluginManager", "PluginManager init done")
        }

        PluginManager.registerService<NetService>(object : NetService {
            override fun GET(url: URL): String {
                Log.i("Service", "[NetService] GET() called.")
                return "success"
            }

            override fun POST(url: URL, params: JSONArray): String {
                Log.i("Service", "[NetService] POST() called.")
                return "success"
            }

        })
    }

    /**
     * Copy apk file from assets
     * It's a simulation of apk's download
     */
    fun provideApk(apkName : String, callback : (String?) -> Unit) {
        try {
            val destPath = "${applicationContext?.filesDir}/$apkName"
            val destFile = File(destPath)
            if (destFile.exists()) {
                callback.invoke(null)
            }

            val inputStream = applicationContext.assets.open(apkName);
            inputStream.use {input ->
                FileOutputStream(destFile).use { output ->
                    val inBuffer = input.buffered()
                    val outBuffer = output.buffered()
                    inBuffer.copyTo(outBuffer)
                    outBuffer.flush()
                }
            }
            callback.invoke(destPath)
            return
        } catch (e : RuntimeException) {
            e.printStackTrace()
        }
        callback.invoke(null)
    }
}