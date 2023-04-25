package dev.reprator.user.data

import dev.reprator.country.data.TableCountry
import org.jetbrains.exposed.sql.*

const val MINIMUM_LENGTH_PHONE = 7

object TableUser : Table(name="user") {
    val id = integer("id").autoIncrement()
    val name = text("name")
    val number = integer("number").check {
        it.greater(MINIMUM_LENGTH_PHONE)
    }
    val countryCode = integer("code").references(TableCountry.isocode, ReferenceOption.RESTRICT)

    override val primaryKey = PrimaryKey(id)
}