package com.joybox.joyplug.plugcore.service

import java.util.Collections

class HostServiceCenter {
    companion object {
        @PublishedApi
        internal var mCacheMap = Collections.synchronizedMap(mutableMapOf<String, Any?>())

        inline fun <reified T : Any> getService() : T? {
            try {
                android.util.Log.i("Service", "--getService ${T::class.java.name}")
                return mCacheMap[T::class.java.name] as T?
            } catch (e : Throwable) {
                e.printStackTrace()
            }
            return null
        }

        inline fun <reified T : Any> registerService(t : T) {
            try {
                android.util.Log.i("Service", "--registerService ${T::class.java.name}")
                mCacheMap[T::class.java.name] = t
            } catch (e : Throwable) {
                e.printStackTrace()
            }
        }
    }
}