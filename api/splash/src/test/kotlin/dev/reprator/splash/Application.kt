package dev.reprator.splash

import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.SerializationFeature
import dev.reprator.core.FailResponse
import dev.reprator.core.exception.StatusCodeException
import dev.reprator.language.controller.routeLanguage
import dev.reprator.splash.controller.routeSplash
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.module() {

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
                indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
                indentObjectsWith(DefaultIndenter("  ", "\n"))
            })
        }
    }

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
        routeLanguage()
        routeSplash(environment?.config?.setUpSplashFolder())
    }
}
