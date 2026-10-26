plugins {
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.serialization") version "2.1.10"
    kotlin("plugin.compose") version "2.1.10"
    id("org.jetbrains.compose") version "1.7.3"
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    // Compose Desktop UI framework
    implementation(compose.desktop.currentOs)

    // JSON Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.1")
}

kotlin {
    jvmToolchain(21)
}

compose.desktop {
    application {
        mainClass = "com.event.teamapp.MainKt"

        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Exe,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb
            )
            packageName = "TeamGenerator"
            packageVersion = "1.0.0"
        }
    }
}