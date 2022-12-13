import * as React from 'react';
import { useContext, useState, useEffect } from 'react';
import context from '../../context';
import RemoveCookie from '../../Cookie/RemoveCookie';
import GetCookie from '../../Cookie/getCookie';
import axios from 'axios';
import Header from './Header';
import { useNavigate } from 'react-router-dom';

export default function HeaderContainer() {
  let [isAuth, setIsAuth] = useContext(context)
  let [user, setUser] = useState('')
  
  const navigate = useNavigate()

  if (GetCookie('userId')) {
    setIsAuth(true)
  } 

  let exit = () => {
    RemoveCookie('userId')
    RemoveCookie('role')
    RemoveCookie('status')
    setIsAuth(false)
    navigate('./auth')
  }

  
  useEffect( () => {
    if (GetCookie('userId')) {
      axios
      .get(`http://localhost:8080/users/profile=${GetCookie('userId')}`)
      .then(response => {
          setUser(response.data)
      })
    } 
  }, [isAuth])

  let username
  if (user !== '') {
    username = user.map (u => u.username)
  } else {
    username = null
  }

  return (
    <Header exit={exit} isAuth={isAuth} username={username}/> 
  );
 
  
}