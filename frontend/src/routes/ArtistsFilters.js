import {useEffect, useState} from "react";
import Hero from "../components/Hero";
import Songs from "./Songs";
import '../components/filters.css' ;
import {Link, useNavigate} from 'react-router-dom';



function ArtistsFilters() {
    const [lim, setLimit] = useState("");
    const [id, setId] = useState("");
    const navigate = useNavigate();



    let handleSubmit = (e) => {
        e.preventDefault();
        //console.log(lim);
        navigate('/artists', {state: {limit: lim}});
    }


    return(
        <div>
            <Hero
                cName="hero"
                heroImg="https://static.vecteezy.com/system/resources/previews/008/721/685/original/continuous-one-line-drawing-music-notes-on-stave-musical-symbol-in-one-linear-minimalist-style-trendy-abstract-wave-melody-outline-sketch-sound-single-line-draw-design-graphic-illustration-vector.jpg"
                title="Select filters"
                button2view="hide"
                button1View="hide"
            />
            <form onSubmit={handleSubmit}>
                <div class="filters">
                    <select type="text" onChange={(e) => setLimit(e.target.value)}>
                        <option value="">Select limit</option>
                        <option value="10">10</option>
                        <option value="20">20</option>
                        <option value="50">50</option>
                        <option value="100">100</option>
                    </select>
                    <div>
                        <button type="submit"> View </button>
                    </div>
                </div>
            </form>

        </div>
    );
}

export default ArtistsFilters;