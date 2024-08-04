import React from 'react'
import {GoogleMap, useLoadScript, Marker} from '@react-google-maps/api';
import Navbar from '../components/Navbar';
import Hero from "../components/Hero";

function Map() {
    const {isLoaded}= useLoadScript({
        googleMapsApiKey: "AIzaSyDfiLZA7lRR8_OvaJgTp7ZstZFXB3W0grw",
    });

    if(!isLoaded) return <div>Loading...</div>

    return <div>
        <Hero
                cName="hero"
                heroImg="https://static.vecteezy.com/system/resources/previews/008/721/685/original/continuous-one-line-drawing-music-notes-on-stave-musical-symbol-in-one-linear-minimalist-style-trendy-abstract-wave-melody-outline-sketch-sound-single-line-draw-design-graphic-illustration-vector.jpg"
                title="Google Maps"
                text="Scroll a bit down and you can see where recent concerts were hosted in Groningen."
                button2view="hide"
                button1View="hide"
            />
       
        <GoogleMap zoom={14} center={{lat: 53.21917, lng: 6.56667}} mapContainerClassName="map-container">
            <Marker position={{lat: 53.21917, lng: 6.56667}} />
            <Marker position={{lat: 53.23, lng: 6.56667}} />
            <Marker position={{lat: 53.21, lng: 6.57}} />
            <Marker position={{lat: 53.17, lng: 6.55}} />
        </GoogleMap>;
    </div>

}

export default Map;