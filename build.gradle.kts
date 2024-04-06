import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.ktor) apply false
}

allprojects {

    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = Config.javaVersion.toString()
        targetCompatibility = Config.javaVersion.toString()
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = Config.javaVersion.toString()
        }
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
