
import gradleconfig.configureHilt
import gradleconfig.configureKakaoApiKey
import gradleconfig.configureKotlinAndroid
import gradleconfig.configureRetrofit2

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.core.datamap"

    configureKotlinAndroid(this)
    configureRetrofit2()
    configureHilt(this)
    configureKakaoApiKey(this)
}

dependencies {
    implementation(
        project(":core:util"),
        project(":core:network"),

        Libraries.AndroidX.PAGING_RUNTIME
    )
}
