plugins {
    alias(libs.plugins.kotlin.jvm)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.3.21" apply false
    id("org.jlleitschuh.gradle.ktlint") version "14.0.1" apply false

}

group = "com.brise"
version = "1.0"

kotlin {
    jvmToolchain(25)
}

dependencies {
    testImplementation(kotlin("test"))
}