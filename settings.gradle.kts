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
        maven(uri("https://jitpack.io"))
    }
}

rootProject.name = "travel-plan-generator-android"
include(":app")
include(":core:network")
include(":core:util")
include(":core:ui")
include(":feature:planeditor")
include(":core:domain")
include(":core:data-map")
include(":feature:user")
include(":core:data")
include(":core:data-security")
include(":feature:planmanage")
include(":feature:planresult")
include(":feature:splash")
