import { NavLink } from "react-router-dom"

export default function Login() {
    return (
        <>
          <NavLink to="/auth" style={{color: 'white', textDecoration: 'none'}}>ВОЙТИ</NavLink>
        </>)
}