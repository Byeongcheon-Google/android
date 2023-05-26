package gradleconfig

import Libraries
import com.android.build.api.dsl.CommonExtension
import implementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureRetrofit2() {
    configureKotlinXSerialization()

    dependencies {
        implementation(
            Libraries.Networking.RETROFIT,
            Libraries.Networking.RETROFIT_KOTLINX_SERIALIZABLE_CONVERTER,
            Libraries.Networking.RETROFIT_SCALAR_CONVERTER,
            Libraries.Networking.OKHTTP,
            Libraries.Networking.OKHTTP_LOGGING_INTERCEPTOR
        )
    }
}