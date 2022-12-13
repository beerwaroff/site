import {NavLink} from "react-router-dom";

export default function NavigationBar() {
    return (
        <nav>
            <p><NavLink to="/profile" style={{color: 'white', textDecoration: "none"}}>Профиль</NavLink></p>
            <p><NavLink to="/subscription" style={{color: 'white', textDecoration: "none"}}>Подписки</NavLink></p>
            <p><NavLink to="/dialog" style={{color: 'white', textDecoration: "none"}}>Сообщения</NavLink></p>
            <p><NavLink to="/discuss" style={{color: 'white', textDecoration: "none"}}>Обсуждения</NavLink></p>
            <p><NavLink to="/users" style={{color: 'white', textDecoration: "none"}}>Пользователи</NavLink></p>
        </nav>
    )
}