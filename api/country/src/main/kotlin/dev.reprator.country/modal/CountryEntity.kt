package dev.reprator.country.modal

typealias CountryId = Int
typealias CountryName = String
typealias CountryCode = Int
typealias CountryShortCode = String

interface CountryEntity {
    val name: CountryName
    val code: CountryCode
    val shortCode: CountryShortCode

    data class DTO (
        val id: CountryId,
        override val name: CountryName,
        override val code: CountryCode,
        override val shortCode: CountryShortCode

    ) : CountryEntity
}