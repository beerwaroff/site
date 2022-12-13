package forum.model

import org.jetbrains.exposed.sql.*

object Comments: Table("comments") {
    val id: Column<Int> = integer("id").primaryKey().autoIncrement()
    val userId: Column<Int> = integer("user_id")
    val username: Column<String> = varchar("username", 16)
    val postId: Column<Int> = integer("post_id")
    val text: Column<String> = varchar("text", 4096)
}