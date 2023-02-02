plugins {
    id("org.jlleitschuh.gradle.ktlint") version Versions.KTLINT
    id("io.gitlab.arturbosch.detekt") version Versions.DETEKT
    id("com.android.library") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version Versions.KOTLIN_VERSION apply false
    id("com.google.dagger.hilt.android") version Versions.HILT_VERSION apply false
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.0")
        classpath(kotlin("gradle-plugin", version = "1.7.20"))

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    apply {
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("io.gitlab.arturbosch.detekt")
    }

    afterEvaluate {
        detekt {
            buildUponDefaultConfig = true
            config.setFrom(files("$rootDir/detekt-config.yml"))
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
