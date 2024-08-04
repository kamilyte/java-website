import logo from './logo.svg';
import './App.css';
import {Routes, Route} from "react-router-dom"
import Home from "./routes/Home";
import Songs from "./routes/Songs";
import Artists from "./routes/Artists";
import NewSong from "./routes/NewSong";
import NewArtist from "./routes/NewArtist";
import Map from "./routes/Map";
import SongFilters from "./routes/SongsFilters";
import DeleteSong from "./routes/RemoveSong";
import 'bootstrap/dist/css/bootstrap.min.css';


import Navbar from "./components/Navbar";

function App() {
  return (
      <div className="App">
          <Navbar/>
        <Routes>
          <Route path="/" element={<Home/>}/>
          <Route path="/songs" element={<Songs/>}/>
          <Route path="/artists" element={<Artists/>}/>
          <Route path="/newSong" element={<NewSong/>}/>
          <Route path="/newArtist" element={<NewArtist/>}/>
          <Route path="/map" element={<Map/>} />
          <Route path="/songFilters" element={<SongFilters/>} />
          <Route path="/deleteSong" element={<DeleteSong/>} />
        </Routes>
      </div>
  );
}

export default App;
