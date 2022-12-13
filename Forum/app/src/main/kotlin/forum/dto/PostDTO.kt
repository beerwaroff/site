package forum.dto

data class PostDTO (
    val id: Int? = null,
    val name: String,
    val userId: Int,
    val username: String,
    val text: String
)

