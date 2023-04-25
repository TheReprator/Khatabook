package dev.reprator.user.domain

import dev.reprator.user.modal.UserEntity
import dev.reprator.user.modal.UserId
import dev.reprator.user.modal.UserModal
import dev.reprator.user.modal.UserName

interface UserFacade {

    @Throws(UserEmptyException::class)
    suspend fun getAllUser(): Iterable<UserModal>

    suspend fun getUser(id: UserId): UserModal

    suspend fun addNewUser(languageInfo: UserName): UserModal

    suspend fun editUser(languageId: UserId, languageInfo: UserEntity): Boolean

    suspend fun deleteUser(id: UserId): Boolean

}