import gradleconfig.configureCompose
import gradleconfig.configureHilt
import gradleconfig.configureKotlinAndroid
import gradleconfig.configureRetrofit2

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.feature.user"

    configureKotlinAndroid(this)
    configureRetrofit2()
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
        project(":core:network"),
    )
}
