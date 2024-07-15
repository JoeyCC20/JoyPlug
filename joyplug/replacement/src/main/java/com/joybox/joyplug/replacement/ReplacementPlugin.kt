package com.joybox.joyplug.replacement

import com.android.build.api.artifact.ScopedArtifact
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.ScopedArtifacts
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class ReplacementPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("[ReplacementPlugin] applied")

        target.plugins.withType(AppPlugin::class.java) {
            val androidComponents =
                target.extensions.getByType(ApplicationAndroidComponentsExtension::class.java)

            androidComponents.onVariants { variant ->
                if (variant.buildType != "release") {
                    return@onVariants
                }

                val taskProvider = target.tasks.register(
                    "${variant.name}ModifyClasses",
                    ModifyClassesTask::class.java,
                )

                variant.artifacts.forScope(ScopedArtifacts.Scope.ALL)
                    .use(taskProvider)
                    .toTransform(
                        ScopedArtifact.CLASSES,
                        ModifyClassesTask::allJars,
                        ModifyClassesTask::allDirectories,
                        ModifyClassesTask::output
                    )
            }
        }
    }
}