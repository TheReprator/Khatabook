plugins {
    kotlin("jvm") version libs.versions.kotlin
}

dependencies {
    api(libs.ktor.server.core)
    api(libs.exposed.core)
}