
import gradleconfig.configureGmsLocation
import gradleconfig.configureHilt
import gradleconfig.configureKotlinAndroid
import gradleconfig.configureNaverMapCompose

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.feature.planeditor"

    configureKotlinAndroid(this)
    configureNaverMapCompose(this)
    configureHilt(this)
    configureGmsLocation()
}

dependencies {
    implementation(Libraries.AndroidX.PAGING_COMPOSE)

    implementation(
        project(":core:domain"),
        project(":core:ui"),
        project(":core:util"),
    )
}
