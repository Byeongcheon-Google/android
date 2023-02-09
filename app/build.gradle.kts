import gradleconfig.configureApplication
import gradleconfig.configureHilt
import gradleconfig.configureKotlinAndroid
import gradleconfig.configureNaverMapCompose

plugins {
    id("com.android.application")
}

android {
    namespace = "com.bcgg.android"

    configureApplication(this)
    configureKotlinAndroid(this)
    configureNaverMapCompose(this)
    configureHilt(this)
}

dependencies {
    implementation(project(":core:ui"))

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")
}
