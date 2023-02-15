import gradleconfig.configureKotlinAndroid
import gradleconfig.configureNaverMapCompose

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.feature.planeditor"

    configureKotlinAndroid(this)
    configureNaverMapCompose(this)
}

dependencies {
    implementation(
        project(":core:domain"),
        project(":core:ui"),
        project(":core:util")
    )
}
