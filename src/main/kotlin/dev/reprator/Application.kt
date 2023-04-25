package dev.reprator

import dev.reprator.configuration.setupApplicationConfiguration
import dev.reprator.core.DatabaseFactory
import dev.reprator.country.setUpKoinCountry
import dev.reprator.language.setUpKoinLanguage
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import dev.reprator.plugins.*
import dev.reprator.user.setUpKoinUser
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger

fun main(args: Array<String>): Unit = EngineMain.main(args)


fun Application.module() {

    install(Koin) {
        SLF4JLogger()
        modules(koinAppModule)
        setUpKoinLanguage()
        setUpKoinCountry()
        setUpKoinUser()
    }

    val databaseFactory : DatabaseFactory by inject { parametersOf(environment.config.setupApplicationConfiguration()) }
    databaseFactory.connect()

    install(ShutDownUrl.ApplicationCallPlugin) {
        shutDownUrl = "/shutdown"
        exitCodeSupplier = { 0 }
    }

    configureMonitoring()
    configureContentNegotiation()
    configureRouting()
}
