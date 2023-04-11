plugins {
    kotlin("jvm") version libs.versions.kotlin
}


dependencies {
    api(project(":lib:core"))

    implementation(libs.exposed.dao)

    implementation(libs.koin.ktor)
}