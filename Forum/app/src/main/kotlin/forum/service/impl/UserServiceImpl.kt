package forum.service.impl
import forum.dto.*
import forum.model.Avatarka
import forum.model.Followers
import forum.model.Messages
import forum.model.Users
import forum.model.Users.password
import forum.model.security.Hashing
import forum.service.UserService
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UserServiceImpl(): UserService {


    class UsersList(_accounts: List<UsersDTO>, _count: Int, _subscriptions: List<Int>) {
        val accounts = _accounts
        val count = _count
        val subscriptions = _subscriptions
    }

    override fun getUsers(id: Int, page: Int): UsersList {
        val users = transaction {
            (Users.join(Followers, JoinType.LEFT, additionalConstraint = { Followers.subscribeId eq Users.id}))
                .slice(Users.id, Users.username, Users.profilePicture, Users.status,Followers.subscribeId.count() )
                .select {Users.id neq id}
                .groupBy(Users.id)
                .map {
                    UsersDTO(
                        id = it[Users.id],
                        username = it[Users.username],
                        profilePicture = it[Users.profilePicture],
                        countFollowers = it[Followers.subscribeId.count()],
                        status = it[Users.status]
                    )
                }
        }


        val count = transaction {
            Users.select {Users.id neq id}.count()
        }

        val subscriptions = mutableListOf<Int>()
        transaction {
            Followers.slice(Followers.userId, Followers.subscribeId).select { (Followers.userId eq id)}.map {
                subscriptions += it[Followers.subscribeId].toInt()
            }
        }

        return UsersList(users, count, subscriptions)
    }

    override fun getProfile(id: Int): List<UsersDTO> {
        val profile = transaction {
                (Users.join(Followers, JoinType.LEFT, additionalConstraint = { Followers.subscribeId eq Users.id}))
                    .slice(Users.id, Users.username, Users.profilePicture, Users.status, Followers.subscribeId.count() )
                    .select {Users.id eq id}
                    .groupBy(Users.id)
                    .map {
                        UsersDTO(
                            id = it[Users.id],
                            username = it[Users.username],
                            profilePicture = it[Users.profilePicture],
                            countFollowers = it[Followers.subscribeId.count()],
                            status = it[Users.status]
                        )
                    }
            }
        return profile
    }

    override fun changePassword(id: Int, dto: ChangePasswordDTO): Any {
        val oldPassword = transaction { Users.slice(Users.password).select {Users.id eq id }.map {
            it[password].toString()
        } }
        return if (oldPassword[0] == Hashing.getHash(dto.oldPassword.toByteArray())) {
            transaction {
                Users.update ({ Users.id eq id }) {
                    it[Users.password] = Hashing.getHash(dto.newPassword.toByteArray()) }
            }
            return "OK"
        } else {
            "Неправильный пароль"
        }
    }

    override fun getUsername(id: Int): String {
        val username = transaction {
            Users.slice(Users.username).select { Users.id eq id }.map {
                it[Users.username].toString()
            }[0]
        }
        return username
    }

    override fun setProfilePicture(id: Int, file: MultipartFile): Any {
        transaction {
            Users.update ({Users.id eq id}) {
                it[profilePicture] = file.bytes
            }
        }

        return try {
            "Фото профиля успешно обновлено"
        } catch (e: FileSizeLimitExceededException) {
            "Файл слишком большой, выберите другой"
        }
    }

    override fun getProfilePicture(id: Int): ByteArray {
        val picture = transaction {
            Users.slice(Users.profilePicture)
                .select { Users.id eq id }.map {
                    it[Users.profilePicture]
                }
        }[0]

        return picture
    }

    override fun statusUp(id: Int): Any {
        transaction {
            Users.update ({ Users.id eq id }) {
                it[Users.status] = "active"
            }
        }
        return try {
            "Разблокирован."
        } catch (e: Exception) {
            "Что-то пошло не так..."
        }
    }

    override fun statusDown(id: Int): Any {
        transaction {
            Users.update ({ Users.id eq id }) {
                it[Users.status] = "blocked"
            }
        }
        return try {
            "Заблокирован."
        } catch (e: Exception) {
            "Что-то пошло не так..."
        }
    }
    private fun Users.toUsersDto(row: ResultRow) =
        UserDTO(
            id = row[id],
            username = row[username],
            password = row[password],
            profilePicture = row[profilePicture],
            status = row[status],
            role = row[role],
        )

}