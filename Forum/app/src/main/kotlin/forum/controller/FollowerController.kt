package forum.controller
import forum.service.FollowersService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
@CrossOrigin
class FollowerController(private val followersService: FollowersService) {

    @GetMapping("subscription/{userId}")
    fun getSubscription(@PathVariable userId: Int): Any {
        return try {
            followersService.getSubscription(userId)
        } catch (e: org.jetbrains.exposed.exceptions.ExposedSQLException) {
            HttpStatus.CONFLICT
        }
    }


    @DeleteMapping("subscription/{userId}/{subscribeId}")
    fun unsubscribe(@PathVariable userId: Int, @PathVariable subscribeId: Int): Any {
        return try {
            followersService.unsubscribe(userId, subscribeId)
        } catch (e: org.jetbrains.exposed.exceptions.ExposedSQLException) {
            HttpStatus.CONFLICT
        }
    }

    @PostMapping("subscription/{userId}/{subscribeId}")
    fun subscribe(@PathVariable userId: Int, @PathVariable subscribeId: Int): Any {
        return try {
            followersService.subscribe(userId, subscribeId)
        } catch (e: org.jetbrains.exposed.exceptions.ExposedSQLException) {
            HttpStatus.CONFLICT
        }
    }
}