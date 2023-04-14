package dev.reprator.country.controller

import dev.reprator.core.DatabaseFactory
import dev.reprator.core.FailResponse
import dev.reprator.core.ResultResponse
import dev.reprator.country.data.CountryRepository
import dev.reprator.country.data.TableCountry
import dev.reprator.country.modal.CountryEntity
import dev.reprator.country.modal.CountryModal
import dev.reprator.country.setUpKoinCountry
import dev.reprator.testModule.KtorServerExtension
import dev.reprator.testModule.KtorServerExtension.Companion.BASE_URL
import dev.reprator.testModule.TestDatabaseFactory
import dev.reprator.testModule.createHttpClient
import io.ktor.client.call.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.test.dispatcher.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(KtorServerExtension::class)
internal class CountryRouteTest : KoinTest {

    private val databaseFactory by inject<DatabaseFactory>()
    private val countryRepository by inject<CountryRepository>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {

        setUpKoinCountry()

        modules(
            module {
                singleOf(::TestDatabaseFactory) bind DatabaseFactory::class
            })
    }

    @BeforeEach
    fun clearDatabase() {
        databaseFactory.connect()
        transaction {
            TableCountry.deleteAll()
        }
    }

    @AfterAll
    fun closeDataBase() {
        databaseFactory.close()
    }

    private fun addCountryInDb(countryInfo: CountryEntity.DTO) = runBlocking {
        val client = createHttpClient()
        client.post("$BASE_URL$ENDPOINT_COUNTRY") {
            contentType(ContentType.Application.Json)
            setBody(countryInfo)
        }
    }

    @Test
    fun `Add new country And Verify from db by id for existence`(): Unit = runBlocking {
        val countryInput = CountryEntity.DTO("India",91, "In")
        val response = addCountryInDb(countryInput)

        Assertions.assertEquals(response.status, HttpStatusCode.OK)
        val resultBody = response.body<ResultResponse<CountryModal.DTO>>()

        Assertions.assertNotNull(resultBody)
        Assertions.assertEquals(countryInput.name, resultBody.data.name)
    }

    @Test
    fun `Failed to add new country, if country already exist`(): Unit = runBlocking {
        val countryInput = CountryEntity.DTO("India",91, "In")
        val addCountryResponse = addCountryInDb(countryInput)

        Assertions.assertEquals(addCountryResponse.status, HttpStatusCode.OK)
        val resultBody = addCountryResponse.body<ResultResponse<CountryModal.DTO>>()

        Assertions.assertNotNull(resultBody)
        Assertions.assertEquals(countryInput.name, resultBody.data.name)

        val addAgainEnglishLanguageResponse = addCountryInDb(countryInput)

        val resultBodyAgain = addAgainEnglishLanguageResponse.body<FailResponse>()
        Assertions.assertEquals(HttpStatusCode.BadRequest.value, resultBodyAgain.statusCode)
        Assertions.assertNotNull(resultBodyAgain)
    }

