pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("./repo")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("./repo")
        }
    }
}

rootProject.name = "JoyPlug"
include(":joyplug:host")
include(":joyplug:utils")
include(":joyplug:plugcore")
include(":joyplug:common")
include(":joyplug:replacement")
include(":samples:view-plugin-sample")
include(":samples:host-sample")
include(":samples:activity-plugin-sample")
include(":samples:view-plugin-sample2")
include(":joyplug:services")
