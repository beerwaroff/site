package forum.model

import forum.model.Dialogs.autoIncrement
import forum.model.Dialogs.primaryKey
import org.jetbrains.exposed.sql.*

object Messages: Table("messages") {
    val id: Column<Int> = integer("id").primaryKey().autoIncrement()
    val dialogId: Column<Int> = integer("dialog_id")
    val senderId: Column<Int> = integer("sender_id")
    val message: Column<String> = varchar("message", 4096)
}