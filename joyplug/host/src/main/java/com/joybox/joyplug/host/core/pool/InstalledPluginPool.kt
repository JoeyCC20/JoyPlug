package com.joybox.joyplug.host.core.pool

import com.joybox.joyplug.host.core.tokens.PluginToken

interface InstalledPluginPool {
    fun acquire(packageName : String) : PluginToken?
    fun offer(component: PluginToken)
}