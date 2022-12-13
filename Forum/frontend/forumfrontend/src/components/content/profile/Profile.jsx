import axios from "axios"
import { useEffect } from "react"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import GetCookie from "../../../Cookie/getCookie"
import RemoveCookie from "../../../Cookie/RemoveCookie"
import { useContext } from "react"
import context from "../../../context";

export default function Profile(props) {

    let handleSubmit = (event) => {
        if (event.target.old_password.value != event.target.old_password_2.value) alert("Пароли не совпадают")
        else {
            event.preventDefault()
            axios.put(`http://localhost:8080/profile/${GetCookie('userId')}`, {        
                    oldPassword: event.target.old_password.value,
                    newPassword: event.target.new_password.value
                }
            ).then(response => {
                alert(response.data)
                if (response.data === "OK") {
                    navigate("../auth")
                    setIsAuth(false)
                    RemoveCookie('userId')
                }
            }).catch(error => alert(error))
        }
        
    }

    let navigate = useNavigate()
    let [profile, setProfile] = useState(null)
    let [isAuth, setIsAuth] = useContext(context)
    const [image, setImage] = useState('')

    useEffect( () => {
        if (GetCookie('userId')) {
            axios
            .get(`http://localhost:8080/users/profile=${GetCookie('userId')}`)
            .then(response => {
                setProfile(response.data)
            })
            
        } else {
            navigate('../auth')
        }
    }, [])
    

    function handleImage(e) {
        setImage(e.target.files[0])
    }

    function uploadImage(e) {
        e.preventDefault()
        const formData = new FormData()
        formData.append('file', image)
        axios.post(`http://localhost:8080/profile-picture/${GetCookie('userId')}`, formData)
        .then(response => {
            try {
                alert(response.data)
            } catch {
                alert(response.data)
            }
        })
    }

    
    return (
        <div>
            {profile ? 
            <div>
                {
                profile.map (p => <div key={p.id}>
                    <div style={{textAlign: "center", paddingTop: "10px"}}><img style={{borderRadius: "100px", border: "3px solid #1c1c1c", boxShadow: "0 0 7px #666", width: "178px", height: "178px" }} src={`data:image/jpeg;base64,${p.profilePicture}`}/></div>
                    <div style={{textAlign: "center", paddingTop: "10px"}}><font size={5}>{p.username}</font></div>
                    <div style={{textAlign: "center"}}><font size={4}>Подписчики: {p.countFollowers}</font></div>
                    <div className="auth">
                        <form enctype='multipart/form-data'>
                            <div className="group"><input type="file" accept="image/*" name="file" onChange={handleImage}/></div>
                            <div className="group"><button type="submit" onClick={uploadImage}>Загрузить фотографию профиля</button></div>
                        </form>
                        
                        <form onSubmit={handleSubmit} >
                            <div className="group"><input type="password" name="old_password" placeholder="Введите старый пароль" /></div>
                            <div className="group"><input type="password" name="old_password_2" placeholder="Введите старый пароль" /></div>
                            <div className="group"><input type="password" name="new_password" placeholder="Новый пароль" /></div>
                            <div className="group"><button type="submit">Изменить пароль</button></div>  
                        </form>
                    </div>
                </div>)
            }
            </div>
            : <div></div>
            }
        </div>
)
} 
    
