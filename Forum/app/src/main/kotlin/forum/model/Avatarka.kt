package forum.model

import org.jetbrains.exposed.sql.*

object Avatarka: Table("avatarka") {
    val id: Column<Int> = integer("id").primaryKey().autoIncrement()
    val profilePicture = binary("profile_picture", 4096)
    val userId = integer("user_id")
}