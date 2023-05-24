package gradleconfig

import Libraries
import androidTestImplementation
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import debugImplementation
import implementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureCompose(commonExtension: CommonExtension<*, *, *, *>) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = "1.3.2"
        }
    }

    dependencies {
        val composeBom = platform(Libraries.AndroidX.composeBom)

        implementation(
            composeBom,
            Libraries.AndroidX.COMPOSE_UI,
            Libraries.AndroidX.COMPOSE_UI_PREVIEW,
            Libraries.AndroidX.MATERIAL_COMPOSE,
            Libraries.AndroidX.MATERIAL3,
            Libraries.AndroidX.MATERIAL3_WINDOW_SIZE_CLASS,
            Libraries.AndroidX.LIFECYCLE_VIEWMODEL_COMPOSE,
            Libraries.AndroidX.ACTIVITY_COMPOSE,
            Libraries.AndroidX.NAVIGATION_COMPOSE
        )

        debugImplementation(
            Libraries.AndroidX.COMPOSE_UI_TOOLING,
            Libraries.AndroidX.COMPOSE_UI_TEST_MANIFEST
        )
    }
}