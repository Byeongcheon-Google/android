
import gradleconfig.configureCompose
import gradleconfig.configureHilt
import gradleconfig.configureKotlinAndroid
import gradleconfig.configureKotlinXSerialization
import gradleconfig.configureNaverMapCompose

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.feature.planresult"

    configureKotlinAndroid(this)
    configureNaverMapCompose(this)
    configureHilt(this)
    configureKotlinXSerialization()
}

dependencies {

    implementation(
        project(":core:domain"),
        project(":core:ui"),
        project(":core:util")
    )
}
