package gradleconfig

import com.android.build.api.dsl.CommonExtension
import implementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

fun Project.configureNaverMapCompose(commonExtension: CommonExtension<*, *, *, *>) {
    configureCompose(commonExtension)

    val naverMapsClientId: String = gradleLocalProperties(rootDir).getProperty("naver_maps_client_id")

    commonExtension.apply {
        defaultConfig {
            manifestPlaceholders["naver_maps_client_id"] = naverMapsClientId
        }
    }

    dependencies {
        implementation(
            Libraries.NaverMap.NAVER_MAP_COMPOSE
        )
    }
}

internal fun gradleLocalProperties(projectRootDir : File) : Properties {
    val properties = Properties()
    val localProperties = File(projectRootDir, "local.properties")

    if (localProperties.isFile) {
        InputStreamReader(FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    }
    return properties
}