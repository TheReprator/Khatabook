package dev.reprator.splash

import io.ktor.client.HttpClient
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.jackson.jackson
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import org.junit.jupiter.api.extension.*

class KtorServerExtension : BeforeEachCallback, AfterEachCallback {
    companion object {
        private lateinit var server: NettyApplicationEngine
    }

    override fun beforeEach(context: ExtensionContext?) {
        val env = applicationEngineEnvironment {
            config = ApplicationConfig("application-test.conf")
            // Public API
            connector {
                host = "0.0.0.0"
                port = 8080
            }
        }
        server = embeddedServer(Netty, env).start(false)
    }

    override fun afterEach(context: ExtensionContext?) {
        server.stop(100, 100)
    }
}

fun createHttpClient(): HttpClient {
    val client = HttpClient {

        install(ContentNegotiation) {
            jackson()
        }

        install(HttpTimeout) {
            connectTimeoutMillis = 30000
            socketTimeoutMillis = 30000
            requestTimeoutMillis = 30000
        }
    }

    return client
}