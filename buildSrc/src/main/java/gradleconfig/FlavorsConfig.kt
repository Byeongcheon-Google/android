package gradleconfig

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

@Suppress("EnumEntryName")
enum class FlavorDimension {
    contentType
}

enum class DataFlavors(val suffix: String? = null) {
    fake("fake"), stage("stage"), production
}

fun Project.configureDataFlavors(
    commonExtension: CommonExtension<*, *, *, *>
) {
    commonExtension.apply {
        flavorDimensions += FlavorDimension.contentType.name

        productFlavors {
            DataFlavors.values().forEach {
                create(it.name) {
                    dimension = FlavorDimension.contentType.name
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (it.suffix != null) {
                            this.applicationIdSuffix = ".${it.suffix}"
                            this.versionNameSuffix = "-${it.suffix}"
                        }
                    }
                }
            }
        }
    }
}