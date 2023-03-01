package gradleconfig

import AppConfig
import Libraries
import androidTestImplementation
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryDefaultConfig
import com.android.build.api.dsl.LibraryExtension
import implementation
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import testImplementation

fun Project.configureKotlinAndroid(commonExtension: CommonExtension<*, *, *, *>) {
    pluginManager.apply("kotlin-android")

    commonExtension.apply {
        compileSdk = AppConfig.COMPILE_SDK

        defaultConfig {
            minSdk = AppConfig.MIN_SDK

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

            if (this@apply is LibraryExtension && this is LibraryDefaultConfig) {
                consumerProguardFiles("consumer-rules.pro")
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
            isCoreLibraryDesugaringEnabled = true
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            }
        }
    }

    dependencies {
        add("coreLibraryDesugaring", Libraries.Desugaring.CORE_LIBRARY_DESUGRAING)

        implementation(
            Libraries.Ktx.CORE,
        )

        testImplementation(
            Libraries.Test.JUNIT
        )

        androidTestImplementation(
            Libraries.AndroidTest.JUNIT,
            Libraries.AndroidTest.ESPRESSO_CORE
        )
    }
}