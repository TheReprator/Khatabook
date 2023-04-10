package dev.reprator.splash.controller

import dev.reprator.core.ResultResponse
import dev.reprator.language.domain.LanguageFacade
import dev.reprator.splash.modal.SplashModal
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.io.File

const val ENDPOINT_SPLASH = "/splash"

fun Routing.routeSplash(splashDirectory: File?) {

    val languageFacade by inject<LanguageFacade>()

    route(ENDPOINT_SPLASH) {
        get {

            val file: List<String> = splashDirectory?.listFiles()?.map {
                it.absolutePath
            }.orEmpty()

            val languageList = languageFacade.getAllLanguage()

            val splashModal = SplashModal(file, languageList.toList())
            call.respond(ResultResponse(HttpStatusCode.OK.value, splashModal))
        }

    }
}