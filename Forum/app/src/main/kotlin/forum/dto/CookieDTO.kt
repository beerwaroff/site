package forum.dto

data class CookieDTO(
    val id: Int? = null,
    val username: String,
    val password: String,
    val role: String,
    val status: String
)
