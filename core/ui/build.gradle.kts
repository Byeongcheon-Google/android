import gradleconfig.configureCompose
import gradleconfig.configureKotlinAndroid

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.core.ui"

    configureKotlinAndroid(this)
    configureCompose(this)
}
