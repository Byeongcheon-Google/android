
import gradleconfig.configureGmsLocation
import gradleconfig.configureHilt
import gradleconfig.configureKotlinAndroid
import gradleconfig.configureKotlinXSerialization
import gradleconfig.configureNaverMapCompose

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.feature.planeditor"

    configureKotlinAndroid(this)
    configureNaverMapCompose(this)
    configureKotlinXSerialization()
    configureHilt(this)
}

dependencies {
    implementation(Libraries.AndroidX.PAGING_COMPOSE)

    implementation(
        project(":core:data"),
        project(":core:domain"),
        project(":core:ui"),
        project(":core:util"),
        "com.google.accompanist:accompanist-webview:0.29.1-alpha"
    )
}
