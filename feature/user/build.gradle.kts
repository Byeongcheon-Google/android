import gradleconfig.configureCompose
import gradleconfig.configureHilt
import gradleconfig.configureKotlinAndroid

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.feature.user"

    configureKotlinAndroid(this)
    configureCompose(this)
    configureHilt(this)
}

dependencies {
    implementation(
        project(":core:data"),
        project(":core:data-security"),
        project(":core:domain"),
        project(":core:ui"),
        project(":core:util"),
    )
}
