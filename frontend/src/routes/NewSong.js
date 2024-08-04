import React, {useState} from 'react'
import Hero from "../components/Hero";
import Form from 'react-bootstrap/Form';
import { maxWidth } from '@mui/system';

function NewSong() {
    /*{"title": "Eye in the sky", 
    "popularity": 30,
    "duration_ms": 63500,
    "explicit": false,
    "artists": "string",
    "id_artists": "string",
    "release_date": "string" */
   const [id, setID] = useState("");
    const [title, setTitle] = useState("");
    const [popularity, setPopularity] = useState("");
    const [duration, setDuration] = useState("");
    const [explicit, setExplicit] = useState(false);
    const [artists, setArtists] = useState([]);
    const [id_artists, setArtistId] = useState([]);
    const [release_date, setReleaseDate] = useState("");
    const [song, setSong] = useState("");
    const [createOrUpdate, setCreate] = useState("");

    let handleSubmit = async (e) => {
        e.preventDefault();
        if(createOrUpdate === 'create') {
            try {
                let res = await fetch("http://localhost:8081/songs", {
                    headers: {
                    'accept': 'application/json',
                    'Content-Type': 'application/json'
                    },
                    method: "POST",
                    body: JSON.stringify({
                        id: id,
                        title: title,
                        popularity : popularity,
                        duration_ms: duration,
                        explicit: explicit,
                        artists: artists,
                        id_artists: id_artists,
                        release_date: release_date,
                    }),
                });
                if (res.status === 202) {
                    setID("");
                    setPopularity("");
                    setDuration("");
                    setExplicit("");
                    setArtists("");
                    setArtistId("");
                    setReleaseDate("");
                    alert("Song was successfully added.");
                } else {
                    alert("There was an error");
                }
            } catch (err) {
                console.log(err);
            }
        } else if (createOrUpdate === 'update') {
            console.log("Update");
            try{
                let res = await fetch(`http://localhost:8081/songs/`+id, {
                    headers: {
                    'accept': 'application/json',
                    'Content-Type': 'application/json'
                    },
                    method: "POST",
                    body: JSON.stringify({
                        id: id,
                        title: title,
                        popularity : popularity,
                        duration_ms: duration,
                        explicit: explicit,
                        artists: artists,
                        id_artists: id_artists,
                        release_date: release_date,
                    }),
                });
                if(res.status === 404) {
                    alert("Song with a given ID does not exist ");
                } else if (res.status === 202) {
                    setID("");
                    setPopularity("");
                    setDuration("");
                    setExplicit("");
                    setArtists("");
                    setArtistId("");
                    setReleaseDate("");
                    alert("Song was successfully updated.");
                } else {
                    alert("There was an error");
                }
            } catch (err) {
                console.log(err);
            }
        }
        
        fetch(`http://localhost:8081/songs?title=`+title, {
            headers: {
              'accept': 'application/json',
              'Content-Type': 'application/json'
            },
            method: "GET"})
            .then(response => response.json()) 
            .then(json => {
                setSong(json.id);
            })
            .catch(error => console.log('error', error))
    }

    /*
    setID("");
                setArtists("");
                setArtistId("");
                setReleaseDate(""); */

    return (
        <>
            <Hero
                cName="hero"
                heroImg="https://static.vecteezy.com/system/resources/previews/008/721/685/original/continuous-one-line-drawing-music-notes-on-stave-musical-symbol-in-one-linear-minimalist-style-trendy-abstract-wave-melody-outline-sketch-sound-single-line-draw-design-graphic-illustration-vector.jpg"
                title="Create/Update a song"
                text="Enter song information"
                button2view="hide"
                button1View="hide"
            />
            <Form className='filters' onSubmit={handleSubmit}>
                <ul>
                    <li>
                        <select class="form-control" type="text" onChange={(e) => setCreate(e.target.value)}>
                            <option value="">Create Or Update?</option>
                            <option value='create'>Create</option>
                            <option value='update'>Update</option>
                        </select>
                    </li>
                    <li class="form-group">
                    <input type="text" class="form-control" placeholder="Song ID" defaultValue={id} onChange={(e) => {
                        setID(e.target.value)}
                    }/>
                    </li>
                    <li class="form-group">
                    <input type="text" class="form-control" placeholder="Song Title" defaultValue={title} onChange={(e) => {
                        setTitle(e.target.value)}
                    }/>
                    </li>
                    <li class="form-group">
                    <input type="number" class="form-control" placeholder="Popularity" defaultValue={popularity} onChange={(e) => {
                        setPopularity(e.target.value)}
                    }/>
                    </li>
                    <li class="form-group">
                    <input type="number" class="form-control" placeholder="Duration in ms" defaultValue={duration} onChange={(e) => {
                        setDuration(e.target.value)}
                    }/>
                    </li>
                    <li>
                        <select class="form-control" type="text" onChange={(e) => setExplicit(e.target.value)}>
                            <option value="">Explicit?</option>
                            <option value='true'>Yes</option>
                            <option value='false'>No</option>
                        </select>
                    </li>
                    <li class="form-group">
                    <input type="text" class="form-control" placeholder="Artists" defaultValue={artists} onChange={(e) => {
                        setArtists(e.target.value)}
                    }/>
                    </li>
                    <li class="form-group">
                    <input type="text" class="form-control" placeholder="Artist IDs" defaultValue={id_artists} onChange={(e) => {
                        setArtistId(e.target.value)}
                    }/>
                    </li>
                    <li class="form-group">
                    <input type="date" class="form-control" placeholder="Release date" defaultValue={release_date} onChange={(e) => {
                        setReleaseDate(e.target.value)}
                    }/>
                    </li>
                    <li class="form-group">
                    <div>
                    <button type="submit" class="btn btn-outline-success">Add Song</button>
                    </div>
                    </li>
                </ul>
            </Form>
            
        </>
    );
}

export default NewSong;