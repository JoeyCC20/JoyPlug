package com.joybox.joyplug.host.core.tokens

import android.app.Activity
import android.app.Application
import com.joybox.joyplug.host.core.delegate.DelegatedHostActivity
import com.joybox.joyplug.host.core.delegate.DelegatedHostApplication
import com.joybox.joyplug.host.core.PluginRecord
import com.joybox.joyplug.plugcore.component.JoyBaseActivity
import com.joybox.joyplug.plugcore.JoyContext
import com.joybox.joyplug.plugcore.PluginApplication

class PluginActivityToken(
    private val hostActivity: Activity,
    private val targetActivity: String,
    private val packageName: String,
    private val pluginRecord: PluginRecord,
) : PluginToken(hostActivity, packageName, pluginRecord) {

    private val mClazzPluginApplication = "com.joybox.joyplug.plugcore.PluginApplication"

    override fun setup() : JoyBaseActivity? {

        val joyContext = JoyContext(hostActivity, mPluginRecord.mApplicationInfo.theme); // theme TODO
        joyContext.setPluginClassLoader(mPluginRecord.mClassLoader)
        joyContext.setPluginResource(mPluginRecord.mResources) //
        joyContext.setApplicationInfo(mPluginRecord.mApplicationInfo)
        joyContext.packageName = mPluginRecord.mPackageName

        try {
            val applicationClazz = mPluginRecord.mClassLoader.loadClass(mClazzPluginApplication)
            val pluginApplication = applicationClazz?.getConstructor()?.newInstance() as PluginApplication
            pluginApplication.attachPluginContext(joyContext)
            pluginApplication.setHostApplicationDelegate(DelegatedHostApplication(hostActivity.applicationContext as Application))

            val pluginClazz : Class<*> = mPluginRecord.mClassLoader.loadClass(targetActivity)

            val pluginActivity = pluginClazz.getConstructor().newInstance() as JoyBaseActivity
            pluginActivity.setHostActivityDelegate(DelegatedHostActivity(hostActivity))
            pluginActivity.attachPluginContext(joyContext)
            pluginActivity.setPluginApplication(pluginApplication)
            return pluginActivity
        } catch (e : Exception) {
            e.printStackTrace()
        }
        return null
    }

}