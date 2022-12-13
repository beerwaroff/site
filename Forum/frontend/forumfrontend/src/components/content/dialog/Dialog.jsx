import axios from 'axios';
import { useEffect } from 'react';
import { useContext, useState } from 'react';
import { NavLink, Route, useNavigate } from 'react-router-dom';
import context from '../../../context';
import GetCookie from '../../../Cookie/getCookie';

export default function Dialog(props) {
    let isAuth = useContext(context)
    let navigate = useNavigate()
    
    const[dialogs, setDialogs] = useState([])

    useEffect( () => {
        if (GetCookie('userId')) {
            axios
                .get(`http://localhost:8080/dialogs/${GetCookie('userId')}`)
                .then(response => {
                    setDialogs(response.data.dialog)
                })
                
        } else {
            navigate('../auth')
        }
    }, [])

    return (
        <div> 
            {dialogs.map (d => <div >
                <button className='openDialog'>
                    <NavLink  to={'../dialog/' + String(d.dialogId)} style={{color: '#1c1c1c', textDecoration: "none", float: "left"}}>
                        <font size={5}>{d.username}</font>
                    </NavLink>
                </button>
                </div>)}
            <form>
                
            </form>
        </div>
    )
}