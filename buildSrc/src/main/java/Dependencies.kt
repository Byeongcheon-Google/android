// Dependencies.kt
object Versions {
    const val KOTLIN_VERSION = "1.7.20"
    const val AGP_VERSION = "7.4.1"

    // AndroidX
    const val APP_COMPAT = "1.6.0"
    const val MATERIAL = "1.9.0"
    const val COMPOSE_MATERIAL = "1.3.1"
    const val MATERIAL3 = "1.1.0-alpha04"
    const val COMPOSE_UI = "1.3.3"
    const val ACTIVITY_COMPOSE = "1.6.1"
    const val LIFECYCLE = "2.5.1"
    const val PAGING = "3.1.1"
    const val PAGING_COMPOSE = "1.0.0-alpha17"
    const val NAVIGATION_COMPOSE = "2.5.3"
    const val DATASTORE = "1.0.0"
    const val ACCOMPANIST_WEBVIEW = "0.31.2-alpha"

    // Kotlin
    const val COROUTINE_CORE = "1.6.4"
    const val DATETIME = "0.4.0"

    // KTX
    const val CORE = "1.9.0"
    const val LIFECYCLE_RUNTIME = "2.5.1"
    const val SERIALIZATION = "1.4.1"

    // Google Play
    const val GMS = "4.3.15"
    const val GMS_LOCATION = "21.0.1"

    // TEST
    const val JUNIT = "1.1.5"

    // Android Test
    const val ESPRESSO_CORE = "3.4.0"

    //lint
    const val KTLINT = "11.1.0"
    const val DETEKT = "1.22.0"

    //Networking
    const val RETROFIT = "2.9.0"
    const val RETROFIT_KOTLINX_SERIALIZABLE_CONVERTER = "1.0.0"
    const val OKHTTP = "4.10.0"

    //DI
    const val HILT_VERSION = "2.44.2"
    const val HILT_COMPOSE_NAVIGATION_VERSION = "1.0.0"

    //Naver Map
    const val NAVER_MAP_COMPOSE = "1.2.3"

    //Desugaring
    const val CORE_LIBRARY_DESUGRAING = "2.0.0"

    //Hawk
    const val HAWK = "2.0.1"
}

object Libraries {
    object AndroidX {
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
        const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
        const val MATERIAL_COMPOSE = "androidx.compose.material:material:${Versions.COMPOSE_MATERIAL}"
        const val MATERIAL3 = "androidx.compose.material3:material3:${Versions.MATERIAL3}"
        const val MATERIAL3_WINDOW_SIZE_CLASS =
            "androidx.compose.material3:material3-window-size-class:${Versions.MATERIAL3}"

        val composeBom = "androidx.compose:compose-bom:2022.12.00"
        const val COMPOSE_UI = "androidx.compose.ui:ui"
        const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling"
        const val COMPOSE_UI_PREVIEW = "androidx.compose.ui:ui-tooling-preview"
        const val COMPOSE_TEST_JUNIT4 = "androidx.compose.ui:ui-test-junit4"
        const val COMPOSE_UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest"
        const val ACTIVITY_COMPOSE =
            "androidx.activity:activity-compose:${Versions.ACTIVITY_COMPOSE}"

        const val LIFECYCLE_VIEWMODEL_COMPOSE =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.LIFECYCLE}"

        const val PAGING_RUNTIME = "androidx.paging:paging-runtime:${Versions.PAGING}"
        const val PAGING_COMPOSE = "androidx.paging:paging-compose:${Versions.PAGING_COMPOSE}"
        const val NAVIGATION_COMPOSE = "androidx.navigation:navigation-compose:${Versions.NAVIGATION_COMPOSE}"

        const val DATASTORE = "androidx.datastore:datastore-preferences:${Versions.DATASTORE}"

        const val ACCOMPANIST_WEBVIEW = "com.google.accompanist:accompanist-webview:${Versions.ACCOMPANIST_WEBVIEW}>"
    }

    object Kotlin {
        const val COROUTINE_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINE_CORE}"
        const val DATETIME = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.DATETIME}"
    }

    object Ktx {
        const val CORE = "androidx.core:core-ktx:${Versions.CORE}"
        const val LIFECYCLE_RUNTIME =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_RUNTIME}"
        const val SERIALIZATION =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.SERIALIZATION}"
    }

    object Test {
        const val JUNIT = "androidx.test.ext:junit:${Versions.JUNIT}"
    }

    object AndroidTest {
        const val JUNIT = "androidx.test.ext:junit:${Versions.JUNIT}"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
    }

    object Networking {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
        const val RETROFIT_KOTLINX_SERIALIZABLE_CONVERTER =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.RETROFIT_KOTLINX_SERIALIZABLE_CONVERTER}"
        const val RETROFIT_SCALAR_CONVERTER = "com.squareup.retrofit2:converter-scalars:2.5.0"
        const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
        const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
    }

    object Di {
        const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT_VERSION}"
        const val HILT_ANDROID_COMPILER =
            "com.google.dagger:hilt-android-compiler:${Versions.HILT_VERSION}"
        const val HILT_COMPOSE_NAVIGATION =
            "androidx.hilt:hilt-navigation-compose:${Versions.HILT_COMPOSE_NAVIGATION_VERSION}"
    }

    object NaverMap {
        const val NAVER_MAP_COMPOSE =
            "io.github.fornewid:naver-map-compose:${Versions.NAVER_MAP_COMPOSE}"
    }

    object Desugaring {
        const val CORE_LIBRARY_DESUGRAING =
            "com.android.tools:desugar_jdk_libs:${Versions.CORE_LIBRARY_DESUGRAING}"
    }

    object Gms {
        const val GMS_LOCATION = "com.google.android.gms:play-services-location:${Versions.GMS_LOCATION}"
    }

    object Hawk {
        const val HAWK = "com.orhanobut:hawk:${Versions.HAWK}"
    }
}