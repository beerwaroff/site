package forum.model

import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime

object Dialogs: Table("dialogs") {
    val id: Column<Int> = integer("id").primaryKey().autoIncrement()
    val created: Column<DateTime> = date("created")
}