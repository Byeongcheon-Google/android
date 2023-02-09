package gradleconfig

import AppConfig
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project

fun Project.configureApplication(applicationExtension: ApplicationExtension) {
    pluginManager.apply("com.android.application")

    applicationExtension.apply {
        compileSdk = AppConfig.COMPILE_SDK

        defaultConfig {
            applicationId = "com.bcgg.android"
            minSdk = AppConfig.MIN_SDK
            targetSdk = AppConfig.TARGET_SDK
            versionCode = AppConfig.VERSION_CODE
            versionName = AppConfig.VERSION_NAME
        }

        packagingOptions {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
}