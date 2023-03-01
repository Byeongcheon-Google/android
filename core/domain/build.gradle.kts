
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
        project(":core:data"),
        project(":core:data-security"),
        project(":core:data-map"),

        Libraries.AndroidX.PAGING_RUNTIME
    )

    implementation("javax.inject:javax.inject:1")
}
