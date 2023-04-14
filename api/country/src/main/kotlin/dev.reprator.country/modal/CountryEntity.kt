package dev.reprator.country.modal

import dev.reprator.core.Validator
import dev.reprator.country.domain.IllegalCountryException

typealias CountryId = Int
typealias CountryName = String
typealias CountryCode = Int
typealias CountryShortCode = String

interface CountryEntity {
    val name: CountryName
    val code: CountryCode
    val shortCode: CountryShortCode

    companion object {
        fun Map<String, String>?.from(): DTO = object {

            val data = this@from ?: throw IllegalCountryException()

            val name:String by data.withDefault { "" }
            val code: String by data.withDefault { "0" }
            val shortCode:String by data.withDefault { "" }

            val mappedData = DTO(name, code.toInt(), shortCode)
        }.mappedData
    }

    data class DTO (
        override val name: CountryName,
        override val code: CountryCode,
        override val shortCode: CountryShortCode
    ) : CountryEntity, Validator<DTO> {

        override fun validate(): DTO {
            validateCountryName(name)
            validateCountryShortCode(shortCode)
            code.toString().validateCountryIsoCode()

            return this
        }
    }
}

fun validateCountryName(countryName: CountryName) {
    if(countryName.isBlank()) {
        throw IllegalCountryException()
    }

    if(3 >= countryName.length) {
        throw IllegalCountryException()
    }
}

fun validateCountryShortCode(countryShortCode: CountryShortCode) {
    if(countryShortCode.isBlank()) {
        throw IllegalCountryException()
    }
}

fun String?.validateCountryIsoCode(): CountryCode {
    val countryCode = this?.toIntOrNull() ?: throw IllegalCountryException()

    if(0 >= countryCode) {
        throw IllegalCountryException()
    }

    return countryCode
}