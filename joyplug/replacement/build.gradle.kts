plugins {
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm")
    id("maven-publish")
}
apply("${rootProject.projectDir.absoluteFile}/buildconfig/config.gradle")

gradlePlugin {
    plugins {
        create("replacement") {
            group = "com.joybox.joyplug"
            version = "${(project.ext.properties["publishVersion"] as Map<String, Object>)[project.name]}"
            id = "com.joybox.joyplug.replacement"
            implementationClass = "com.joybox.joyplug.replacement.ReplacementPlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            url = uri("${rootProject.rootDir.absoluteFile}/repo")
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:8.1.2")
    implementation("org.ow2.asm:asm:9.2")
    implementation("org.ow2.asm:asm-commons:9.2")
    implementation("org.javassist:javassist:3.27.0-GA")
}
