import axios from "axios"
import React, { useEffect, useState } from "react"
import { useParams } from "react-router-dom"
import GetCookie from "../../../Cookie/getCookie"
import { Avatar } from "@mui/material";
import { ChatBox, ReceiverMessage, SenderMessage } from "mui-chat-box";
import { useRef } from "react";


export default function Messages() {
    const params = useParams()
    let [messages, setMessages] = useState([])

    useEffect( () => {
        axios
            .get(`http://localhost:8080/messages/${params.dialogId}`)
            .then(response => {
                setMessages(response.data.messages)
            })
    }, [])

    let handleSend = (event) => {
        if (event.target.message.value) {
            event.preventDefault()
            axios.post(`http://localhost:8080/messages/${params.dialogId}`, {
                dialogId: params.dialogId,
                senderId: GetCookie('userId'),
                message: event.target.message.value
            }).then(response => {
                setMessages(response.data.messages)
            })
            event.target.reset(); 
        }
    }
    if (messages === []) {
        return (
        <div>
            <form onSubmit={handleSend} className="sender">
                <input type="textarea" name="message"/>
                <br />
                <button type="submit" name="send"><font size='4'>Отправить</font></button>
            </form>
        </div>
        )
    } else {
        return (
            <div>
                <div className="chatbox">
                    <ChatBox>
                        {messages.map (m => String(m.senderId) === String(GetCookie('userId')) ? 
                        <SenderMessage avatar={<Avatar><img src={`data:image/jpeg;base64,${m.profilePicture}`} ></img></Avatar>}>
                            <font size='3' color="#1c1c1c">{m.message}</font>
                        </SenderMessage> :
                        <ReceiverMessage avatar={<Avatar><img src={`data:image/jpeg;base64,${m.profilePicture}`}></img></Avatar>}>
                            <font size='3' color="#1c1c1c">{m.message}</font>
                        </ReceiverMessage>)}
                    </ChatBox>
                </div>
                <form onSubmit={handleSend} className="sender">
                    <textarea name="message"/>
                    <br />
                    <button type="submit" name="send"><font size='4'>Отправить</font></button>
                </form>
            </div>
        )
    }
    
}