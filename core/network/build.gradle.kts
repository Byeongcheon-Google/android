import gradleconfig.configureHilt
import gradleconfig.configureKotlinAndroid
import gradleconfig.configureRetrofit2

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.core.networking"

    configureKotlinAndroid(this)
    configureRetrofit2()
    configureHilt(this)
}
