package dev.reprator

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import dev.reprator.plugins.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    install(ShutDownUrl.ApplicationCallPlugin) {
        shutDownUrl = "/shutdown"
        exitCodeSupplier = { 0 }
    }
    configureMonitoring()
    configureSerialization()
    configureRouting()
}
