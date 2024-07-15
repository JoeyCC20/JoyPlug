package com.joybox.joyplug.host.core.tokens

import android.content.Context
import com.joybox.joyplug.host.core.PluginRecord

abstract class PluginToken(var mHostContext : Context, var mPackageName : String, var mPluginRecord : PluginRecord) {
    var mComponentType : ComponentType = ComponentType.ACTIVITY

    abstract fun setup() : Any?
}