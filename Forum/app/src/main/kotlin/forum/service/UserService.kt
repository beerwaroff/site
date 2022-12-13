package forum.service

import forum.dto.ChangePasswordDTO
import org.springframework.web.multipart.MultipartFile


interface UserService {

    fun getUsers(id: Int, page: Int) = object {}

    fun getProfile(id: Int) = object {}

    fun changePassword(id: Int, dto: ChangePasswordDTO): Any

    fun getUsername(id: Int): String

    fun setProfilePicture(id: Int, file: MultipartFile): Any

    fun getProfilePicture(id: Int): ByteArray

    fun statusUp(id: Int): Any

    fun statusDown(id: Int): Any
}