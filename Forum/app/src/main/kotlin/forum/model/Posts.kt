package forum.model


import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime

object Posts: Table("posts") {
    val id: Column<Int> = integer("id").primaryKey().autoIncrement()
    val name: Column<String> = varchar("name", 255)
    val userId: Column<Int> = integer("user_id")
    val username: Column<String> = varchar("username", 255)
    val text: Column<String> = varchar("text", 4096)
    val created: Column<DateTime> = date("created")
}