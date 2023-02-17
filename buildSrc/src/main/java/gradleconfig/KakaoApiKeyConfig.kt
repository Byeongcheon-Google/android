package gradleconfig

import AppConfig
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import implementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureKakaoApiKey(commonExtension: CommonExtension<*, *, *, *>) {
    val kakaoApiKey: String = gradleLocalProperties(rootDir).getProperty("kakao_api_key")

    commonExtension.apply {
        defaultConfig {
            buildConfigField("String", AppConfig.KAKAO_API_KEY, "\"$kakaoApiKey\"")
            manifestPlaceholders["kakao_api_key"] = kakaoApiKey
        }
    }
}