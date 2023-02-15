
import gradleconfig.configureHilt
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
}

dependencies {
    implementation(
        project(":core:util"),
        project(":core:network"),
        "org.osgeo:proj4j:0.1.0"
    )
}
