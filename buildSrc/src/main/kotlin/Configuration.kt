@file:Suppress("ConstPropertyName")

import org.gradle.api.JavaVersion

object Config {
    const val group = "com.divpundir.template.ktor"
    const val version = "0.0.1"

    val javaVersion = JavaVersion.VERSION_11
}
