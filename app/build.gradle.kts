import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("kotlin-android")
}

val naverMapsClientId: String = gradleLocalProperties(rootDir).getProperty("naver_maps_client_id")

android {
    namespace = "com.bcgg.android"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.bcgg.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        manifestPlaceholders["naver_maps_client_id"] = naverMapsClientId
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
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

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val composeBom = platform(Libraries.AndroidX.composeBom)

    implementation(
        project(":core:ui"),
        Libraries.Ktx.CORE,
        Libraries.AndroidX.COMPOSE_UI,
        Libraries.AndroidX.COMPOSE_UI_PREVIEW,
        Libraries.AndroidX.MATERIAL3,
        Libraries.AndroidX.MATERIAL3_WINDOW_SIZE_CLASS,
        Libraries.NaverMap.NAVER_MAP_COMPOSE,
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

    implementation("androidx.activity:activity-compose:1.3.1")
}
