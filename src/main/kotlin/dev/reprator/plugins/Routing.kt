package dev.reprator.plugins

import dev.reprator.core.FailResponse
import dev.reprator.core.exception.StatusCodeException
import dev.reprator.language.controller.routeLanguage
import dev.reprator.splash.controller.routeSplash
import dev.reprator.splash.setUpSplashFolder
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.http.*
import io.ktor.server.application.*

fun Application.configureRouting() {

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            if (cause is StatusCodeException)
                call.respond(FailResponse(HttpStatusCode.NoContent.value, cause.message.orEmpty()))
            else
                call.respond(FailResponse(HttpStatusCode.InternalServerError.value, "500: ${cause.message}"))
        }

        status(HttpStatusCode.NotFound, HttpStatusCode.Forbidden) { call, status ->
            val message = when (status) {
                HttpStatusCode.NotFound -> {
                    "Api doesnot exist"
                }

                else -> {
                    "An unknown error occurred"
                }
            }
            call.respond(FailResponse(status.value, message))
        }
    }

    routing {
        routeSplash(environment?.config?.setUpSplashFolder())
        routeLanguage()
    }
}
