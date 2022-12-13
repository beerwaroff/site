import Cookie from "js-cookie";

const SetCookie = (cookieName, usrin) => {
    Cookie.set(cookieName, usrin, {
        expires: 7,
        secure: true,
        sameSite: 'strict',
        path: '/'
    })
}

export default SetCookie