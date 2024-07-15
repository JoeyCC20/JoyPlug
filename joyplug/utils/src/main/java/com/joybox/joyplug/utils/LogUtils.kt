package com.joybox.joyplug.utils

val TAG = "[JOYPLUG]"

@JvmName(name = "LogD")
fun logd(msg : String) {
    val stackTraceElement = Thread.currentThread().stackTrace[1]
    android.util.Log.d(TAG, "[${stackTraceElement.methodName}] $msg")
}

@JvmName(name = "LogI")
fun logi(msg : String) {
    val stackTraceElement = Thread.currentThread().stackTrace[1]
    android.util.Log.i(TAG, "[${stackTraceElement.methodName}] $msg")
}

@JvmName(name = "LogW")
fun logw(msg : String) {
    val stackTraceElement = Thread.currentThread().stackTrace[1]
    android.util.Log.w(TAG, "[${stackTraceElement.methodName}] $msg")
}

@JvmName(name = "LogE")
fun loge(msg : String) {
    val stackTraceElement = Thread.currentThread().stackTrace[1]
    android.util.Log.e(TAG, "[${stackTraceElement.methodName}] $msg")
}