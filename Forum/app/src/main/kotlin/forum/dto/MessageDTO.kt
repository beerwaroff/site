package forum.dto


data class MessageDTO(
    val id: Int? = null,
    val dialogId: Int,
    val senderId: Int,
    val message: String,
    val profilePicture:ByteArray
)
