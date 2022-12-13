package forum.controller
import forum.dto.AuthDTO
import forum.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/")
@CrossOrigin
class AuthController(private val authService: AuthService) {

    @PostMapping("authorization")
    fun authorization(@RequestBody dto: AuthDTO): Any {
        return try {
            authService.authorization(dto)
        } catch (e: org.jetbrains.exposed.exceptions.ExposedSQLException) {
            HttpStatus.CONFLICT
        }
    }

    @PostMapping("registration")
    fun registration(@RequestBody dto: AuthDTO): Any {
        return try {
            authService.registration(dto)
        } catch (e: org.jetbrains.exposed.exceptions.ExposedSQLException) {
            HttpStatus.CONFLICT
        }
    }

    @PostMapping("registration-admin")
    fun regAdmin(@RequestBody dto: AuthDTO): Any {
        return try {
            authService.registrationAdmin(dto)
        } catch (e: org.jetbrains.exposed.exceptions.ExposedSQLException) {
            HttpStatus.CONFLICT
        }
    }
}