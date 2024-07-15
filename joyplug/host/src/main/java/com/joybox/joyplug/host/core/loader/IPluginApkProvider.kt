package com.joybox.joyplug.host.core.loader

interface IPluginApkProvider {
    fun onGetAsync(notification: ApkProvideNotification)
}