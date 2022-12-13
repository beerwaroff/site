package forum.dto


data class SendMessageDTO(
    val id: Int? = null,
    val dialogId: Int,
    val senderId: Int,
    val message: String,
)
