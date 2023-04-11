package dev.reprator.splash

import dev.reprator.language.controller.routeLanguage
import dev.reprator.splash.controller.routeSplash
import dev.reprator.testModule.configureCoreModule
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.module() {

    configureCoreModule()
    routing {
        routeLanguage()
        routeSplash(environment?.config?.setUpSplashFolder())
    }
}
