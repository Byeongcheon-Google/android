import gradleconfig.configureCompose
import gradleconfig.configureKotlinAndroid
import gradleconfig.configureRetrofit2

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.core.util"

    configureKotlinAndroid(this)
    configureRetrofit2()
    configureCompose(this)
}

dependencies {
    implementation(
        Libraries.Kotlin.COROUTINE_CORE
    )
}
