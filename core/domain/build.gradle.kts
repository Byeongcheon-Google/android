
import gradleconfig.configureHilt
import gradleconfig.configureKotlinAndroid

plugins {
    id("com.android.library")
}

android {
    namespace = "com.bcgg.core.domain"

    configureKotlinAndroid(this)
    configureHilt(this)
}

dependencies {
    implementation(
        project(":core:network"),
        project(":core:util"),

        Libraries.AndroidX.PAGING_RUNTIME
    )

    implementation("javax.inject:javax.inject:1")

    api(
        project(":core:data-map")
    )
}
