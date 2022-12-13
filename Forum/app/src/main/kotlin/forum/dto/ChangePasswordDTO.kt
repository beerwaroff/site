package forum.dto

data class ChangePasswordDTO(
    val newPassword: String,
    val oldPassword: String
)
