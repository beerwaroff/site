package forum.model


import org.jetbrains.exposed.sql.*


object Followers: Table("followers") {
    val userId: Column<Int> = integer("user_id")
    var subscribeId: Column<Int> = integer("subscribe_id")
}