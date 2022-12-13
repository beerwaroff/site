package forum.controller

import forum.dto.ChangePasswordDTO
import forum.service.UserService
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.net.URI

@RestController
@RequestMapping("/")
@CrossOrigin
class UserController(private val userService: UserService) {

    private val uploadPath= "C:/Forum/images"

    @GetMapping("users/{id}/{page}")
    fun getUsers(@PathVariable id: Int, @PathVariable page: Int): Any {
        return userService.getUsers(id, page)
    }

    @GetMapping("users/profile={profile}")
    fun getProfile(@PathVariable profile: Int): Any {
        return userService.getProfile(profile)
    }

    @PutMapping("profile/{id}")
    fun changePassword(@PathVariable id: Int, @RequestBody dto: ChangePasswordDTO): Any {
        return try {
            userService.changePassword(id, dto)
        } catch (e: org.jetbrains.exposed.exceptions.ExposedSQLException) {
            HttpStatus.CONFLICT
        }
    }

    @GetMapping("username/{id}")
    fun getUsername(@PathVariable id: Int): String {
        return userService.getUsername(id)
    }

    @PostMapping(value= ["/profile-picture/{id}"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun setProfilePicture(@PathVariable("id") id: Int, @RequestParam file: MultipartFile): Any {
        return try {
            userService.setProfilePicture(id, file)

        } catch (e: FileSizeLimitExceededException) {
            "Файл слишком большой, выберите другой"
        }
    }

    @GetMapping("/profile-picture/{id}")
    fun getProfilePicture(@PathVariable("id") id: Int): Any{
        return try {
            userService.getProfilePicture(id)
        } catch(error: NoSuchElementException){
            "error"
        }
    }

    @PostMapping("/status-up/{id}")
    fun statusUp(@PathVariable("id") id: Int): Any {
        return try {
            userService.statusUp(id)
        } catch(error: NoSuchElementException){
            "error"
        }
    }

    @PostMapping("/status-down/{id}")
    fun statusDown(@PathVariable("id") id: Int): Any {
        return try {
            userService.statusDown(id)
        } catch(error: NoSuchElementException){
            "error"
        }
    }
}

