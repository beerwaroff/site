import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import HeaderContainer from './components/header/HeaderContainer';
import NavigationBar from './components/navbar/NavigationBar';
import Content from './components/content/Content';

import { useState } from 'react';
import context from './context';
import { useEffect } from 'react';

function App(props) {

  const [isAuth, setIsAuth] = useState(props.auth)
  const [role, setRole] = useState('')
  const [online, setOnline] = useState(props.online)
 
  return (
    
    <div className='wrapper'>

        <context.Provider value={[isAuth, setIsAuth]} role={[role, setRole]} online={[online, setOnline]}>
          <BrowserRouter >
            <div className='head'>
              <HeaderContainer/>
            </div>
            <div className='navigation'>
              <NavigationBar />
            </div>
            <div className='content'>
              <Content />
            </div>
          </BrowserRouter>
        </context.Provider>
      
    </div>
  );
}

export default App;