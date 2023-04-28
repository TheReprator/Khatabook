package dev.reprator.user.controller

import dev.reprator.user.domain.IllegalUserException
import dev.reprator.user.domain.UserEmptyException
import dev.reprator.user.domain.UserNotFoundException
import dev.reprator.user.modal.UserEntity
import dev.reprator.user.modal.UserId
import dev.reprator.user.modal.UserModal
import dev.reprator.user.modal.UserName


interface UserController  {

    @Throws(UserEmptyException::class)
    suspend fun getAllUser(): Iterable<UserModal>

    @Throws(UserNotFoundException::class)
    suspend fun getUser(id: UserId): UserModal

    @Throws(IllegalUserException::class)
    suspend fun addNewUser(userName: UserName): UserModal

    @Throws(IllegalUserException::class)
    suspend fun editUser(userId: UserId, userInfo: UserEntity): Boolean

    @Throws(IllegalUserException::class)
    suspend fun deleteUser(id: UserId): Boolean
}

