package com.joybox.joyplug.plugcore.delegate

import android.annotation.TargetApi
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.CallSuper

interface HostApplicationDelegate {
    fun registerComponentCallbacks(callback: ComponentCallbacks?);

    fun unregisterComponentCallbacks(callback: ComponentCallbacks?);

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun registerOnProvideAssistDataListener(callback: Application.OnProvideAssistDataListener?);

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun unregisterOnProvideAssistDataListener(callback: Application.OnProvideAssistDataListener?);

    @CallSuper
    fun onConfigurationChanged(newConfig: Configuration);

    fun registerActivityLifecycleCallbacks(callback: Application.ActivityLifecycleCallbacks?);

    fun unregisterActivityLifecycleCallbacks(callback: Application.ActivityLifecycleCallbacks?);
}