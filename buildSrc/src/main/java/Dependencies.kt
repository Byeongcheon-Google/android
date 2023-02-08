// Dependencies.kt
object Versions {

    const val KOTLIN_VERSION = "1.7.20"

    // AndroidX
    const val APP_COMPAT = "1.6.0"
    const val MATERIAL = "1.8.0"
    const val MATERIAL3 = "1.0.1"
    const val COMPOSE_UI = "1.3.3"

    // KTX
    const val CORE = "1.9.0"

    // TEST
    const val JUNIT = "1.1.5"

    // Android Test
    const val ESPRESSO_CORE = "3.4.0"

    //lint
    const val KTLINT = "11.1.0"
    const val DETEKT = "1.22.0"

    //Networking
    const val KTOR_CLIENT = "2.2.3"

    //DI
    const val HILT_VERSION = "2.44"
}

object Libraries {
    object AndroidX {
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
        const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
        const val COMPOSE_UI = "androidx.compose.ui:ui:${Versions.COMPOSE_UI}"
        const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE_UI}"
        const val COMPOSE_UI_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE_UI}"
        const val MATERIAL3 = "androidx.compose.material3:material3:${Versions.MATERIAL3}"
        const val MATERIAL3_WINDOW_SIZE_CLASS = "androidx.compose.material3:material3-window-size-class:${Versions.MATERIAL3}"

        val composeBom = "androidx.compose:compose-bom:2022.12.00"
    }

    object Ktx {
        const val CORE = "androidx.core:core-ktx:${Versions.CORE}"
    }

    object Test {
        const val JUNIT = "androidx.test.ext:junit:${Versions.JUNIT}"
    }

    object AndroidTest {
        const val JUNIT = "androidx.test.ext:junit:${Versions.JUNIT}"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
    }

    object Networking {
        const val KTOR_CLIENT_CORE = "io.ktor:ktor-client-core:${Versions.KTOR_CLIENT}"
        const val KTOR_CLIENT_LOGGING = "io.ktor:ktor-client-logging:${Versions.KTOR_CLIENT}"
    }

    object Di {
        const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT_VERSION}"
        const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT_VERSION}"
    }
}