import { Route, Routes } from "react-router-dom";
import Authorization from "./auth/Authorization";
import Discuss from "./discuss/Discuss";
import Profile from "./profile/Profile";
import Registr from "./registr/Registr";
import Dialog from "./dialog/Dialog";
import Users from "./users/Users";
import Subscription from "./subscription/Subscriptions";
import Messages from "./dialog/Messages";
import Post from "./discuss/Post";

export default function Content(props) {
    return (
        <div>
            <Routes >
                <Route path="/dialog/" element={<Dialog />} />
                <Route path="/dialog/:dialogId" element={<Messages createDialog={false}/>} />
                <Route path="/create_dialog/:subscribe_id" element={<Messages createDialog={true}/>} />
                <Route path="/profile" element={<Profile cookie={props.cookie}/>} />
                <Route path="/discuss/" element={<Discuss />} />
                <Route path="/discuss/:postId" element={<Post />} />
                <Route path="/users/" element={ <Users/>} />               
                <Route path="/auth" element={<Authorization className="auth"/>} />
                <Route path="/registr" element={<Registr />} />
                <Route path="/subscription" element={<Subscription />} />
                
            </Routes>
            <div>
                
            </div>
        </div>
    )
}