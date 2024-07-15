package com.joybox.joyplug.host.core.pool

import com.joybox.joyplug.host.core.tokens.PluginToken
import java.util.Collections

class InstalledPluginPoolImp : InstalledPluginPool {
    private val mPluginPool: MutableMap<String, PluginToken> = Collections.synchronizedMap(mutableMapOf<String, PluginToken>())

    override fun acquire(packageName: String): PluginToken? {
        return mPluginPool[packageName]
    }

    override fun offer(component: PluginToken) {
//        mPluginPool[component] = PluginComponentActivity()
    }
}