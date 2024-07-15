package com.joybox.joyplug.host.core.tokens

import android.content.Context
import com.joybox.joyplug.host.core.PluginRecord
import com.joybox.joyplug.plugcore.JoyContext
import com.joybox.joyplug.plugcore.component.JoyBaseView

class PluginViewToken(
    mHostContext: Context,
    mPackageName: String,
    private var targetView: String,
    mPluginRecord: PluginRecord
) :
    PluginToken(mHostContext, mPackageName, mPluginRecord) {

    override fun setup(): JoyBaseView? {
        val joyContext = JoyContext(mHostContext, mPluginRecord.mApplicationInfo.theme);
        joyContext.setPluginClassLoader(mPluginRecord.mClassLoader)
        joyContext.setPluginResource(mPluginRecord.mResources) //
        joyContext.setApplicationInfo(mPluginRecord.mApplicationInfo)
        joyContext.packageName = mPluginRecord.mPackageName

        try {
            val pluginClazz : Class<*> = mPluginRecord.mClassLoader.loadClass(targetView)
            val pluginView =
                pluginClazz.getConstructor(Context::class.java).newInstance(joyContext) as JoyBaseView
            return pluginView
        } catch (e : Exception) {
            e.printStackTrace()
        }
        return null
    }
}