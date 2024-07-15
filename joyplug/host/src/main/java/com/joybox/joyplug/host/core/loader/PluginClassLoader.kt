package com.joybox.joyplug.host.core.loader

import dalvik.system.BaseDexClassLoader
import java.io.File

/**
 * Class loader for plugin loading
 */
class PluginClassLoader(
    dexPath: String?,
    optimizedDirectory: File?, librarySearchPath: String?, parent: ClassLoader?,
) : BaseDexClassLoader(dexPath, optimizedDirectory, librarySearchPath, parent) {
    private val mCommonPackages = "com.joybox.joyplug.services"

    override fun loadClass(name: String?, resolve : Boolean): Class<*> {
        // First, check if the class has already been loaded
        var c = findLoadedClass(name)
        if (c == null) {
            var exceptions : Throwable? = null

            // Case 1:
            // For common classes e.g. a variety of 'HostService' has to be loaded by both Host and Plugin,
            // hence they have to be loaded by super classloader, in order to share them among different classloaders
            if (name != null && name.startsWith(mCommonPackages)) {
                return super.loadClass(name, resolve)
            }

            // Case 2:
            // For system class whose super class is 'Activity', it's super class has been replaced by JoyBaseActivity,
            // e.g. 'ComponentActivity', hence PluginClassLoader should look for it in Plugin Dex first, in order
            // to return a customized ComponentActivity, instead of an origin one
            try {
                c = findClass(name)
            } catch (e : ClassNotFoundException) {
                exceptions = e
            }

            if (c == null) {
                try {
                    return super.loadClass(name, resolve)
                } catch (e : ClassNotFoundException) {
                    exceptions?.let { e.addSuppressed(it) }
                    throw e;
                }
            }
        }
        return c
    }

    private fun String.packageName() : String = substringBeforeLast("")
}