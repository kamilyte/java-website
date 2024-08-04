import React, {useEffect, useState} from "react";
import Hero from "../components/Hero";

function Artists() {
    let [resourceType, setResourceType] = useState("All")
    let [artists, setArtists] = useState([])
    let [value, setValue] = useState("");
    let [year, setYear] = useState("2010");
    let [lim, setLimit] = useState("10");

    
    useEffect(() => {
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "\"application/json\"");

        const requestOptions = {
            method: 'GET',
            headers: myHeaders,

            redirect: 'follow'
        };

        if (resourceType === "All") {
            fetch("http://localhost:8081/artists", requestOptions)
                .then(response => response.json())
                .then(json => setArtists(json))
                .catch(error => console.log('error', error));
        }
        if (resourceType === "ByID"){
            fetch("http://localhost:8081/artists?id=" + value, requestOptions)
                .then(response => response.json())
                .then(json1 => setArtists(json1))
                .catch(error => console.log('error', error));
        }
        if (resourceType === "ByName"){
            fetch("http://localhost:8081/artists?artistsName=" + value, requestOptions)
                .then(response => response.json())
                .then(json1 => setArtists(json1))
                .catch(error => console.log('error', error));
        }
        if (resourceType === "SongsByID"){
            fetch("http://localhost:8081/songs?artistsID=" + value, requestOptions)
                .then(response => response.json())
                .then(json1 => setArtists(json1))
                .catch(error => console.log('error', error));
        }
        if (resourceType === "SongsByName"){
            fetch("http://localhost:8081/songs?artistsName=" + value, requestOptions)
                .then(response => response.json())
                .then(json1 => setArtists(json1))
                .catch(error => console.log('error', error));
        }
        if (resourceType === "DelID"){
            fetch("http://localhost:8081/artists?id=" + value, { method: 'DELETE' })
                .then(() => console.log('Success'))
                .catch(error => console.log('error', error));
        }
        if (resourceType === "DelName"){
            fetch("http://localhost:8081/artists?artistsName=" + value, { method: 'DELETE' })
                .then(() => console.log('Success'))
                .catch(error => console.log('error', error));
        }
        if (resourceType === "ByYear"){
            fetch("http://localhost:8081/artists/popularity?limit=" + lim + "&year=" + year, requestOptions)
                .then(response => response.json())
                .then(json1 => setArtists(json1))
                .catch(error => console.log('error', error));
        }
    }, [resourceType, value, year, lim])

    return(
        <>
            <Hero
            cName="hero"
            heroImg="https://static.vecteezy.com/system/resources/previews/008/721/685/original/continuous-one-line-drawing-music-notes-on-stave-musical-symbol-in-one-linear-minimalist-style-trendy-abstract-wave-melody-outline-sketch-sound-single-line-draw-design-graphic-illustration-vector.jpg"
            title="Artists"
            text="Retrieve or delete artist songs by their ID or Name, etc."
            button2view="hide"
            button1View="hide"
            />
            <div className="filters">
                <button onClick={() => setResourceType("All")}>All artists</button>
                <input placeholder="Enter ID or name" value={value} onChange={e => setValue(e.target.value)} />
                <button onClick={() => setResourceType("ByID")}>Artist by ID</button>
                <button onClick={() => setResourceType("SongsByID")}>Songs by ID</button>
                <button onClick={() => setResourceType("ByName")}>Artist by Name</button>
                <button onClick={() => setResourceType("SongsByName")}>Songs by Name</button>
                <button onClick={() => setResourceType("DelID")}>Delete Songs by ID</button>
                <button onClick={() => setResourceType("DelName")}>Delete Songs by Name</button>
                <li className="form-group">
                    <select className="form-control" type="text" onChange={(e) => {
                        setYear(e.target.value)
                    }}>
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
                <li className="form-group">
                    <select className="form-control" type="text" onChange={(e) => {
                        setLimit(e.target.value)
                    }}>
                        <option value="">Select limit</option>
                        <option value="10">10</option>
                        <option value="20">20</option>
                        <option value="50">50</option>
                        <option value="100">100</option>
                    </select>
                </li>
                <button onClick={() => setResourceType("ByYear")}>Most popular artists by year</button>
            </div>
            <h1>{resourceType}</h1>
            {artists.map(artist => {
                let songEarliest = artist.earliestRelease;
                let songLatest = artist.latestRelease;
                let song1, song2;
                if(songEarliest === null) {
                    console.log(null);
                } else {
                    song1 = songEarliest.title;
                }
                if(songLatest === null) {
                    console.log(null);
                } else {
                    song2 = songLatest.title;
                }
                
                /* if(artist.earliestRelease.title != null) {
                    song1 = JSON.stringify(artist.earliestRelease.title);
                } else {
                    song1 = "None";
                }
                if((artist.latestRelease.title != null)) {
                    song2 = JSON.stringify(artist.latestRelease.title);
                } else {
                    song2 = "None";
                } */
                 
                
                return (
                    <li  className="artists">
                        <h2>Name: {artist.name}</h2>
                        <p>ID: {artist.id}</p>
                        <p>Number of songs: {artist.numberOfsongs}</p>
                        <p>Earliest song: {song1}</p>
                        <p>Latest song: {song2}</p>
                        <p>Max popularity: {artist.highestPopularity}</p>
                    </li>
                )
            })}
        </>
    )
}

export default Artists;