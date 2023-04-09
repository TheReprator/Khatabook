plugins {
    kotlin("jvm") version libs.versions.kotlin
}


dependencies {
    api(project(":lib:core"))

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.common)
    implementation(libs.ktor.server.status.page)
    implementation(libs.ktor.server.logging)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.serialization)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.logback)

    runtimeOnly(libs.exposed.postgres)
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.hikariCp)
    implementation(libs.exposed.encache)

    implementation(libs.koin.ktor)
    implementation(libs.koin.logger)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)

    testImplementation(libs.ktor.test.server)
    testImplementation(libs.ktor.test.junit)
}