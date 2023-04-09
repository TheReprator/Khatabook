plugins {
    kotlin("jvm") version libs.versions.kotlin
}

dependencies {
    implementation(project(":lib:language"))

    implementation(libs.koin.ktor)
}