package forum.service.impl

import forum.dto.SubscriptionDTO
import forum.model.Followers
import forum.model.Users
import forum.model.security.Hashing
import forum.service.FollowersService
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class FollowersServiceImpl : FollowersService {

    override fun getSubscriptionId(userId: Int): MutableList<Int> {
        val subscriptions = mutableListOf<Int>()
        transaction {
            Followers.slice(Followers.userId, Followers.subscribeId).select { (Followers.userId eq userId)}.map {
                subscriptions += it[Followers.subscribeId].toInt()
            }
        }
        return subscriptions
    }

    override fun getSubscription(userId: Int): Any {
        val subscriptionId = getSubscriptionId(userId)
        val subscription = mutableListOf<SubscriptionDTO>()
        for (i in subscriptionId) {
            transaction {
                Users.slice(Users.id, Users.username, Users.profilePicture).select {Users.id eq i}.map {
                    subscription += Users.toSubscriptionDto(it)
                }
            }
        }
        return subscription
    }

    override fun unsubscribe(userId: Int, subscribeId: Int): Any {
        transaction { Followers.deleteWhere { (Followers.userId eq userId) and (Followers.subscribeId eq subscribeId) } }
        return getSubscriptionId(userId)
    }

    override fun subscribe(_userId: Int, _subscribeId: Int): Any {
        transaction {
            Followers.insert {
                it[userId] = _userId
                it[subscribeId] = _subscribeId
            }
        }
        return getSubscriptionId(_userId)
    }

    private fun Users.toSubscriptionDto(row: ResultRow) =
        SubscriptionDTO(
            id = row[id],
            username = row[username],
            profilePicture = row[profilePicture]
        )

}