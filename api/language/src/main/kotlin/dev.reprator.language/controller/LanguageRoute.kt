package dev.reprator.language.controller

import dev.reprator.core.ResultResponse
import dev.reprator.core.exception.StatusCodeException
import dev.reprator.language.modal.LanguageEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

const val ENDPOINT_LANGUAGE = "/language"
private const val INPUT_LANGUAGE_ID = "languageId"

fun Routing.routeLanguage() {

    val controller by inject<LanguageController>()

    route(ENDPOINT_LANGUAGE) {
        get {
            call.respond(ResultResponse(HttpStatusCode.OK.value, controller.getAllLanguage()))
        }

        get("{$INPUT_LANGUAGE_ID}") {
            val languageId = call.parameters[INPUT_LANGUAGE_ID]?.toIntOrNull() ?: throw StatusCodeException.BadRequest(
                message = "Enter valid language id"
            )
            call.respond(ResultResponse(HttpStatusCode.OK.value, controller.getLanguage(languageId)))
        }

        post {
            val languageName: String =
                call.receiveNullable<String>() ?: throw StatusCodeException.BadRequest(message = "Enter valid language")
            if(languageName.isEmpty() || languageName.length < 3)
                throw StatusCodeException.BadRequest(message = "Enter valid language")
            call.respond(ResultResponse(HttpStatusCode.Created.value, controller.addNewLanguage(languageName)))
        }

        patch {
            val languageInfo = call.receiveNullable<LanguageEntity.DTO>() ?: throw StatusCodeException.BadRequest(message = "Enter valid data")
            call.respond(ResultResponse(HttpStatusCode.OK.value, controller.editLanguage(languageInfo)))
        }

        delete("{$INPUT_LANGUAGE_ID}") {
            val languageId = call.parameters[INPUT_LANGUAGE_ID]?.toIntOrNull() ?: throw StatusCodeException.BadRequest(
                message = "Enter valid language id"
            )
            call.respond(ResultResponse(HttpStatusCode.OK.value, controller.deleteLanguage(languageId)))
        }
    }
}