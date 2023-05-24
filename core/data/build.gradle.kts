
import gradleconfig.configureHilt
import gradleconfig.configureKotlinAndroid
import gradleconfig.configureRetrofit2
import gradleconfig.configureStomp

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.core.data"

    configureKotlinAndroid(this)
    configureRetrofit2()
    configureStomp()
    configureHilt(this)
}

dependencies {
    implementation(
        project(":core:util"),
        project(":core:network")
    )
}
