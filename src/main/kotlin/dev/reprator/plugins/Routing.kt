package dev.reprator.plugins

import dev.reprator.core.FailResponse
import dev.reprator.core.exception.StatusCodeException
import dev.reprator.language.controller.languageRoute
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
    }

    routing {
        languageRoute()
    }
}
