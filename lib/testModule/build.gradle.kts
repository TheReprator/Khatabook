plugins {
    kotlin("jvm") version libs.versions.kotlin
}

dependencies {
    api(libs.ktor.server.core)
    api(libs.exposed.core)

    // testing
    api(libs.test.junit5)
    api(project(mapOf("path" to ":lib:core")))
    api(libs.test.junit5.runtime)
    api(libs.test.ktor.server)
    api(libs.test.koin)
    api(libs.test.koin.junit5)
    api(libs.test.kotlin)

    implementation(libs.ktor.client.content.negotiation)

    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.common)
    implementation(libs.ktor.server.status.page)
    implementation(libs.ktor.server.logging)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.serialization)

    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.hikariCp)
    implementation(libs.exposed.h2Db)
}