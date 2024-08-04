import React, {useState} from 'react'
import Hero from "../components/Hero";
import Form from 'react-bootstrap/Form';
import { maxWidth } from '@mui/system';

function RemoveSong() {
   const [id, setID] = useState("");
   const [title, setTitle] = useState("");
   const [titleDisabled, setTitleDisabled] = useState(false);

    let handleSubmit = async (e) => {
        e.preventDefault();
        try {
            let res = await fetch(`http://localhost:8081/songs/`+id, {
                headers: {
                  'accept': 'application/json',
                  'Content-Type': 'application/json'
                },
                method: 'DELETE',
            });

            if(res.status === 202) {
                alert("Song was successfully removed.");
            } else if (res.status === 404) {
                setID("");
                setTitle("")
                alert("Song with given ID does not exist.");
                console.log(res);
            } else {
                alert("Error ocurred.");
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
                title="Delete a Song"
                text="Choose ID of a song you want to be deleted."
                button2view="hide"
                button1View="hide"
            />
            <Form className='filters' onSubmit={handleSubmit}>
                <ul>
                    <li class="form-group">
                    <input type="text" class="form-control" placeholder="Song ID" defaultValue={id} onChange={(e) => {
                        setTitleDisabled(!titleDisabled);
                        setID(e.target.value)}
                    }/>
                    </li>
                    <li class="form-group">
                    <div>
                    <button type="submit" class="btn btn-outline-success">Remove</button>
                    </div>
                    </li>
                </ul>
            </Form>
            
        </>
    );
}

export default RemoveSong;