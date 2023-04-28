package dev.reprator.user.controller

import dev.reprator.core.ResultResponse
import dev.reprator.core.exception.StatusCodeException
import dev.reprator.user.modal.UserEntity
import dev.reprator.user.modal.UserEntity.DTO.Companion.mapToModal
import dev.reprator.user.modal.validateUserId
import dev.reprator.user.modal.validateUserName
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

const val ENDPOINT_USER = "/user"
private const val INPUT_USER_ID = "userId"

fun Routing.routeUser() {

    val controller by inject<UserController>()

    route(ENDPOINT_USER) {
        get {
            call.respond(ResultResponse(HttpStatusCode.OK.value, controller.getAllUser()))
        }

        get("{$INPUT_USER_ID}") {
            val languageId = call.parameters[INPUT_USER_ID].validateUserId()
            call.respond(ResultResponse(HttpStatusCode.OK.value, controller.getUser(languageId)))
        }

        post {
            val languageName: String =
                call.receiveNullable<String>().validateUserName()
            call.respond(ResultResponse(HttpStatusCode.Created.value, controller.addNewUser(languageName)))
        }

        patch("{$INPUT_USER_ID}") {
            val languageId = call.parameters[INPUT_USER_ID].validateUserId()
            val languageInfo = call.receiveNullable<Map<String, String>>().mapToModal()

            call.respond(ResultResponse(HttpStatusCode.OK.value, controller.editUser(languageId, languageInfo)))
        }

        delete("{$INPUT_USER_ID}") {
            val languageId = call.parameters[INPUT_USER_ID].validateUserId()
            call.respond(ResultResponse(HttpStatusCode.OK.value, controller.deleteUser(languageId)))
        }
    }
}