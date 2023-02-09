import gradleconfig.configureKotlinAndroid

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.core.networking"

    configureKotlinAndroid(this)
}
