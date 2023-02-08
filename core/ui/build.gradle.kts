import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("kotlin-android")
}

val naverMapsClientId: String = gradleLocalProperties(rootDir).getProperty("naver_maps_client_id")

android {
    namespace = "com.bcgg.core.ui"
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        minSdk = AppConfig.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        manifestPlaceholders["naver_maps_client_id"] = naverMapsClientId
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
        kotlinCompilerExtensionVersion = AppConfig.KOTLIN_COMPILER_EXTENSION_VERSION
    }
}

dependencies {
    val composeBom = platform(Libraries.AndroidX.composeBom)

    implementation(
        Libraries.Ktx.CORE,
        Libraries.AndroidX.COMPOSE_UI,
        Libraries.AndroidX.COMPOSE_UI_PREVIEW,
        Libraries.AndroidX.MATERIAL3,
        Libraries.AndroidX.MATERIAL3_WINDOW_SIZE_CLASS,
        Libraries.NaverMap.NAVER_MAP_SDK,
        composeBom
    )

    debugImplementation(
        Libraries.AndroidX.COMPOSE_UI_TOOLING
    )

    testImplementation(
        Libraries.Test.JUNIT
    )

    androidTestImplementation(
        Libraries.AndroidTest.JUNIT,
        Libraries.AndroidTest.ESPRESSO_CORE
    )
}
