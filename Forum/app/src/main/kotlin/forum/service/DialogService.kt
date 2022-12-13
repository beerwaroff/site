package forum.service

import forum.dto.MessageDTO
import forum.dto.SendMessageDTO

interface DialogService {

    fun getDialogs(id: Int): Any

    fun getMessages(dialogId: Int): Any

    fun sendMessage(dialogId: Int, dto: SendMessageDTO): Any

    fun isDialog(userId: Int, subscribeId: Int): Any

    fun createDialog(userId: Int, subscribeId: Int): Any

    fun getParticipant(userId: Int, subscribeId: Int): Any
}