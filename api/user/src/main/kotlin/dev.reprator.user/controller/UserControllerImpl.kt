package dev.reprator.user.controller

import dev.reprator.user.domain.UserFacade
import dev.reprator.user.modal.UserEntity
import dev.reprator.user.modal.UserId
import dev.reprator.user.modal.UserModal
import dev.reprator.user.modal.UserName

class UserControllerImpl(private val userFacade: UserFacade) : UserController {

    override suspend fun getAllUser(): Iterable<UserModal> {
        return userFacade.getAllUser()
    }

    override suspend fun getUser(id: UserId): UserModal {
        return userFacade.getUser(id)
    }

    override suspend fun addNewUser(userName: UserName): UserModal {
        return userFacade.addNewUser(userName)
    }

    override suspend fun editUser(userId: UserId, userInfo: UserEntity): Boolean {
        return userFacade.editUser(userId, userInfo)
    }

    override suspend fun deleteUser(id: UserId): Boolean {
        return userFacade.deleteUser(id)
    }

}

