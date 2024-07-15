package com.joybox.joyplug.replacement.items

import javassist.CtClass

class ReplaceManager {
    private val mReplacedActivity = "com.joybox.joyplug.plugcore.component.JoyBaseActivity"
    private val mReplacedApplication = "com.joybox.joyplug.plugcore.PluginApplication"
    private val mReplacedActivityLifecycleCallbacks = "com.joybox.joyplug.plugcore.PluginApplication\$PluginActivityLifecycleCallbacks"


    private var mReplaceItemMap = mapOf(
        "android.app.Activity" to mReplacedActivity,
        "android.app.Application" to mReplacedApplication,
        "android.app.Application\$ActivityLifecycleCallbacks" to mReplacedActivityLifecycleCallbacks
    )

    fun acceptable(className : String) : Boolean {
        return !className.startsWith("META-INF/")
                && className.endsWith(".class")
                && !className.contains("com/joybox/joyplug/plugcore")
    }

    fun performReplace(ctClass : CtClass) {
        mReplaceItemMap.entries.forEach { entry ->
            ctClass.replaceClassName(entry.key, entry.value)
        }
    }

}