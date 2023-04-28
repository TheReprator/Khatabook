pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

rootProject.name = "Khatabook"

include(":lib:testModule")
include(":lib:core")
include(":api:language")
include(":api:splash")
include(":api:country")