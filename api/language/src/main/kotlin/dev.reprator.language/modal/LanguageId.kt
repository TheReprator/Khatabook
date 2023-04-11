package dev.reprator.language.modal

typealias LanguageId = Int
typealias LanguageName = String

interface LanguageEntity {
    val id: LanguageId
    val name: LanguageName

    data class DTO (
        override val id: LanguageId,
        override val name: LanguageName
    ) : LanguageEntity
}