  /*  @Test
    fun `Failed to add new language, if language already exist`(): Unit = runBlocking {
        val addEnglishLanguageResponse = addLanguageInDb(LANGUAGE_ENGLISH)

        Assertions.assertEquals(addEnglishLanguageResponse.status, HttpStatusCode.OK)
        val resultBody = addEnglishLanguageResponse.body<ResultResponse<LanguageModal.DTO>>()
        Assertions.assertNotNull(resultBody)

        Assertions.assertEquals(languageRepository.language(resultBody.data.id)?.name, LANGUAGE_ENGLISH)
        Assertions.assertEquals(resultBody.data.name, LANGUAGE_ENGLISH)

        val addAgainEnglishLanguageResponse = addLanguageInDb(LANGUAGE_ENGLISH)
        Assertions.assertEquals(addAgainEnglishLanguageResponse.status, HttpStatusCode.OK)
        val resultBodyAgain = addAgainEnglishLanguageResponse.body<FailResponse>()
        Assertions.assertEquals(resultBodyAgain.statusCode, HttpStatusCode.InternalServerError.value)
        Assertions.assertNotNull(resultBodyAgain)
    }

    @Test
    fun `Get all language from db`(): Unit = runBlocking {
        val languageList = listOf(LANGUAGE_ENGLISH, LANGUAGE_HINDI)
        languageList.forEach {
            addLanguageInDb(it)
        }

        val client = createHttpClient()
        val response = client.get("$BASE_URL$ENDPOINT_COUNTRY")

        Assertions.assertEquals(response.status, HttpStatusCode.OK)
        val resultBody = response.body<ResultResponse<List<LanguageModal.DTO>>>()
        Assertions.assertNotNull(resultBody)

        Assertions.assertEquals(resultBody.data.size, languageList.size)
        Assertions.assertEquals(resultBody.data.first().name, languageList.first())
    }

    @Test
    fun `Get language from db by ID, if exist`(): Unit = runBlocking {
        val addLanguageResponse = addLanguageInDb(LANGUAGE_ENGLISH)

        Assertions.assertEquals(addLanguageResponse.status, HttpStatusCode.OK)
        val addResultBody = addLanguageResponse.body<ResultResponse<LanguageModal.DTO>>()
        Assertions.assertNotNull(addResultBody)

        val client = createHttpClient()
        val findResponseSuccess = client.get("$BASE_URL$ENDPOINT_COUNTRY/${addResultBody.data.id}")

        Assertions.assertEquals(findResponseSuccess.status, HttpStatusCode.OK)
        val findResultBody = findResponseSuccess.body<ResultResponse<LanguageModal.DTO>>()
        Assertions.assertNotNull(findResultBody)
        Assertions.assertEquals(findResultBody.data.name, LANGUAGE_ENGLISH)
    }

    @Test
    fun `Failed to get language from db by ID, as it didn't exit in db`(): Unit = runBlocking {
        val languageId = 90

        val client = createHttpClient()
        val findResponseSuccess = client.get("$BASE_URL$ENDPOINT_COUNTRY/$languageId")

        Assertions.assertEquals(findResponseSuccess.status, HttpStatusCode.OK)
        val findResultBody = findResponseSuccess.body<FailResponse>()
        Assertions.assertEquals(findResultBody.statusCode, HttpStatusCode.NoContent.value)
    }

    @Test
    fun `Edit language from db by ID, as it exists`(): Unit = runBlocking {
        val addLanguageResponse = addLanguageInDb(LANGUAGE_ENGLISH)

        val editLanguage = "Khatabook"
        Assertions.assertEquals(addLanguageResponse.status, HttpStatusCode.OK)
        val addResultBody = addLanguageResponse.body<ResultResponse<LanguageModal.DTO>>()
        Assertions.assertNotNull(addResultBody)
        Assertions.assertEquals(LANGUAGE_ENGLISH, addResultBody.data.name)

        val client = createHttpClient()
        val editResponse = client.patch("$BASE_URL$ENDPOINT_LANGUAGE") {
            contentType(ContentType.Application.Json)
            setBody(LanguageEntity.DTO(addResultBody.data.id, editLanguage))
        }

        Assertions.assertEquals(editResponse.status, HttpStatusCode.OK)
        val editBody = editResponse.body<ResultResponse<Boolean>>()
        Assertions.assertEquals(editBody.statusCode, HttpStatusCode.OK.value)

        Assertions.assertEquals(languageRepository.language(addResultBody.data.id)?.name, editLanguage)
    }

    @Test
    fun `Edit language from db by ID got failed, as it didn't exists`(): Unit = runBlocking {
        val languageId = 21

        val client = createHttpClient()
        val editResponse = client.patch("$BASE_URL$ENDPOINT_COUNTRY") {
            contentType(ContentType.Application.Json)
            setBody(LanguageEntity.DTO(languageId, "vikram"))
        }

        Assertions.assertEquals(editResponse.status, HttpStatusCode.OK)
        val editBody = editResponse.body<ResultResponse<Boolean>>()
        Assertions.assertEquals(editBody.statusCode, HttpStatusCode.OK.value)
        Assertions.assertEquals(editBody.data, false)
    }

    @Test
    fun `Delete language from db by ID, as it exists`(): Unit = runBlocking {
        val addLanguageResponse = addLanguageInDb(LANGUAGE_ENGLISH)

        Assertions.assertEquals(addLanguageResponse.status, HttpStatusCode.OK)
        val addResultBody = addLanguageResponse.body<ResultResponse<LanguageModal.DTO>>()
        Assertions.assertNotNull(addResultBody)
        Assertions.assertEquals(LANGUAGE_ENGLISH, addResultBody.data.name)

        val client = createHttpClient()
        val deleteResponse = client.delete("$BASE_URL$ENDPOINT_COUNTRY/${addResultBody.data.id}")

        Assertions.assertEquals(deleteResponse.status, HttpStatusCode.OK)
        val editBody = deleteResponse.body<ResultResponse<Boolean>>()
        Assertions.assertEquals(editBody.data, true)
        Assertions.assertEquals(editBody.statusCode, HttpStatusCode.OK.value)

        Assertions.assertNull(languageRepository.language(addResultBody.data.id)?.name)
    }

    @Test
    fun `Delete language from db by ID got failed, as it didn't exists`(): Unit = runBlocking {
        val languageId = 21

        val client = createHttpClient()
        val deleteResponse = client.delete("$BASE_URL$ENDPOINT_COUNTRY/$languageId")

        Assertions.assertEquals(deleteResponse.status, HttpStatusCode.OK)
        val editBody = deleteResponse.body<ResultResponse<Boolean>>()
        Assertions.assertEquals(editBody.statusCode, HttpStatusCode.OK.value)
        Assertions.assertEquals(editBody.data, false)
    }*/
}