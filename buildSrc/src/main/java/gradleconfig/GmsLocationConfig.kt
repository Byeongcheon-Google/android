package gradleconfig

import Libraries
import implementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureGmsLocation() {
    dependencies {
        implementation(
            Libraries.Gms.GMS_LOCATION
        )
    }
}