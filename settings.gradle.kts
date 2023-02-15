pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://naver.jfrog.io/artifactory/maven/")
        }
    }
}

rootProject.name = "travel-plan-generator-android"
include(":app")
include(":core:network")
include(":core:util")
include(":core:ui")
include(":feature:planeditor")
include(":core:domain")
