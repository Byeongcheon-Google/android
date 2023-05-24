package gradleconfig

import Libraries
import com.android.build.api.dsl.CommonExtension
import implementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureStomp() {
    configureKotlinXSerialization()

    dependencies {
        implementation(
            "com.github.naiksoftware:stompprotocolandroid:1.6.6",
            "io.reactivex.rxjava2:rxkotlin:2.4.0",
            "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:1.7.1"
        )
    }
}