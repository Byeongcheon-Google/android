
import gradleconfig.configureCompose
import gradleconfig.configureHilt
import gradleconfig.configureKotlinAndroid

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.feature.planeditor"

    configureKotlinAndroid(this)
    configureCompose(this)
    configureHilt(this)
}

dependencies {
    implementation(Libraries.AndroidX.PAGING_COMPOSE)

    implementation(
        project(":core:domain"),
        project(":core:ui"),
        project(":core:util")
    )
}
