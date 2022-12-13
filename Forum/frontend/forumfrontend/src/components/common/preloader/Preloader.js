import preloader from "./../../../assets/images/Spinner-3.6s-200px.svg"

let Preloader = (props) => {
    return <div style={{backgroundColor: 'white'}}>
            <img src={preloader} />
        </div>
}

export default Preloader;