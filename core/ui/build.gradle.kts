import gradleconfig.configureCompose
import gradleconfig.configureGmsLocation
import gradleconfig.configureKotlinAndroid
import gradleconfig.configureNaverMapCompose

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.core.ui"

    configureKotlinAndroid(this)
    configureNaverMapCompose(this)
    configureGmsLocation()
}

dependencies {
    implementation(
        project(":core:util"),
        "com.google.accompanist:accompanist-permissions:0.29.1-alpha"
    )
}
