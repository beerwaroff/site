package forum.model


import org.jetbrains.exposed.sql.*


object Users: Table("users") {
    val id: Column<Int> = integer("id").primaryKey().autoIncrement()
    val username: Column<String> = varchar("username", 16)
    var password: Column<String> = varchar("password", 64)
    val profilePicture = binary("profile_picture", 4096)
    val role: Column<String> = varchar("role", 8)
    val status: Column<String> = varchar("status", 8)
}