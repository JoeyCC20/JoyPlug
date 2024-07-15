package com.joybox.joyplug.replacement

import com.android.build.gradle.AppExtension
import javassist.ClassPool
import javassist.LoaderClassPath
import java.io.File

class MergedClassPool(application : AppExtension) : ClassPool() {
    init {
        appendClassPath(
            LoaderClassPath(Thread.currentThread().contextClassLoader)
        )
        val androidJarPath = "${application.sdkDirectory}\\platforms\\${application.compileSdkVersion}\\android.jar"
        if (!File(androidJarPath).exists()) {
            println("[MergedClassPool] android.jar not found!! path:$androidJarPath")
        }
        appendClassPath(androidJarPath)

    }
}