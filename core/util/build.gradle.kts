import gradleconfig.configureKotlinAndroid

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.core.util"

    configureKotlinAndroid(this)
}
