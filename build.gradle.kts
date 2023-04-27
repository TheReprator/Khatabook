import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.ktor)
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "dev.reprator"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

dependencies {
    implementation(project("api:language"))
    implementation(project("api:splash"))
    implementation(project("api:country"))

    implementation(libs.ktor.server.common)
    implementation(libs.ktor.server.status.page)
    implementation(libs.ktor.server.logging)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.serialization)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.logback)

    runtimeOnly(libs.exposed.postgres)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.hikariCp)

    implementation(libs.koin.ktor)
    implementation(libs.koin.logger)

    // testing
    testImplementation(project(":lib:testModule"))
    testImplementation(libs.test.ktor.server)
}

tasks {

    val shadowJarTask = named<ShadowJar>("shadowJar") {
        archiveFileName.set("khatabook-0-0-1-with-dependencies.jar")
        mergeServiceFiles()

        manifest {
            attributes(mapOf("Main-Class" to application.mainClass.get()))
        }
    }

    named("jar") {
        enabled = false
    }

    named("assemble") {
        dependsOn(shadowJarTask)
    }
}

tasks.test {
    // Use the built-in JUnit support of Gradle.
    useJUnitPlatform()
}