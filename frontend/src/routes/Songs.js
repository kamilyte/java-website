import {useEffect, useState} from "react";
import Hero from "../components/Hero";
import {useLocation} from 'react-router-dom';
import '../components/HeroStyles.css'
import '../components/filters.css'
import {Link, useNavigate} from 'react-router-dom';
import 'react-bootstrap-form';
import Form from 'react-bootstrap/Form';



function Songs(props) {
    let [son, getSongs] = useState([]);
    let [heroText, setText] = useState('');
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
    
    const location = useLocation();
    console.log(location.state.orderBy);
    console.log(location.state.orderDir);
    console.log(location.state.limit);
    console.log(location.state.title);
    console.log(location.state.year);

    let handleSubmit = (e) => {
        e.preventDefault();
        location.state = {
            limit: lim, 
            orderBy: orderBy,
            orderDir: orderDir,
            title: title,
            year: year,
            songID: songID,
        };
        setText("No filters chosen.");
        setOrderDir("");
        setOrderBy("");
        setTitle("");
        setYear("");
        setLimit("");
        setSongID("");
        setIsDisabled(false);
        setIsDisabledYear(false);
        setIsDisabledFields(false);

        getAllSongs();
    }
    //Loads data when page is loaded
    useEffect(() => {
        getAllSongs();
    },[]);

    //sends a @GET request to API
    const getAllSongs = () => {
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "\"application/json\"");

        
        const requestOptions = {
            method: 'GET',
            headers: myHeaders,

            redirect: 'follow'
        };

        let url = "http://localhost:8081/songs";

        if(location.state.songID !== "") {
            heroText = `You selected song with ID: ` + location.state.songID;
            url = url + `/` + location.state.songID;
            setText(heroText);
            console.log(url);
        } else if(location.state.title !== "") {
            heroText = `You selected song with a title:  "` + location.state.title + `"`;
            url = url + `?title=` + location.state.title;
            setText(heroText);
        } else if (location.state.year !=="") {
            if(location.state.limit==="") {
                location.state.limit = 10;
            }
            heroText = `Top ` + location.state.limit + ` songs in ` + location.state.year;
            url = url +`?order-by=POPULARITY` + `&year=` + location.state.year 
            + `&limit=` + location.state.limit;
            setText(heroText);
        } else {
            if(location.state.limit === "" && location.state.orderBy === "" &&  location.state.orderDir === "") {
                heroText = "No filters selected. Returned default. "
            } else {
                heroText = "You selected " + location.state.limit + " songs filtered by " + location.state.orderBy + " " + location.state.orderDir;
            }
                url = url +`?limit=` +location.state.limit
                +`&order-by=` +location.state.orderBy 
                + `&order-dir=`+location.state.orderDir;
                setText(heroText);
        }

        /* 
        `http://localhost:8081/songs?limit=`+location.state.limit
        +`&order-by=` +location.state.orderBy 
        + `&order-dir=`+location.state.orderDir
        + `&title=`+location.state.titl
        */

        /**
         * .then(response => response.json())
                .then(json => setArtists(json))
                .catch(error => console.log(
         */

        fetch(url, requestOptions)
            .then(response => response.json())
            .then(json => {
                console.log(json);
                getSongs(json);
            })
            .catch(error => console.log('error', error));

    }


    function Song(props) {
        console.log(son.length);
        if(son.length > 0) {
            return(
                son.map((song, index) => {
                if(song.explicit) {
                    song.explicit = "true";
                } else {
                    song.explicit = "false";
                }
                return (
                    <li  className="songs">
                        <h2>Title: {song.title}</h2>
                        <p>ID: {song.id}</p>
                        <p>Popularity: {song.popularity}</p>
                        <p>Explicit: {song.explicit}</p>
                        <p>Song duration (ms): {song.duration_ms}</p>
                        <p>Release date: {song.release_date}</p>
                        <p>Artists: {song.artists}</p>
                    </li>
                )
            })
        )
        } else {
            return <h1>No songs found.</h1>
        }
    }
    return(
         
        <div>
            <Hero 
            cName="hero"
            heroImg="https://static.vecteezy.com/system/resources/previews/008/721/685/original/continuous-one-line-drawing-music-notes-on-stave-musical-symbol-in-one-linear-minimalist-style-trendy-abstract-wave-melody-outline-sketch-sound-single-line-draw-design-graphic-illustration-vector.jpg"
            title="Songs"
            text={heroText}
            />
            <Form className='filters' onSubmit={handleSubmit}>
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
            </Form>
            <Song className="hero-text" songs={{son}}/>
        </div>
    );
}

export default Songs;
