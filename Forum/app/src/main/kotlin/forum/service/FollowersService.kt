package forum.service

interface FollowersService {

    fun getSubscriptionId(userId: Int): MutableList<Int>

    fun getSubscription(userId: Int): Any

    fun unsubscribe(userId: Int, subscribeId: Int): Any

    fun subscribe(_userId: Int, _subscribeId: Int): Any


}