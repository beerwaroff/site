package forum.controller
import forum.dto.CommentsDTO
import forum.dto.PostDTO
import forum.service.DiscussService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
@CrossOrigin
class DiscussController(private val discussService: DiscussService) {

    @GetMapping("getPosts")
    fun getPosts(): Any {
        return discussService.getPosts()
    }

    @GetMapping("getPost/{postId}")
    fun getPost(@PathVariable postId: Int): Any {
        return discussService.getPost(postId)
    }

    @PostMapping("sendComment/{postId}")
    fun sendComment(@PathVariable postId: Int, @RequestBody dto: CommentsDTO): Any {
        return discussService.sendComment(postId, dto)
    }

    @PostMapping("createPost")
    fun createPost(@RequestBody dto: PostDTO): Any {
        return discussService.createPost(dto)
    }

    @PostMapping("deletePost/{postId}")
    fun deletePost(@PathVariable postId: Int): Any {
        return discussService.deletePost(postId)
    }

}