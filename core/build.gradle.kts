plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(ktorLibs.plugins.ktor)
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jlleitschuh.gradle.ktlint") version "14.0.1"
    id("dev.detekt")
}

group = "com.brise"
version = "1.0"

kotlin {
    jvmToolchain(25)
}

application {
    mainClass.set("com.brise.core.ClientKt")
}

dependencies {
    implementation(ktorLibs.client.cio)
    implementation(ktorLibs.client.contentNegotiation)
    implementation(ktorLibs.client.logging)
    implementation(ktorLibs.serialization.kotlinx.json)
    implementation(libs.logback.classic)

    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.11.0")
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
}

detekt {
    buildUponDefaultConfig = true
    config.setFrom("$rootDir/config/detekt/detekt.yml")
}

tasks.test {
    useJUnitPlatform {
        excludeTags("integration")
    }
}