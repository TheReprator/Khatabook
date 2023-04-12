plugins {
    kotlin("jvm") version libs.versions.kotlin
}


dependencies {
    api(project(":lib:core"))

    implementation(libs.exposed.dao)
    implementation(libs.koin.ktor)

    // testing
    testImplementation(libs.test.ktor.server)
    testImplementation(project(":lib:testModule"))
}

tasks.test {
    // Use the built-in JUnit support of Gradle.
    useJUnitPlatform()
}