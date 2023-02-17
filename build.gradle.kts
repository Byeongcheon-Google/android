plugins {
    id("org.jlleitschuh.gradle.ktlint") version Versions.KTLINT
    id("io.gitlab.arturbosch.detekt") version Versions.DETEKT
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.AGP_VERSION}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.KOTLIN_VERSION}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT_VERSION}")
        classpath("com.google.gms:google-services:${Versions.GMS}")
        classpath(kotlin("gradle-plugin", version = Versions.KOTLIN_VERSION))

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
