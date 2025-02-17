package com.joybox.joyplug.host.core

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.joybox.joyplug.common.PluginCommonConst
import com.joybox.joyplug.host.core.component.PluginContainerActivity
import com.joybox.joyplug.host.core.loader.PluginLoader

class PluginRouter {
    companion object {
        fun startActivity(context: Context, packageName : String, targetActivity : String, extras : Bundle? = null) {
            if (!PluginManager.hasInitiated()) {
                throw Exception("PluginManager has not been initiated!")
            }

            PluginLoader.getInstance(context).acquirePlugin(packageName) { pluginRecord ->
                pluginRecord ?: return@acquirePlugin

                val intent = Intent(context, PluginContainerActivity::class.java)
                extras?.let {
                    intent.putExtras(extras)
                }
                intent.putExtra(PluginCommonConst.KEY_TARGET_PLUGIN_PACKAGE_NAME, packageName)
                intent.putExtra(PluginCommonConst.KEY_TARGET_ACTIVITY, targetActivity)
                context.startActivity(intent)
            }
        }
    }
}