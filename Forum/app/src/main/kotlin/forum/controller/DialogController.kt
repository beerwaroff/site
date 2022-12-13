package forum.controller
import forum.dto.MessageDTO
import forum.dto.SendMessageDTO
import forum.service.DialogService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/")
@CrossOrigin
class DialogController(private val dialogService: DialogService) {

    @GetMapping("dialogs/{userId}")
    fun getDialogs(@PathVariable userId: Int): Any {
        return dialogService.getDialogs(userId)
    }

    @GetMapping("messages/{dialogId}")
    fun getMessages(@PathVariable dialogId: Int): Any {
        return dialogService.getMessages(dialogId)
    }

    @PostMapping("messages/{dialogId}")
    fun sendMessage(@PathVariable dialogId: Int, @RequestBody dto: SendMessageDTO): Any {
        return dialogService.sendMessage(dialogId, dto)
    }

    @GetMapping("isDialog/{userId}/{subscribeId}")
    fun isDialog(@PathVariable userId: Int, @PathVariable subscribeId: Int): Any {
        return dialogService.isDialog(userId, subscribeId)
    }

    @PostMapping("createDialog/{userId}/{subscribeId}")
    fun createDialog(@PathVariable userId: Int, @PathVariable subscribeId: Int): Any {
        return dialogService.createDialog(userId, subscribeId)
    }

    @GetMapping("getParticipant/{userId}/{subscribeId}")
    fun getParticipant(@PathVariable userId: Int, @PathVariable subscribeId: Int): Any {
        return dialogService.getParticipant(userId, subscribeId)
    }
}