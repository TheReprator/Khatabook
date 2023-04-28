plugins {
    kotlin("jvm") version libs.versions.kotlin
}

dependencies {
    implementation(project(":lib:core"))
    implementation(project(":api:country"))

    implementation(libs.exposed.dao)
    implementation(libs.koin.ktor)

    // testing
    testImplementation(libs.test.ktor.server)
    testImplementation(libs.test.mockk)
    testImplementation(project(":lib:testModule"))
}

tasks.test {
    // Use the built-in JUnit support of Gradle.
    useJUnitPlatform()
}
