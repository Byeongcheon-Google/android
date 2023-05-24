import gradleconfig.configureApplication
import gradleconfig.configureHilt
import gradleconfig.configureKotlinAndroid
import gradleconfig.configureNaverMapCompose

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.bcgg.android"

    configureApplication(this)
    configureKotlinAndroid(this)
    configureNaverMapCompose(this)
    configureHilt(this)
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(
        project(":core:ui"),
        project(":feature:splash"),
        project(":feature:planeditor"),
        project(":feature:planmanage"),
        project(":feature:planresult"),
        project(":feature:user")
    )
}
