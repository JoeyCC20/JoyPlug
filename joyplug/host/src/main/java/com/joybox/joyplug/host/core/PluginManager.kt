package com.joybox.joyplug.host.core

import android.content.Context
import com.joybox.joyplug.host.core.loader.IPluginApkProvider
import com.joybox.joyplug.host.core.loader.PluginLoader
import com.joybox.joyplug.plugcore.service.HostServiceCenter

class PluginManager {
    companion object {
        @Volatile
        private var hasInitiated = false;

        fun init(context: Context, callback : () -> Unit) {
            hasInitiated = true;
            PluginLoader.getInstance(context).start {
                callback.invoke()
            }
        }

        fun addPluginApkProvider(context: Context, pluginDexProvider : IPluginApkProvider) {
            PluginLoader.getInstance(context).addDexProvider(pluginDexProvider)
        }

        fun hasInitiated() : Boolean {
            return hasInitiated
        }

        inline fun <reified T : Any> registerService(t : T) {
            HostServiceCenter.registerService<T>(t)
        }
    }
}