package com.joybox.joyplug.plugcore

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.res.Resources
import android.content.res.Resources.Theme
import android.os.Build
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import com.joybox.joyplug.common.PluginCommonConst

open class JoyContext(base: Context?, private var themeResId: Int) : ContextThemeWrapper(base, themeResId) {
    private lateinit var mPluginResource : Resources
    private lateinit var mLayoutInflater : LayoutInflater
    private lateinit var mApplicationInfo : ApplicationInfo
    private lateinit var mPluginClassLoader : ClassLoader
    private lateinit var mPluginApplication : PluginApplication
    private lateinit var mPackageName : String

    init {
        if (themeResId != 0) {
            super.setTheme(themeResId)
        }
//        else {
//            if (Build.VERSION.SDK_INT >= 29) {
//                theme = baseContext?.theme
//            }
//        }
    }

    fun cloneFrom(joyContext: JoyContext) {
        mPluginResource = joyContext.mPluginResource
        mApplicationInfo = joyContext.mApplicationInfo
        mPluginClassLoader = joyContext.mPluginClassLoader
        packageName = joyContext.packageName
        super.attachBaseContext(joyContext.baseContext)
        if (themeResId != 0) {
            super.setTheme(themeResId)
        }
        if (joyContext.themeResId != 0) {
            super.setTheme(joyContext.themeResId)
        }
    }

    fun setPluginResource(resources: Resources) : JoyContext {
        mPluginResource = resources;
        return this
    }

    fun setApplicationInfo(applicationInfo: ApplicationInfo) : JoyContext {
        mApplicationInfo = ApplicationInfo(applicationInfo)
        return this
    }

    fun setPluginClassLoader(classLoader: ClassLoader) : JoyContext {
        mPluginClassLoader = classLoader
        return this
    }

    fun setPackageName(packageName : String) {
        mPackageName = packageName
    }

    fun getPluginApplication() : PluginApplication {
        return mPluginApplication
    }

    fun setPluginApplication(pluginApplication: PluginApplication) : JoyContext {
        mPluginApplication = pluginApplication
        return this
    }

    private var joyLayoutInflater : PluginLayoutInflater? = null

    override fun getSystemService(name: String): Any? {
        if (LAYOUT_INFLATER_SERVICE == name) {
            return if (joyLayoutInflater == null) {
                val origin = super.getSystemService(name) as LayoutInflater
                // 中转一下，是因为setFactory2只能设置一次，后期Fragment会需要设置Factory，因此此时只能通过构造方法来设置
                val mergeLayoutInflater = MergeLayoutInflater(origin, this)
                mergeLayoutInflater.setFactory2(PluginFactory2(this))
                joyLayoutInflater = PluginLayoutInflater(mergeLayoutInflater, this)
                joyLayoutInflater
            } else {
                joyLayoutInflater
            }

        }
        return super.getSystemService(name)
    }

    class MergeLayoutInflater(origin : LayoutInflater, context: Context?) : LayoutInflater(origin, context) {
        override fun cloneInContext(newContext: Context?): LayoutInflater {
            return MergeLayoutInflater(this, newContext)
        }
    }

    override fun getResources(): Resources {
        return mPluginResource
    }

    override fun getApplicationContext(): Context {
        return mPluginApplication
    }

    override fun getClassLoader(): ClassLoader {
        return mPluginClassLoader
    }

    override fun getApplicationInfo(): ApplicationInfo {
        return mApplicationInfo
    }

    override fun getPackageName(): String {
        return mPackageName
    }

    override fun onApplyThemeResource(theme: Theme?, resId: Int, first: Boolean) {
        super.onApplyThemeResource(theme, resId, first)
    }

    override fun startActivity(intent: Intent?) {
        intent ?: return
        val newIntent = Intent(baseContext, baseContext.classLoader.loadClass(PluginCommonConst.HOST_CONTAINER_ACTIVITY))
        newIntent.replaceExtras(intent.extras)
        newIntent.putExtra(PluginCommonConst.KEY_TARGET_PLUGIN_PACKAGE_NAME, intent.component?.packageName)
        newIntent.putExtra(PluginCommonConst.KEY_TARGET_ACTIVITY, intent.component?.className)
        super.startActivity(newIntent)
    }

    override fun startActivity(intent: Intent?, options: Bundle?) {
        intent ?: return
        val newIntent = Intent(baseContext, baseContext.classLoader.loadClass(PluginCommonConst.HOST_CONTAINER_ACTIVITY))
        intent.extras?.let { newIntent.putExtras(it) }
        newIntent.putExtra(PluginCommonConst.KEY_TARGET_PLUGIN_PACKAGE_NAME, intent.component?.packageName)
        newIntent.putExtra(PluginCommonConst.KEY_TARGET_ACTIVITY, intent.component?.className)
        super.startActivity(newIntent, options)
    }
}