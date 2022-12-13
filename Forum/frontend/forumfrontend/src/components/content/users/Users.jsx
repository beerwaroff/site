import axios from "axios";
import React from "react";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import styles from './users.module.css';
import { useContext } from "react";
import context from "../../../context";
import GetCookie from "../../../Cookie/getCookie";


let Users = (props) => {
    const [users, setUsers] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalUsersCount, setTotalUsersCount] = useState(0);
    const [pageSize, setPageSize] = useState(32);
    let [isAuth, setIsAuth] = useContext(context)
    let navigate = useNavigate()
    const [subscriptions, setSubscriptions] = useState([])

    useEffect( () => {
  
        if (isAuth) {
            axios
                .get(`http://localhost:8080/users/${GetCookie('userId')}/${currentPage}`)
                .then(response => {
                    setUsers(response.data.accounts)
                    setTotalUsersCount(response.data.count)
                    setSubscriptions(response.data.subscriptions)
                    console.log(users)
                })             
            } else {
                navigate('../auth')
        }
    }, [])
    
    
    let onPageChanged = (pageNumber) => {
        setCurrentPage(pageNumber);
        axios
            .get(`http://localhost:8080/users/${GetCookie('userId')}/${pageNumber}`)
            .then(response => {
                setUsers(response.data.accounts);
        });
    }

    
    
    let unsubscribe = (subscribeId) => {
        axios
            .delete(`http://localhost:8080/subscription/${GetCookie('userId')}/${subscribeId}`)
            .then(response => {
                setSubscriptions(response.data)
            })
    }

    let subscribe = (subscribeId) => {
        axios
            .post(`http://localhost:8080/subscription/${GetCookie('userId')}/${subscribeId}`)
            .then(response => {
                setSubscriptions(response.data)
            })
    }

    let statusDown = (id) => {
        axios
            .post(`http://localhost:8080/status-down/${id}`)
            .then(response => {
                alert(response.data)
            })       
    }

    let statusUp = (id) => {
        axios
        .post(`http://localhost:8080/status-up/${id}`)
        .then(response => {
            alert(response.data)
        })          
    }
    let pages = []

    for (let i=1; i <= Math.ceil (totalUsersCount / pageSize); i++) {
        pages.push(i)
    }

    if (isAuth === true) {
        return (
            <div>
                { pages.map 
                    (p => {
                        return <span className={currentPage === p && styles.selectedPage}
                        onClick={(e)=>{onPageChanged(p)}}>{p}</span>
                    }) 
                }

                { users.map 
                    (u => <div>
                        <div>
                            <div className="users">
                                <div className="usersCard"><img style={{borderRadius: "100px", border: "3px solid #1c1c1c", boxShadow: "0 0 7px #666", width: "89px", height: "89px"}} src={`data:image/jpeg;base64,${u.profilePicture}`} /></div>
                                <div className="usersCard"><font size={3}>{u.username}</font></div>
                                <div className="usersCard"><font size={3}>Подписчики: {u.countFollowers}</font></div>
                                {subscriptions.includes(u.id) ? <button onClick={unsubscribe.bind(null, u.id)} className="subscr">Отписаться</button> : <button onClick={subscribe.bind(null, u.id)} className="subscr">Подписаться</button>}
                                {GetCookie('role') === 'admin'?
                                <form>
                                {u.status === "active" ? <button onClick={statusDown.bind(null, u.id)} className="subscr">Заблокировать</button> : <button onClick={statusUp.bind(null, u.id)} className="subscr">Разблокировать</button>}
                                </form> : null} 
                            </div>
                        </div></div>
                   )
                }   
            </div>
        )
        } else navigate("../auth")
    }

export default Users
