import axios from "axios"
import { useEffect } from "react"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import GetCookie from "../../../Cookie/getCookie"
import { useContext } from "react"
import context from "../../../context";
import React from "react"
export default function Subscription(props) {

    let navigate = useNavigate()
    let [profile, setProfile] = useState(null)
    let [isAuth, setIsAuth] = useContext(context)
    let [subscriptions, setSubscriptions] = useState([])
    let [dId, setDId] = useState()
    let [isDialog, setIsDialog] = useState() 
    let [isLoading, setIsLoading] = useState()

    useEffect( () => {
        if (isAuth) {
            axios
                .get(`http://localhost:8080/subscription/${GetCookie('userId')}`)
                .then(response => {
                    setSubscriptions(response.data)
                })
            
        } else {
            navigate('../auth')
        }
    }, [isAuth])
    
    let unsubscribe = (subscribeId) => {
        axios
            .delete(`http://localhost:8080/subscription/${GetCookie('userId')}/${subscribeId}`)
            .then(response => {
                setSubscriptions(response)
            })
    }

    let dialog = (subscribeId) => {
        setIsLoading(false)
        checkDialog(subscribeId)
        if (isLoading) {
            axios
            .get(`http://localhost:8080/getParticipant/${GetCookie('userId')}/${subscribeId}`)
            .then(response => {
                navigate(`../dialog/${response.data}`)
            })
        }

    }

    function checkDialog(subscribeId) {
        axios
            .get(`http://localhost:8080/isDialog/${GetCookie('userId')}/${subscribeId}`)
            .then(response => {
                setIsDialog(response.data)
            }
            )
        
        if (isDialog === false) {
            axios
                .post(`http://localhost:8080/createDialog/${GetCookie('userId')}/${subscribeId}`)
                .then(response => { 
                    setDId(response.data)
                    setIsDialog(true)
                })
        }
        setIsLoading(true)
    }

    return (
        <div>             
            {subscriptions.map (s => 
            <div className="users">
                <div className="usersCard"><img style={{borderRadius: "100px", border: "3px solid #1c1c1c", boxShadow: "0 0 7px #666", width: "89px", height: "89px" }} src={`data:image/jpeg;base64,${s.profilePicture}`}/></div>
                <div className="usersCard"><font size={3}>{s.username}</font></div>
                <div><button onClick={unsubscribe.bind(null, s.id)} className="subscr">Отписаться</button></div>
                <div><button onClick={dialog.bind(null, s.id)} className="subscr">Написать сообщение</button></div>
            </div>)}
        </div>
    )
} 
    
