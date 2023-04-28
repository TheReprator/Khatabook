package dev.reprator.user.domain

import dev.reprator.user.data.UserRepository
import dev.reprator.user.modal.UserEntity
import dev.reprator.user.modal.UserId
import dev.reprator.user.modal.UserModal
import dev.reprator.user.modal.UserName

class UserFacadeImpl(private val repository: UserRepository): UserFacade {

    override suspend fun getAllUser(): Iterable<UserModal> {
        return repository.allUser().ifEmpty {
            throw UserEmptyException()
        }
    }

    override suspend fun getUser(id: UserId): UserModal {
        return repository.user(id)
    }

    override suspend fun addNewUser(languageInfo: UserName): UserModal {
        return repository.addNewUser(languageInfo)
    }

    override suspend fun editUser(languageId: UserId, languageInfo: UserEntity): Boolean {
        return repository.editUser(languageId, languageInfo.name)
    }

    override suspend fun deleteUser(id: UserId): Boolean {
        return repository.deleteUser(id)
    }
}