package gradleconfig

import com.android.build.api.dsl.CommonExtension
import implementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

fun Project.configureDataStore() {
    dependencies {
        implementation(
            Libraries.AndroidX.DATASTORE
        )
    }
}