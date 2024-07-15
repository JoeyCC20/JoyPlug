package com.joybox.joyplug.host.core.delegate

import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import com.joybox.joyplug.plugcore.delegate.HostApplicationDelegate

class DelegatedHostApplication(var hostApplication: Application) : HostApplicationDelegate {

    override fun onConfigurationChanged(newConfig: Configuration) {
        hostApplication.onConfigurationChanged(newConfig)
    }

    override fun registerActivityLifecycleCallbacks(callback: Application.ActivityLifecycleCallbacks?) {
        hostApplication.registerActivityLifecycleCallbacks(callback)
    }

    override fun registerComponentCallbacks(callback: ComponentCallbacks?) {
        hostApplication.registerComponentCallbacks(callback)
    }

    override fun registerOnProvideAssistDataListener(callback: Application.OnProvideAssistDataListener?) {
        hostApplication.registerOnProvideAssistDataListener(callback)
    }

    override fun unregisterActivityLifecycleCallbacks(callback: Application.ActivityLifecycleCallbacks?) {
        hostApplication.unregisterActivityLifecycleCallbacks(callback)
    }

    override fun unregisterComponentCallbacks(callback: ComponentCallbacks?) {
        hostApplication.unregisterComponentCallbacks(callback)
    }

    override fun unregisterOnProvideAssistDataListener(callback: Application.OnProvideAssistDataListener?) {
        hostApplication.unregisterOnProvideAssistDataListener(callback)
    }
}