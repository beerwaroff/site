package forum.service.impl

import forum.dto.AuthDTO
import forum.dto.CookieDTO
import forum.model.Users
import forum.model.security.Hashing
import forum.service.AuthService
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class AuthServiceImpl : AuthService {

    class Cookie(_id: Int?, _role: String, _status: String) {
        val id = _id
        val role = _role
        val status = _status
    }

    override fun authorization(dto: AuthDTO): Any {
        val user = transaction {
            Users.select { Users.username eq dto.username}.map {
                Users.toCookie(it)
            }
        }


        return if (Hashing.getHash(dto.password.toByteArray()) == user[0].password) Cookie(user[0].id, user[0].role, user[0].status) else 0
    }

    override fun registration(dto: AuthDTO): Any {
        transaction {
            Users.insert {
                it[username] = dto.username
                it[password] = Hashing.getHash(dto.password.toByteArray())
                it[role] = "user"
                it[status] = "active"
            }
        }
        return try {
            "OK"
        } catch (e: Exception) {
            "ERROR"
        }
    }

    override fun registrationAdmin(dto: AuthDTO): Any {
        transaction {
            Users.insert {
                it[username] = dto.username
                it[password] = Hashing.getHash(dto.password.toByteArray())
                it[role] = "admin"
                it[status] = "active"
            }
        }
        return try {
            "OK"
        } catch (e: Exception) {
            "ERROR"
        }
    }
    private fun Users.toCookie(row: ResultRow) = CookieDTO (
        id = row[id],
        username = row[username],
        password = row[password],
        role = row[role],
        status = row[status]
    )
}