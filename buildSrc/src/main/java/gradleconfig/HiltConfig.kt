package gradleconfig

import AppConfig
import Libraries
import androidTestImplementation
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryDefaultConfig
import com.android.build.api.dsl.LibraryExtension
import implementation
import kapt
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import testImplementation

fun Project.configureHilt(commonExtension: CommonExtension<*, *, *, *>) {
    pluginManager.apply("dagger.hilt.android.plugin")
    pluginManager.apply("org.jetbrains.kotlin.kapt")

    commonExtension.apply {
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }

    with((this as org.gradle.api.plugins.ExtensionAware).extensions.getByName("hilt") as dagger.hilt.android.plugin.HiltExtension) {
        enableAggregatingTask = true
    }

    dependencies {
        implementation(
            Libraries.Di.HILT_ANDROID,
            Libraries.Di.HILT_COMPOSE_NAVIGATION
        )
        kapt(Libraries.Di.HILT_ANDROID_COMPILER)
    }
}