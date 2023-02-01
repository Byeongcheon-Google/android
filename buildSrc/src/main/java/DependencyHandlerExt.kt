import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.kapt(vararg dependencies: String) {
    dependencies.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(vararg dependencies: String) {
    dependencies.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.api(vararg dependencies: String) {
    dependencies.forEach { dependency ->
        add("api", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(vararg dependencies: String) {
    dependencies.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(vararg dependencies: String) {
    dependencies.forEach { dependency ->
        add("testImplementation", dependency)
    }
}