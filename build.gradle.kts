plugins {
    id("org.jlleitschuh.gradle.ktlint") version Versions.KTLINT
    id("io.gitlab.arturbosch.detekt") version Versions.DETEKT
    id("com.google.dagger.hilt.android") version Versions.HILT_VERSION apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.AGP_VERSION}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.KOTLIN_VERSION}")
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
