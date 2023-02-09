package gradleconfig

import Libraries
import com.android.build.api.dsl.CommonExtension
import implementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureRetrofit2(commonExtension: CommonExtension<*, *, *, *>) {
    configureKotlinXSerialization(commonExtension)

    dependencies {
        implementation(
            Libraries.Networking.RETROFIT,
            Libraries.Networking.RETROFIT_KOTLINX_SERIALIZABLE_CONVERTER
        )
    }
}