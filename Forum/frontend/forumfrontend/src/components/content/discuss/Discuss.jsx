import axios from "axios"
import { useContext } from "react"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import context from "../../../context"
import { NavLink } from "react-router-dom"
import GetCookie from "../../../Cookie/getCookie"

export default function Discuss() {
    let [posts, setPosts] = useState([])
    let [comments, setComments] = useState([])
    let [isAuth, setIsAuth] = useContext(context)
    const navigate = useNavigate()
    let [uname, setUname] = useState()
    useEffect( () => {
        if (isAuth) {
            axios
                .get(`http://localhost:8080/getPosts`)
                .then(
                    response => {
                        setPosts(response.data)
                    }
                )
        } else {
            navigate("../auth")
        }
    }, [])

    let createPost =(event) => {
        
        axios
            .get(`http://localhost:8080/username/${GetCookie('userId')}`)
            .then(response => [
                setUname(response.data)
            ])
        event.preventDefault()
        axios 
            .post(`http://localhost:8080/createPost`, {
                name: event.target.namePost.value,
                userId: GetCookie('userId'),
                username: uname,
                text: event.target.textPost.value
            })
            .then(
                response => {
                    setPosts(response.data)
                }
            )
        event.target.reset(); 
    }
    return (
        <div>
            {posts.map(p =>
            <button className='openDiscuss'>
                <NavLink  to={'../discuss/' + String(p.id)} style={{color: '#1c1c1c', textDecoration: "none", float: "left"}}>
                    <font size={5}>{p.username}: {p.name}</font>
                </NavLink>
            </button>
            )}

            <form onSubmit={createPost} className="post">
                <input type="textarea" name="namePost" placeholder="Тема обсуждения"/>
                <br />
                <textarea name="textPost" placeholder="Введите текст" className="textPost"/>
                <br />
                <button type="submit" name="send"><font size='4'>Создать</font></button>
            </form>
        </div>
    )
}