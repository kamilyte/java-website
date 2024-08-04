import Hero from "../components/Hero";

function Home() {
    return(
        <Hero 
            cName="hero"
            heroImg="https://static.vecteezy.com/system/resources/previews/008/721/685/original/continuous-one-line-drawing-music-notes-on-stave-musical-symbol-in-one-linear-minimalist-style-trendy-abstract-wave-melody-outline-sketch-sound-single-line-draw-design-graphic-illustration-vector.jpg"
            title="Welcome"
            text="Web Engineering project by RUG students S4729315 S S"
            button1Url="/SongFilters"
            button2Url="/artists"
            button1Text="View songs"
            button2Text="View artists"
            button2view="show"
            button1view="show"
        />
    );
}

export default Home;