package dev.reprator.user.data.mapper

import dev.reprator.core.Mapper
import dev.reprator.user.data.TableUser
import dev.reprator.user.modal.UserModal
import org.jetbrains.exposed.sql.ResultRow

class UserResponseMapper : Mapper<ResultRow, UserModal> {

    override suspend fun map(from: ResultRow): UserModal {
        return UserModal.DTO(from[TableUser.id], from[TableUser.name])
    }

}