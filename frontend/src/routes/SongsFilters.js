import {useEffect, useState} from "react";
import Hero from "../components/Hero";
import Songs from "./Songs";
import '../components/filters.css' ;
import {Link, useNavigate} from 'react-router-dom';
import Form from 'react-bootstrap/Form';




function SongFilters() {
    const [songs, setSongs] = useState([])
    const [lim, setLimit] = useState("");
    const [orderBy, setOrderBy] = useState("");
    const [orderDir, setOrderDir] = useState("");
    const [title, setTitle] = useState("");
    const [year, setYear] = useState("");
    const [songID, setSongID] = useState("");
    const navigate = useNavigate();
    const [isDisabled, setIsDisabled] = useState(false);
    const [isDisabledYear, setIsDisabledYear] = useState(false);
    const [isDisabledFields, setIsDisabledFields] = useState(false);

    
    
    let handleSubmit = (e) => {
        e.preventDefault();
        //console.log(lim);
        navigate('/songs', {state: {
            limit: lim, 
            orderBy: orderBy,
            orderDir: orderDir,
            title: title,
            year: year,
            songID: songID,
        }});
    }


    return(
        <div>
            <Hero 
            cName="hero"
            heroImg="https://static.vecteezy.com/system/resources/previews/008/721/685/original/continuous-one-line-drawing-music-notes-on-stave-musical-symbol-in-one-linear-minimalist-style-trendy-abstract-wave-melody-outline-sketch-sound-single-line-draw-design-graphic-illustration-vector.jpg"
            title="Select filters"
            text="Find songs by title, name, sort by yearly popularity, release date, alphabetical order, etc."
            button2view="hide"
            button1View="hide"
            />
            <Form className='filters' onSubmit={handleSubmit}>
                <ul>
                <li class="form-group">
                    <input class="form-control" disabled={isDisabledYear} type="text" placeholder="Song ID" defaultValue={""} onChange={(e) => {
                        setIsDisabledFields(!isDisabledFields)
                        setIsDisabled(!isDisabled);
                        setSongID(e.target.value)}
                    }/>
                </li>
                <li class="form-group">
                    <select class="form-control" disabled={isDisabled} type="text" onChange={(e) => {
                        setIsDisabledYear(!isDisabledYear);
                        setIsDisabledFields(!isDisabledFields)
                        setYear(e.target.value)}}>
                        <option value="">Popularity by Year</option>
                        <option value="2020">2020</option>
                        <option value="2019">2019</option>
                        <option value="2018">2018</option>
                        <option value="2017">2017</option>
                        <option value="2016">2016</option>
                        <option value="2015">2015</option>
                        <option value="2014">2014</option>
                        <option value="2013">2013</option>
                        <option value="2012">2012</option>
                        <option value="2011">2011</option>
                        <option value="2010">2010</option>
                    </select>
                </li>
                <li class="form-group">
                    <input class="form-control" disabled={isDisabledYear} type="text"  placeholder="Song Title" defaultValue={""} onChange={(e) => {
                        setIsDisabledFields(!isDisabledFields)
                        setIsDisabled(!isDisabled);
                        setTitle(e.target.value)}
                    }/>
                </li>
                <li class="form-group">
                    <select class="form-control" disabled={isDisabledFields} type="text" onChange={(e) => setOrderBy(e.target.value)}>
                        <option value="">Order By</option>
                        <option value="TITLE">Title</option>
                        <option value="POPULARITY">Popularity</option>
                        <option value="RELEASE_DATE">Release Date</option>
                    </select>
                </li>
                <li class="form-group">
                    <select class="form-control" disabled={isDisabledFields} type="text" onChange={(e) => setOrderDir(e.target.value)}>
                        <option value="">Direction</option>
                        <option value="asc">Ascending</option>
                        <option value="desc">Descending</option>
                    </select>
                </li>
                <li class="form-group">
                    <select disabled={isDisabled} class="form-control" type="text" onChange={(e) => setLimit(e.target.value)}>
                        <option value="">Select limit</option>
                        <option value="10">10</option>
                        <option value="20">20</option>
                        <option value="50">50</option>
                        <option value="100">100</option>
                    </select>
                </li>
                    
                    <div>
                    <button type="submit" class="btn btn-outline-success">Filter</button>
                    </div>
                </ul>
            </Form>
        </div>
    );
}

export default SongFilters;