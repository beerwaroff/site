package forum.model

import org.jetbrains.exposed.sql.*

object Participant: Table("participant") {
    val dialogId: Column<Int> = integer("dialog_id")
    val userId: Column<Int> = integer("user_id")
}