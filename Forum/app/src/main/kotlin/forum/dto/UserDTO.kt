package forum.dto

data class UserDTO(
        val id: Int? = null,
        val username: String,
        val password: String,
        val profilePicture: ByteArray?,
        val status: String,
        val role: String
)
