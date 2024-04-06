package com.divpundir.template.ktor.database

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.coroutines.CoroutineContext

private object UserTable : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 50)
    val email = varchar("email", length = 50)

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String,
)

class UserService(
    database: Database,
    private val context: CoroutineContext
) {
    init {
        transaction(database) {
            SchemaUtils.create(UserTable)
        }
    }

    suspend fun create(name: String, email: String): User = dbQuery {
        val id = UserTable.insert {
            it[UserTable.name] = name
            it[UserTable.email] = email
        }[UserTable.id]

        User(
            id = id,
            name = name,
            email = email
        )
    }

    suspend fun read(id: Int): User? = dbQuery {
        UserTable
            .selectAll()
            .where { UserTable.id eq id }
            .map {
                User(
                    id = it[UserTable.id],
                    name = it[UserTable.name],
                    email = it[UserTable.email]
                )
            }
            .singleOrNull()
    }

    suspend fun readAll(): List<User> = dbQuery {
        UserTable
            .selectAll()
            .map {
                User(
                    id = it[UserTable.id],
                    name = it[UserTable.name],
                    email = it[UserTable.email]
                )
            }
    }

    suspend fun update(user: User) {
        dbQuery {
            UserTable.update(
                where = { UserTable.id eq user.id }
            ) {
                it[name] = user.name
                it[email] = user.email
            }
        }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            UserTable.deleteWhere {
                UserTable.id eq id
            }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(context) { block() }
}
