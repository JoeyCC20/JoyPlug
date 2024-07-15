package com.joybox.joyplug.plugcore.component

import android.content.ComponentName
import android.content.Intent

class PluginIntent(val packageName : String, private val targetActivity : String) : Intent() {

    constructor(targetActivity: String) : this(
        targetActivity.substringBeforeLast("."),
        targetActivity
    )

    override fun getComponent(): ComponentName {
        return ComponentName(packageName, targetActivity)
    }
}