package dev.reprator.user.data

import dev.reprator.user.modal.UserModal


interface UserRepository {

    suspend fun allUser(): List<UserModal>

    suspend fun user(id: Int): UserModal

    suspend fun addNewUser(name: String): UserModal

    suspend fun editUser(id: Int, name: String): Boolean

    suspend fun deleteUser(id: Int): Boolean

}