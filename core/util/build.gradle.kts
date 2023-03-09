import gradleconfig.configureKotlinAndroid

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.core.util"

    configureKotlinAndroid(this)
}

dependencies {
    implementation(
        Libraries.Kotlin.COROUTINE_CORE
    )
}
