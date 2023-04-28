package dev.reprator.user.data

import dev.reprator.core.Mapper
import dev.reprator.core.dbQuery
import dev.reprator.user.domain.IllegalUserException
import dev.reprator.user.domain.UserNotFoundException
import dev.reprator.user.modal.UserModal
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class UserRepositoryImpl(private val mapper: Mapper<ResultRow, UserModal>) : UserRepository {

    private suspend fun resultRowToUser(row: ResultRow): UserModal = mapper.map(row)

    override suspend fun allUser(): List<UserModal> = dbQuery {
        TableUser.selectAll().map {
            resultRowToUser(it)
        }.sortedBy {
            it.name
        }
    }

    override suspend fun user(id: Int): UserModal = dbQuery {
        TableUser
            .select { TableUser.id eq id }
            .map{
                resultRowToUser(it)
            }
            .singleOrNull() ?: throw UserNotFoundException()
    }

    override suspend fun addNewUser(name: String): UserModal = dbQuery {
        val insertStatement = TableUser.insert {
            it[TableUser.name] = name
        }
        insertStatement.resultedValues?.singleOrNull()?.let {
            resultRowToUser(it)
        } ?: throw IllegalUserException()
    }

    override suspend fun editUser(id: Int, name: String): Boolean = dbQuery {
        TableUser.update({ TableUser.id eq id }) {
            it[TableUser.name] = name
        } > 0
    }

    override suspend fun deleteUser(id: Int): Boolean = dbQuery {
        TableUser.deleteWhere { TableUser.id eq id } > 0
    }
}