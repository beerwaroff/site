import axios from "axios"
import { useContext } from "react"
import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"
import context from "../../../context"
import { NavLink } from "react-router-dom"
import GetCookie from "../../../Cookie/getCookie"
import { SportsEsportsOutlined } from "@mui/icons-material"

export default function Post() {
    let [post, setPost] = useState([])
    let [comments, setComments] = useState([])
    let [isAuth, setIsAuth] = useContext(context)
    const navigate = useNavigate()
    const params = useParams()
    const [uname, setUname] = useState()
    const [postId, setPostId] = useState()

    useEffect( () => {
        axios
            .get(`http://localhost:8080/username/${GetCookie('userId')}`)
            .then(response => [
                setUname(response.data)
            ])
        if (isAuth) {
            axios
                .get(`http://localhost:8080/getPost/${params.postId}`)
                .then(
                    response => {
                        setPost(response.data.post)
                        setComments(response.data.comments)
                        setPostId(response.data.postId)
                    }
                )
        } else {
            navigate("../auth")
        }
    }, [setComments])

    let sendComment = (event) => {
        
        if (event.target.comment.value) {
            event.preventDefault()
            axios.post(`http://localhost:8080/sendComment/${params.postId}`, {
                userId: GetCookie('userId'),
                username: uname,
                postId: postId,
                text: event.target.comment.value
            }).then(response => {
                setComments(response.data.comments)
            })
            event.target.reset(); 
        }
    }

    const deletePost = (e) => {
        e.preventDefault()
            axios
                .post(`http://localhost:8080/deletePost/${params.postId}`)
                .then(response => {
                    alert(response.data)
                    navigate('../discuss')
                })

    }


    if (comments === []) {
        return (
            <div>
            {post.map(p =>
            <div>
                <h3 style={{textAlign: "center"}}><span style={{marginRight: "50px", marginTop: "20px"}}>{p.name}</span></h3>
                <div style={{marginLeft: "20px"}}><font size={5}>{p.username}: {p.text}</font></div>
            </div>
   
            )}
            <div style={{marginTop: "20px"}} className="PostBox">

            
            </div>
            <form onSubmit={sendComment} className="comment">
                <input type="textarea" name="comment"/>
                <br />
                <button type="submit" name="send"><font size='4'>Отправить</font></button>
            </form>
        </div>
        )
    } else {
        return (
            <div>
                {post.map(p =>
                <div>
                    <h3 style={{textAlign: "center"}}><span style={{marginRight: "50px", marginTop: "20px"}}>{p.name}</span></h3>
                    <div style={{marginLeft: "20px"}}><font size={5}>{p.username}: {p.text}</font></div>
                </div>
    
                )}
                <div style={{marginTop: "20px"}} className="PostBox">

                    {comments.map(c =>
                        <div>
                            <div style={{marginLeft: "50px"}}><font size={4.5}>{c.username}: {c.text}</font></div>
                        </div>
                    )}
                </div>
                <form onSubmit={sendComment} className="comment">
                    <input type="textarea" name="comment"/>
                    <br />
                    <button type="submit" name="send"><font size='4'>Отправить</font></button>
                    {GetCookie('role') === 'admin'? 
                    <form onClick={deletePost}>
                        <button type="button" name="send"><font size='4'>Удалить пост</font></button>
                    </form> 
                    : null}
                </form>
                
            </div>
        )}
}