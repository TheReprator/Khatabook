package dev.reprator.language.modal

import com.fasterxml.jackson.annotation.JsonTypeInfo

/*For unit testing of api, else we jackson response get parsing error, else we don't require this annotation*/
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
interface LanguageModal : LanguageEntity {

    data class DTO (
        override val id: LanguageId,
        override val name: LanguageName
    ) : LanguageModal
}