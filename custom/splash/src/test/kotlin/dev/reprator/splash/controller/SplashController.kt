package dev.reprator.splash.controller

import dev.reprator.core.DatabaseFactory
import dev.reprator.splash.KtorServerExtension
import dev.reprator.splash.TestDatabaseFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

@ExtendWith(KtorServerExtension::class)
internal class SplashController : KoinTest {

    private val databaseFactory by inject<DatabaseFactory>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            module {
                singleOf(::TestDatabaseFactory) bind DatabaseFactory::class
            })
    }

    @Test
    fun contextIsCreatedForTheTest() {
        val db = databaseFactory
        Assertions.assertNotNull(db)
    }
}