package dev.reprator.splash.controller

import dev.reprator.core.DatabaseFactory
import dev.reprator.language.data.LanguageRepository
import dev.reprator.language.data.TableLanguage
import dev.reprator.language.modal.LanguageModal
import dev.reprator.language.setUpKoinLanguage
import dev.reprator.splash.KtorServerExtension
import dev.reprator.splash.TestDatabaseFactory
import dev.reprator.splash.createHttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.test.dispatcher.*
import io.netty.handler.codec.http.HttpHeaders.addHeader
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import java.util.*

@ExtendWith(KtorServerExtension::class)
internal class SplashController : KoinTest {

    private val databaseFactory by inject<DatabaseFactory>()
    private val languageRepository by inject<LanguageRepository>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {

        setUpKoinLanguage()

        modules(
            module {
                singleOf(::TestDatabaseFactory) bind DatabaseFactory::class
            })
    }

    @BeforeEach
    fun clearDatabase() {
        databaseFactory.connect()
        transaction {
            TableLanguage.deleteAll()
        }
    }

    @Test
    fun `Add new language`(): Unit = runBlocking {
        // given
        val client = createHttpClient()

        // when
        val response = client.post("http://0.0.0.0:8080/language") {
            setBody("onet")
            contentType(ContentType.Application.Json)
        }

        // then
        assertThat(response.status).isEqualTo(HttpStatusCode.OK)
        val resultBody = response.body<LanguageModal.DTO>()
        assertThat(resultBody).isNotNull

        assertThat(languageRepository.language(resultBody.id)).isNotNull
    }
}


