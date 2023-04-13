package dev.reprator.country.controller

import dev.reprator.core.ResultResponse
import dev.reprator.core.exception.StatusCodeException
import dev.reprator.country.domain.CountryFacade
import dev.reprator.country.modal.CountryEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

const val ENDPOINT_COUNTRY = "/country"
private const val INPUT_COUNTRY_ID = "countryId"

fun Routing.routeCountry() {

    val countryFacade by inject<CountryFacade>()

    route(ENDPOINT_COUNTRY) {
        get {
            call.respond(ResultResponse(HttpStatusCode.OK.value, countryFacade.getAllCountry()))
        }

        get("{$INPUT_COUNTRY_ID}") {
            val countryId = call.parameters[INPUT_COUNTRY_ID]?.toIntOrNull() ?: throw StatusCodeException.BadRequest(
                message = "Enter valid country id"
            )
            call.respond(ResultResponse(HttpStatusCode.OK.value, countryFacade.getCountry(countryId)))
        }

        post {
            val countryInfo =
                call.receiveNullable<CountryEntity.DTO>() ?: throw StatusCodeException.BadRequest(message = "Enter valid country")
            call.respond(ResultResponse(HttpStatusCode.Created.value, countryFacade.addNewCountry(countryInfo)))
        }

        put ("{$INPUT_COUNTRY_ID}") {
            val countryId = call.parameters[INPUT_COUNTRY_ID]?.toIntOrNull() ?: throw StatusCodeException.BadRequest(
                message = "Enter valid country id"
            )

            val countryInfo = call.receiveNullable<CountryEntity.DTO>() ?: throw StatusCodeException.BadRequest(message = "Enter valid data")
            call.respond(ResultResponse(HttpStatusCode.OK.value, countryFacade.editCountry(countryId, countryInfo)))
        }

        delete("{$INPUT_COUNTRY_ID}") {
            val countryId = call.parameters[INPUT_COUNTRY_ID]?.toIntOrNull() ?: throw StatusCodeException.BadRequest(
                message = "Enter valid country id"
            )

            countryFacade.deleteCountry(countryId)
            call.respond(ResultResponse(HttpStatusCode.OK.value, true))
        }
    }
}