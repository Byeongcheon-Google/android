package gradleconfig

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import implementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureNaverMapCompose(commonExtension: CommonExtension<*, *, *, *>) {
    configureCompose(commonExtension)

    val naverMapsClientId: String = gradleLocalProperties(rootDir).getProperty("naver_maps_client_id")

    commonExtension.apply {
        defaultConfig {
            buildConfigField("String", AppConfig.NAVER_MAPS_CLIENT_ID, "\"$naverMapsClientId\"")
            manifestPlaceholders["naver_maps_client_id"] = naverMapsClientId
        }
    }

    dependencies {
        implementation(
            Libraries.NaverMap.NAVER_MAP_COMPOSE
        )
    }
}