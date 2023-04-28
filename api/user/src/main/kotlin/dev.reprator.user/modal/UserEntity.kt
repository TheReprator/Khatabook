package dev.reprator.user.modal

import dev.reprator.core.Validator
import dev.reprator.user.domain.IllegalUserException

typealias UserId = Int
typealias UserName = String

interface UserEntity {
    val id: UserId
    val name: UserName

    data class DTO (
        override val id: UserId,
        override val name: UserName
    ) : UserEntity {

        companion object {
            fun Map<String, String>?.mapToModal(): DTO = object: Validator<DTO> {

                val data = this@mapToModal ?: throw IllegalUserException()

                val name:String by data.withDefault { "" }

                override fun validate(): DTO {

                    if(name.isNotEmpty())
                        name.validateUserName()

                    return DTO(0, name)
                }

            }.validate()
        }
    }
}

fun String?.validateUserId(): UserId {
    val countryCode = this?.toIntOrNull() ?: throw IllegalUserException()

    if(0 >= countryCode) {
        throw IllegalUserException()
    }

    return countryCode
}

fun String?.validateUserName(): UserName {
    val languageName = this ?: throw IllegalUserException()

    if(languageName.isBlank()) {
        throw IllegalUserException()
    }

    if(languageName.length < 3)
        throw IllegalUserException("User name can't be less than 3 character")

    return languageName
}