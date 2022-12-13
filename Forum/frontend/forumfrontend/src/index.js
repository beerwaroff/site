import './index.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { Provider } from 'react-redux';
import GetCookie from './Cookie/getCookie';

let cookie = null

if (GetCookie('userId')) {
    cookie = GetCookie('userId')
    
}

let auth = cookie ? true : false
const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <App auth={auth} online={false}/>
);
reportWebVitals();