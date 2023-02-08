plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.bcgg.core.ui"
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        minSdk = AppConfig.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
}

dependencies {

    implementation(
        Libraries.Ktx.CORE,
        Libraries.AndroidX.COMPOSE_UI,
        Libraries.AndroidX.COMPOSE_UI_PREVIEW,
        Libraries.AndroidX.MATERIAL3,
        Libraries.AndroidX.MATERIAL3_WINDOW_SIZE_CLASS
    )

    testImplementation(
        Libraries.Test.JUNIT
    )

    androidTestImplementation(
        Libraries.AndroidTest.JUNIT,
        Libraries.AndroidTest.ESPRESSO_CORE
    )
}
