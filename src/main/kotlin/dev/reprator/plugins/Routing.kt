package dev.reprator.plugins

import dev.reprator.dao.DAOLanguageFacade
import dev.reprator.dao.DAOLanguageFacadeCacheImpl
import dev.reprator.dao.DAOLanguageFacadeImpl
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.http.*
import io.ktor.server.application.*
import kotlinx.coroutines.runBlocking
import java.io.File

fun Application.configureRouting() {

    val dao: DAOLanguageFacade = DAOLanguageFacadeCacheImpl(
        DAOLanguageFacadeImpl(),
        File(environment.config.property("storage.ehcacheFilePath").getString())
    ).apply {
        runBlocking {
            if(allLanguage().isEmpty()) {
                addNewLanguage("Chinese")
            }
        }
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        route("language") {
            get {
                call.respond(dao.allLanguage())
            }
        }
    }
}
