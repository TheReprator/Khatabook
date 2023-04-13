plugins {
    kotlin("jvm") version libs.versions.kotlin
}

dependencies {
    api(libs.ktor.server.core)
    api(libs.exposed.core)

    //For testing of api, else we jackson response get parsing error
    api(libs.ktor.server.serialization)
}