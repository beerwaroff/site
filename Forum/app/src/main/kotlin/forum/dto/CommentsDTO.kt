package forum.dto

data class CommentsDTO(
    val id: Int? = null,
    val userId: Int,
    val username: String,
    val postId: Int,
    val text: String
)
