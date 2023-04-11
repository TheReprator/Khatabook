plugins {
    kotlin("jvm") version libs.versions.kotlin
}

dependencies {
    implementation(project(":api:language"))

    implementation(libs.koin.ktor)

    // testing
    testImplementation(libs.test.junit5)
    testRuntimeOnly(libs.test.junit5.runtime)
    testImplementation(libs.test.ktor.server)
    testImplementation(libs.test.koin)
    testImplementation(libs.test.koin.junit5)
    testImplementation(libs.test.kotlin)

    testImplementation(libs.ktor.client.content.negotiation)

    testImplementation(libs.ktor.server.netty)
    testImplementation(libs.ktor.server.common)
    testImplementation(libs.ktor.server.status.page)
    testImplementation(libs.ktor.server.logging)
    testImplementation(libs.ktor.server.content.negotiation)
    testImplementation(libs.ktor.server.serialization)

    testImplementation(libs.exposed.jdbc)
    testImplementation(libs.exposed.hikariCp)
    testImplementation(libs.exposed.h2Db)

}

tasks.test {
    // Use the built-in JUnit support of Gradle.
    useJUnitPlatform()
}
