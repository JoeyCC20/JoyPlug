package com.joybox.joyplug.host.core.loader

interface ApkProvideNotification {
    fun notifyApkReady(apkRecord: ApkRecord)
    fun notifyDexBatchReady(apkRecordList : List<ApkRecord>)
}