package com.joybox.joyplug.utils

import android.os.Handler
import android.os.Looper

fun runInUIThread(block : () -> Unit) {
    Handler(Looper.getMainLooper()).post(block)
}