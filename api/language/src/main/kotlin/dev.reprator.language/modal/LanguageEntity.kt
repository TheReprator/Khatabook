package dev.reprator.language.modal

import dev.reprator.core.Validator
import dev.reprator.language.domain.IllegalLanguageException

typealias LanguageId = Int
typealias LanguageName = String

interface LanguageEntity {
    val id: LanguageId
    val name: LanguageName

    data class DTO (
        override val id: LanguageId,
        override val name: LanguageName
    ) : LanguageEntity, Validator<DTO> {

        override fun validate(): DTO {
            TODO("Not yet implemented")
        }

    }
}

fun String?.validateLanguageId(): LanguageId {
    val countryCode = this?.toIntOrNull() ?: throw IllegalLanguageException()

    if(0 >= countryCode) {
        throw IllegalLanguageException()
    }

    return countryCode
}

fun String?.validateLanguageName(): LanguageName {
    val languageName = this ?: throw IllegalLanguageException()

    if(languageName.isBlank()) {
        throw IllegalLanguageException()
    }

    if(languageName.length < 3)
        throw IllegalLanguageException("Langage name can't be less than 3 character")

    return languageName
}