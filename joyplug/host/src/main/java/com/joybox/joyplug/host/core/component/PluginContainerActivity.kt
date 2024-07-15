package com.joybox.joyplug.host.core.component

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.joybox.joyplug.common.PluginCommonConst
import com.joybox.joyplug.host.core.tokens.PluginActivityToken
import com.joybox.joyplug.host.core.loader.PluginLoader
import com.joybox.joyplug.plugcore.component.JoyBaseActivity
import com.joybox.joyplug.utils.logd

class PluginContainerActivity : ComponentActivity() {

    private var mPluginActivity: JoyBaseActivity? = null
    private lateinit var mPluginActivityToken: PluginActivityToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val targetActivity = intent.getStringExtra(PluginCommonConst.KEY_TARGET_ACTIVITY) ?: return
        val targetPluginPackageName =
            intent.getStringExtra(PluginCommonConst.KEY_TARGET_PLUGIN_PACKAGE_NAME) ?: return
        val targetPluginRecord =
            PluginLoader.getInstance(this).getInstalledPlugin(targetPluginPackageName, false) ?: return

        mPluginActivityToken = PluginActivityToken(
            this,
            targetActivity,
            targetPluginPackageName,
            targetPluginRecord
        )
        val pluginActivity = mPluginActivityToken.setup()
        pluginActivity ?: return
        mPluginActivity = pluginActivity
        mPluginActivity?.onCreate(savedInstanceState)
        logd("[Plugin Activity] onCreate() called - $targetPluginPackageName/$targetActivity")
    }

    override fun onStart() {
        super.onStart()
        mPluginActivity?.onStart()
        logd("[Plugin Activity] onStart() called")
    }
}