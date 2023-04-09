package dev.reprator

import dev.reprator.dao.DatabaseFactory
import dev.reprator.language.setUpKoinLanguage
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import dev.reprator.plugins.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init(environment.config)

    install(Koin) {
        SLF4JLogger()
        setUpKoinLanguage()
    }

    install(ShutDownUrl.ApplicationCallPlugin) {
        shutDownUrl = "/shutdown"
        exitCodeSupplier = { 0 }
    }

    configureMonitoring()
    configureSerialization()
    configureRouting()
}
