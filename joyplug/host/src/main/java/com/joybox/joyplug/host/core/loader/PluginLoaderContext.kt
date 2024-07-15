package com.joybox.joyplug.host.core.loader

import android.content.Context
import java.io.File

class PluginLoaderContext(private val context : Context) {

    private val rootDir : String by lazy {
        val path = context.filesDir.absolutePath + File.separator + "joyplug"
        val file = File(path)
        if (!file.exists()) {
            file.mkdir()
        }
        path
    }

    fun getDexRootDir() : String {
        return rootDir
    }

    fun getPluginDir(apkRecord: ApkRecord) : String {
        val pluginDir = File(rootDir, apkRecord.packageName)
        if (!pluginDir.exists()) {
            pluginDir.mkdir()
        }
        return pluginDir.absolutePath
    }

    fun getPluginLibDir(apkRecord: ApkRecord) : String {
        return getPluginDir(apkRecord) + File.separator + "libs"
    }

    fun getPluginDexPath(apkRecord: ApkRecord): String {
        return getPluginDir(apkRecord) + File.separator + apkRecord.version + "_" + apkRecord.priority.ordinal + ".apk"
    }

    fun getPluginAppDir(apkRecord: ApkRecord) : String {
        return getPluginDir(apkRecord) + File.separator + "app"
    }

    fun getPluginDataDir(apkRecord: ApkRecord) : String {
        return getPluginAppDir(apkRecord) + File.separator + "data"
    }

    fun getFilesDir() : File {
        return context.filesDir
    }

    fun getContext() : Context {
        return context
    }
}