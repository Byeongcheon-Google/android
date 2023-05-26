import gradleconfig.configureKotlinAndroid
import gradleconfig.configureRetrofit2

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.core.util"

    configureKotlinAndroid(this)
    configureRetrofit2()
}

dependencies {
    implementation(
        Libraries.Kotlin.COROUTINE_CORE
    )
}
