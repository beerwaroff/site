import axios from "axios";
import React, { useState } from "react";
import { useContext } from "react";
import context from "../../../context";
import { useNavigate } from "react-router-dom";
import SetCookie from "../../../Cookie/setCookie";
import GetCookie from "../../../Cookie/getCookie";
import RemoveCookie from "../../../Cookie/RemoveCookie";

const Authorization = (props) => {

    const navigate = useNavigate();

    const [isAuth, setIsAuth] = useContext(context)
    const [role, setRole] = useContext(context)
    
    let registration = () => {
        navigate('../registr')
    }

    let handleSubmit = (event) => {
        if (!event.target.username.value || !event.target.password.value) alert("Заполните все поля")
        else {
            event.preventDefault()
            axios.post(`http://localhost:8080/authorization`, {
                username: event.target.username.value,
                password: event.target.password.value
            }).then(response => {
                if (response.data != 0) {
                    if (GetCookie('useId')) RemoveCookie('userId')
                    if (GetCookie('role')) RemoveCookie('role')
                    if (GetCookie('status')) RemoveCookie('status')
                    SetCookie('userId', response.data.id)
                    SetCookie('role', response.data.role)
                    SetCookie('status', response.data.status)
                    setIsAuth(true)
                    if (GetCookie('status') === 'blocked') {
                        RemoveCookie('userId')
                        RemoveCookie('role')
                        RemoveCookie('status')
                        setIsAuth(false)
                        alert('Аккаунт заблокирован.')
                    }
                    setRole(GetCookie('role'))
                    navigate("../profile")
                } else {
                    alert("Неправильно введен пароль  или имя пользователя")
                } 
            })
        }

    }
    return (
        <div className="auth">
            <h1>Авторизация</h1>
            <form onSubmit={handleSubmit} >
                <div className="group"><input type="text" name="username" placeholder="Имя пользователя" /></div>
                <div className="group"><input type="password" name="password" placeholder="Пароль" /></div>
                <div className="group"><button type="submit">Войти</button></div>
                <div className="group"><button onClick={registration}>Зарегистрироваться</button></div>
            </form>
        </div>
    )
}

export default Authorization