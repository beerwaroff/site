package forum.service.impl

import forum.dto.DialogDTO
import forum.dto.MessageDTO
import forum.dto.SendMessageDTO
import forum.model.Dialogs
import forum.model.Messages
import forum.model.Participant
import forum.model.Participant.dialogId
import forum.model.Participant.userId
import forum.model.Users
import forum.model.Users.username
import forum.service.DialogService
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.springframework.stereotype.Service
import java.util.*
import java.util.Collections.max
import java.util.Date

@Service
class DialogServiceImpl : DialogService {

    class DialogPreview(_dialog: List<DialogDTO>) {
        val dialog = _dialog
    }

    class MessageData(_messages: List<MessageDTO>) {
        val messages = _messages
    }
    override fun getDialogs(id: Int): Any {

        val dialogs = mutableListOf<Int>()
        transaction {
            Participant.slice(dialogId).select {Participant.userId eq id}.map {
                dialogs += it[dialogId]
            }
        }

        val recipient = mutableListOf<DialogDTO>()
        for (i in dialogs) {
            transaction {
                (Participant.join(Users, JoinType.INNER, additionalConstraint = {Participant.userId eq Users.id}))
                    .slice(dialogId, Participant.userId, username)
                    .select{dialogId eq i and (Participant.userId neq id)}
                    .map {
                        recipient += toDialogDto(it)
                    }
            }
        }

        return DialogPreview(recipient)
    }

    override fun getMessages(dialogId: Int): Any {
        val messages = transaction {
            (Messages.join(Users, JoinType.INNER, additionalConstraint = {Messages.senderId eq Users.id}))
                .slice(Messages.id, Messages.dialogId, Messages.senderId, Messages.message, Users.profilePicture)
                .select{Messages.dialogId eq dialogId}
                .orderBy(Messages.id)
                .map {
                    MessageDTO (
                        id = it[Messages.id],
                        dialogId = it[Messages.dialogId],
                        senderId = it[Messages.senderId],
                        message = it[Messages.message],
                        profilePicture = it[Users.profilePicture]
                    )
                }
        }

        return MessageData(messages)
    }

    override fun sendMessage(dialogId: Int, dto: SendMessageDTO): Any {
        transaction {
            Messages.insert {
                it[this.dialogId] = dto.dialogId
                it[senderId] = dto.senderId
                it[message] = dto.message
            }
        }

        return getMessages(dialogId)
    }

    override fun isDialog(userId: Int, subscribeId: Int): Any {
        val dialogsUserId = mutableListOf<Int>()
        val dialogsSubscribeId = mutableListOf<Int>()

        transaction {
            Participant.slice(dialogId).select { Participant.userId eq userId }.map {
                dialogsUserId += it[dialogId]
            }
        }

        transaction {
            Participant.slice(dialogId).select { Participant.userId eq subscribeId }.map {
                dialogsSubscribeId += it[dialogId]
            }
        }

        var result: Any = false
        loop@
        for (i in dialogsUserId) {
            for (j in dialogsSubscribeId) {
                if (i == j) {
                    result = true
                    break@loop
                }
            }
        }
        return result
    }

    override fun createDialog(userId: Int, subscribeId: Int): Any {
        transaction {
            Dialogs.insert {
                it[Dialogs.created] = DateTime()
            }
        }

        val dialogId = transaction {
            Dialogs.slice(Dialogs.id).selectAll().orderBy(Dialogs.id).map {
                it[Dialogs.id].toInt()
            }
        }.last()

        transaction {
            Participant.insert {
                it[Participant.dialogId] = dialogId
                it[Participant.userId] = userId
            }
        }

        transaction {
            Participant.insert {
                it[Participant.dialogId] = dialogId
                it[Participant.userId] = subscribeId
            }
        }

        return dialogId
    }

    override fun getParticipant(userId: Int, subscribeId: Int): Any {
        val dialogs = mutableListOf<Int>()
        transaction {
            Participant.slice(dialogId).select {Participant.userId eq userId}.map {
                dialogs += it[dialogId]
            }
        }

        val recipient = mutableListOf<Int>()
        for (i in dialogs.indices) {
            transaction {
                (Participant.join(Users, JoinType.INNER, additionalConstraint = {Participant.userId eq Users.id}))
                    .slice(dialogId, Participant.userId, username)
                    .select{dialogId eq dialogs[i] and (Participant.userId neq userId) and (Participant.userId eq subscribeId)}
                    .map {
                        recipient += it[Participant.dialogId].toInt()
                    }
            }
        }

        return recipient[0]
    }
    private fun toDialogDto(row: ResultRow) =
        DialogDTO (
            dialogId = row[dialogId],
            userId = row[userId],
            username = row[username]
        )


}