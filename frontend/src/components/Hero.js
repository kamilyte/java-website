import "./HeroStyles.css";

function Hero(props) {
    return(
        <div className={props.cName}>
            <img alt="heroImg" src={props.heroImg}/>
            <div className="hero-text">
                <h1>{props.title}</h1>
                <p>{props.text}</p>
                <a href= {props.button1Url} className={props.button1view}>
                    {props.button1Text}
                </a>
                <a href={props.button2Url} className={props.button2view}>
                    {props.button2Text}
                </a>
                
            </div>
        </div>
    );
}

export default Hero;