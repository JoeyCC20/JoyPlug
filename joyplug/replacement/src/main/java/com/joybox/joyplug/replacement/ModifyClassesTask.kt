package com.joybox.joyplug.replacement

import com.android.build.gradle.AppExtension
import com.joybox.joyplug.replacement.items.ReplaceManager
import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import javassist.CtClass
import java.io.InputStream
import java.io.FileOutputStream
import java.io.BufferedOutputStream
import java.io.File
import java.util.jar.JarFile
import java.util.jar.JarEntry
import java.util.jar.JarOutputStream
import org.gradle.api.file.RegularFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.file.RegularFileProperty
import javax.inject.Inject

abstract class ModifyClassesTask: DefaultTask() {
    @get:InputFiles
    abstract val allJars: ListProperty<RegularFile>

    @get:InputFiles
    abstract val allDirectories: ListProperty<Directory>

    @get:OutputFile
    abstract val output: RegularFileProperty

    @Internal
    val jarPaths = mutableSetOf<String>()

    @TaskAction
    fun taskAction() {
        val appExtension = project.extensions.getByType(AppExtension::class.java)
        val classPool = MergedClassPool(appExtension)
        val jarOutput = JarOutputStream(BufferedOutputStream(FileOutputStream(
            output.get().asFile
        )))

        val replaceManager = ReplaceManager()
        allJars.get().forEach { file ->
            val jarFile = JarFile(file.asFile)
            jarFile.entries().iterator().forEach { jarEntry ->
                if (replaceManager.acceptable(jarEntry.name)) {
                    val ctClass : CtClass = jarFile.getInputStream(jarEntry).use {
                        classPool.makeClass(it)
                    }
                    ctClass.defrost()
                    replaceManager.performReplace(ctClass)

                    jarOutput.writeEntity(jarEntry.name, ctClass.toBytecode())
                } else {
                    jarOutput.writeEntity(jarEntry.name, jarFile.getInputStream(jarEntry))
                }
            }
            jarFile.close()
        }

        allDirectories.get().forEach { directory ->
            directory.asFile.walk().forEach { file ->
                if (file.isFile) {
                    println("replacement file: ${file.name}")
                    if (replaceManager.acceptable(file.name)) {
                        val ctClass = file.inputStream().use {
                            classPool.makeClass(it);
                        }
                        ctClass.defrost()
                        replaceManager.performReplace(ctClass)

                        val relativePath = directory.asFile.toURI().relativize(file.toURI()).path
                        jarOutput.writeEntity(relativePath.replace(File.separatorChar, '/'), ctClass.toBytecode())
                    } else {
                        val relativePath = directory.asFile.toURI().relativize(file.toURI()).path
                        jarOutput.writeEntity(relativePath.replace(File.separatorChar, '/'), file.inputStream())
                    }
                }
            }
        }
        jarOutput.close()
    }

    private fun JarOutputStream.writeEntity(name: String, inputStream: InputStream) {
        if (jarPaths.contains(name)) {
            printDuplicatedMessage(name)
        } else {
            putNextEntry(JarEntry(name))
            inputStream.copyTo(this)
            closeEntry()
            jarPaths.add(name)
        }
    }

    private fun JarOutputStream.writeEntity(relativePath: String, byteArray: ByteArray) {
        if (jarPaths.contains(relativePath)) {
            printDuplicatedMessage(relativePath)
        } else {
            putNextEntry(JarEntry(relativePath))
            write(byteArray)
            closeEntry()
            jarPaths.add(relativePath)
        }
    }

    private fun printDuplicatedMessage(name: String) =
        println("Cannot add ${name}, because output Jar already has file with the same name.")
}
