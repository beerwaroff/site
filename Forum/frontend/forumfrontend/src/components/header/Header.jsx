import Login from "./Login"
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import SchoolIcon from '@mui/icons-material/School';
import { NavLink } from 'react-router-dom';

const Header = (props) => {
    return (
        <header>
            <Box sx={{ flexGrow: 1 }}>
                <AppBar style={{backgroundColor: '#1c1c1c'}}>
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                    <SchoolIcon />   
                    БелыеХакеры
                    </Typography>
                    
                    { props.isAuth ? <div><NavLink to="/profile" style={{color: 'white', textDecoration: 'none'}}>{props.username}</NavLink>
                                    <button onClick={props.exit} style={{background: 'none', border: 'none', color: 'white', textDecoration: 'none'}}>ВЫЙТИ</button>
                                </div> : <Login /> }
                </Toolbar>
                </AppBar>
            </Box>
        </header>
    )
}

export default Header