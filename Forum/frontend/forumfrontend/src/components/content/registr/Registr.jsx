import axios from "axios"
import { useNavigate } from "react-router-dom"

export default function Registr() {

    const navigate = useNavigate()

    let handleSubmit = (event) => {
        event.preventDefault()
        if (!event.target.username.value || !event.target.password.value) alert("Заполните все поля *")
        if ( event.target.password.value === event.target.repeatpassword.value) {
            axios.post(`http://localhost:8080/registration`, {
                username: event.target.username.value,
                password: event.target.password.value,
            }).then(response => {
                if (response.data === "CONFLICT") {
                    alert ("Введенные вами имя пользователя или почта уже используется")
                } else {
                    navigate('../auth')
                    alert ("Регистрация прошла успешно. Войдите в систему.")
                }
            })
        } else {
            alert("Пароли не совпадают")
        }
    }

    return (
        <div className="auth">
            <h1>Регистрация</h1>
            <form onSubmit={handleSubmit}>
                <div className="group"><input type="text" name="username" placeholder="Имя пользователя*" /></div>
                <div className="group"><input type="password" name="password" placeholder="Пароль*" /></div>
                <div className="group"><input type="password" name="repeatpassword" placeholder="Пароль*" /></div>
                <div className="group"><button type="submit" name="send">Зарегистрироваться</button></div>
            </form>
            <p style={{textAlign: 'center'}}>* - обязательны для заполнения</p>
        </div>
    );
}