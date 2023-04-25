package dev.reprator.user.modal

import com.fasterxml.jackson.annotation.JsonTypeInfo

/*For unit testing of api, else we jackson response get parsing error, else we don't require this annotation*/
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = UserModal.DTO::class)
interface UserModal : UserEntity {

    data class DTO (
        override val id: UserId,
        override val name: UserName
    ) : UserModal
}