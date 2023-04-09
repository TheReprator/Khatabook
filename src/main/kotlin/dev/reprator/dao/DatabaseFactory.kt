package dev.reprator.dao

import com.zaxxer.hikari.*
import dev.reprator.language.data.TableLanguage
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*

object DatabaseFactory {
    fun init(config: ApplicationConfig) {

        val driverClassName = config.property("storage.driverClassName").getString()
        val databaseName = config.property("storage.databaseName").getString()
        val portNumber = config.property("storage.portNumber").getString()
        val serverName = config.property("storage.serverName").getString()

        val jdbcUrl = "jdbc:postgresql://$serverName:$portNumber/$databaseName"
        val database = Database.connect(createHikariDataSource(driverClassName, jdbcUrl))

        transaction(database) {
            SchemaUtils.create(TableLanguage)
        }
    }

    private fun createHikariDataSource(
        driverName: String,
        url: String,
    ) = HikariDataSource(HikariConfig().apply {
        jdbcUrl = url
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        driverClassName = driverName
        validate()
    })
}