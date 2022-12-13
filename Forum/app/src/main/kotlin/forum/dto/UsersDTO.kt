package forum.dto

data class UsersDTO(
    val id: Int? = null,
    val username: String,
    val profilePicture: ByteArray?,
    val countFollowers: Int,
    val status: String
)
