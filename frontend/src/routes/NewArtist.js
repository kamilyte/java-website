import React, {useState} from 'react'
import Hero from "../components/Hero";
import Form from 'react-bootstrap/Form';
import Select from 'react-select'
import '../components/filters.css' ;




function NewArtist() {
    const [artist_id, setArtistId] = useState("");
    const [name, setName] = useState("");
    const [genres, setGenres] = useState("");
    const [followers, setFollowers] = useState("");


    const genresOptions = [
        { value: 'pop', label: 'Pop' },
        { value: 'classic', label: 'Classic' },
        { value: 'rock', label: 'Rock' },
        { value: 'indie', label: 'Indie' },
        { value: 'hip hop', label: 'Hip Hop' },
      ]

    const handleSelect = function(selectedItems) {
        const gen = [];
        for (let i=0; i<selectedItems.length; i++) {
            gen.push(selectedItems[i].value);
        }
        setGenres(JSON.stringify(gen));
    }
      
    let handleSubmit = async (e) => {
        e.preventDefault();
        try {
            let res = await fetch("http://localhost:8081/artists", {
                headers: {
                  'accept': 'application/json',
                  'Content-Type': 'application/json'
                },
                method: "POST",
                body: JSON.stringify({
                    id: artist_id,
                    followers : followers,
                    genres : genres,
                    name: name,
                    popularity: 0,
                }),
            });
            console.log(res);
            let resJson = await res.json();
            if (res.status === 200) {
                setArtistId("");
                setFollowers("");
                setGenres("");
                setName("");
                alert("Delivery offer has been ");
                //window.location.replace('/delivery');
                console.log(resJson);
            } else if(res.status===422) {
                console.log(resJson);
                alert("Order is already being delivered");
            } else {
                alert("Random error");
            }
        } catch (err) {
            console.log(err);
        }
    }

    return (
        <>
            <Hero
                cName="hero"
                heroImg="https://static.vecteezy.com/system/resources/previews/008/721/685/original/continuous-one-line-drawing-music-notes-on-stave-musical-symbol-in-one-linear-minimalist-style-trendy-abstract-wave-melody-outline-sketch-sound-single-line-draw-design-graphic-illustration-vector.jpg"
                title="Create a new artist"
                text="Enter artist information."
                button2view="hide"
                button1View="hide"
            />
            
            <Form className="filters" onSubmit={handleSubmit}>
                <li class="form-group">
                    <input
                        type="text"
                        defaultValue={artist_id}
                        placeholder="Artist ID"
                        onChange={(e) => setArtistId(e.target.value)}
                        class="form-control"
                    />
                </li>
                <li class="form-group">
                        <input
                        class="form-control"
                            type="text"
                            defaultValue={name}
                            placeholder="Artist Name"
                            onChange={(e) => setName(e.target.value)}
                            
                        />
                </li>
                <li class="form-group">
                    <input
                        type="number"
                        defaultValue={followers}
                        placeholder="Number Of Followers"
                        onChange={(e) => setFollowers(e.target.value)}
                        className="form-control"
                    />
                </li>
                <li class="form-group">
                <Select
                        defaultValue={[genresOptions[0]]}
                        isMulti
                        onSubmit={(e)=> {handleSelect(e.target.selectedOptions)}}
                        name="genres"
                        options={genresOptions}
                        className="basic-multi-select"
                        
                        placeholder="Select music genres"
                    />
                </li>
            
                <button type="submit" class="btn btn-outline-success">Add Artist</button>
            </Form>
        </>
    );
}

export default NewArtist;