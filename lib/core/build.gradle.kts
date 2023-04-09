plugins {
    kotlin("jvm") version libs.versions.kotlin
}

dependencies {
    implementation(libs.ktor.server.core)
    api(libs.exposed.core)
}