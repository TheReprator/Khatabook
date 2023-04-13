package dev.reprator.country.data

import dev.reprator.core.Mapper
import dev.reprator.core.dbQuery
import dev.reprator.country.domain.CountryNotFoundException
import dev.reprator.country.domain.IllegalCountryException
import dev.reprator.country.modal.CountryEntity
import dev.reprator.country.modal.CountryId
import dev.reprator.country.modal.CountryModal
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class CountryRepositoryImpl(private val mapper: Mapper<TableCountryEntity, CountryModal>) : CountryRepository {

    private suspend fun resultRowToCountry(row: TableCountryEntity): CountryModal = mapper.map(row)

    override suspend fun getAllCountry(): List<CountryModal> = dbQuery {
        TableCountryEntity.all().map {
            resultRowToCountry(it)
        }.sortedBy {
            it.name
        }
    }

    override suspend fun getCountry(id: CountryId): CountryModal = dbQuery {
        val countryEntity = TableCountryEntity.findById(id)  ?: throw CountryNotFoundException()
        resultRowToCountry(countryEntity)
    }

    override suspend fun addNewCountry(countryInfo: CountryEntity): CountryModal = dbQuery {
        val existingCountry = TableCountryEntity.find { TableCountry.isocode eq countryInfo.code }.firstOrNull()

        if(null != existingCountry)
            throw IllegalCountryException()

        val newCountry = TableCountryEntity.new {
            name = countryInfo.name
            shortcode = countryInfo.shortCode
            isocode = countryInfo.code
        }
        resultRowToCountry(newCountry)
    }

    override suspend fun editCountry(countryId: CountryId, countryInfo: CountryEntity): Boolean = transaction {
        TableCountryEntity.findById(countryId) ?: throw IllegalCountryException()

        TableCountry.update({ TableCountry.id eq countryId }) {
            it[TableCountry.name] = countryInfo.name
            it[TableCountry.isocode] = countryInfo.code
            it[TableCountry.shortcode] = countryInfo.shortCode
        } > 0
    }

    override suspend fun deleteCountry(id: CountryId): Unit = transaction {
        val existingCountry =
            TableCountryEntity.find { TableCountry.id eq id }.firstOrNull()
                ?: throw IllegalCountryException()
        existingCountry.delete()
    }
}