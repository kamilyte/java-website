package io.swagger.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import core.DbConfig;
import io.swagger.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static core.IDGenerator.generateID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-01-08T23:33:38.407Z[GMT]")
@RestController
public class ArtistsApiController implements ArtistsApi {

    private static final Logger log = LoggerFactory.getLogger(ArtistsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private JdbcTemplate db;

    @org.springframework.beans.factory.annotation.Autowired
    public ArtistsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        db = new JdbcTemplate(new DbConfig().dataSource());
    }

    public ResponseEntity<String> artistsGet(@NotNull  @Max(100) @Parameter(in = ParameterIn.QUERY, description = "Limit the number of returned items" ,required=true,schema=@Schema(allowableValues={  }, maximum="100"
            , defaultValue="50")) @Valid @RequestParam(value = "limit", required = true, defaultValue="50") Integer limit, @Parameter(in = ParameterIn.QUERY, description = "The name of the Artist" ,schema=@Schema()) @Valid @RequestParam(value = "artistsName", required = false) String artistsName, @Parameter(in = ParameterIn.QUERY, description = "id of an artist" ,schema=@Schema()) @Valid @RequestParam(value = "id", required = false) String id) {

        String accept = request.getHeader("Accept");

        //If Content-Type == "text/csv"
        if(accept != null && request.getContentType().contains("text/csv")) {
            ObjectWriter writer = new CsvConverter<List<ArtistSummary>>(this.objectMapper).getCsvWriter(findAllArtists(limit, id, artistsName));
            try {
                log.info(writer.writeValueAsString(findAllArtists(limit, id, artistsName)));
                return new ResponseEntity<String>(writer.writeValueAsString(findAllArtists(limit, id, artistsName)), HttpStatus.ACCEPTED);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        //If not csv then return response as json.
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json;
            json = ow.writeValueAsString(findAllArtists(limit, id, artistsName));
            log.info("After checking" + findAllArtists(limit, id, artistsName).toString());
            return new ResponseEntity<String>(json, HttpStatus.ACCEPTED);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<String> artistsGetByPopularity(Integer limit, String year) {
        String accept = request.getHeader("Accept");
        //If Content-Type == "text/csv"
        if(accept != null && request.getContentType().contains("text/csv")) {
            ObjectWriter writer = new CsvConverter<List<ArtistSummary>>(this.objectMapper).getCsvWriter(getArtistsDate(year, limit));
            try {
                log.info(writer.writeValueAsString(getArtistsDate(year, limit)));
                return new ResponseEntity<String>(writer.writeValueAsString(getArtistsDate(year, limit)), HttpStatus.ACCEPTED);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        //If not csv then return response as json.
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json;
            json = ow.writeValueAsString(getArtistsDate(year, limit));
            log.info("After checking" + getArtistsDate(year, limit).toString());
            return new ResponseEntity<String>(json, HttpStatus.ACCEPTED);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Void> artistsDelete(@Parameter(in = ParameterIn.QUERY, description = "The name of the Artist" ,schema=@Schema()) @Valid @RequestParam(value = "artistsName", required = false) String artistsName, @Parameter(in = ParameterIn.QUERY, description = "id of an artist" ,schema=@Schema()) @Valid @RequestParam(value = "id", required = false) String id) {
        if (id != null) {
            log.info("ID not null");
            if (findAllArtists(1, id, null).isEmpty()) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            } else {
                this.deleteSongsByID(id);
            }
        }
        if (artistsName != null) {
            log.info("Name not null");
            if (findAllArtists(1, null, artistsName).isEmpty()) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            } else {
                this.deleteSongsByName(artistsName);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    /*
        public ResponseEntity<Void> artistsIdDelete(@Parameter(in = ParameterIn.PATH, description = "The ID of the artist", required=true, schema=@Schema()) @PathVariable("id") String id) {
            String accept = request.getHeader("Accept");

            if(findAllArtists(1, id, null).isEmpty()) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }

            log.info(id);
            this.deleteArtistByID(id);
            return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
        }
    */
    public ResponseEntity<String> artistsIdGet(@Parameter(in = ParameterIn.PATH, description = "The ID of the artist", required=true, schema=@Schema()) @PathVariable("id") String id) {
        String accept = request.getHeader("Accept");

        if(findAllArtists(1, id, null).isEmpty()) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        //If Content-Type == "text/csv"
        if(accept != null && request.getContentType().contains("text/csv")) {
            ObjectWriter writer = new CsvConverter<List<ArtistSummary>>(this.objectMapper).getCsvWriter(findAllArtists(1, id, null));
            try {
                log.info(writer.writeValueAsString(findAllArtists(1, id, null)));
                return new ResponseEntity<String>(writer.writeValueAsString(findAllArtists(1, id, null)), HttpStatus.ACCEPTED);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        //If not csv then return response as json.
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json;
            json = ow.writeValueAsString(getArtistByID(id));
            log.info("After checking" + getArtistByID(id).toString());
            return new ResponseEntity<String>(json, HttpStatus.ACCEPTED);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Artist> artistsIdPost(@Parameter(in = ParameterIn.PATH, description = "The ID of the artist", required=true, schema=@Schema()) @PathVariable("id") String id,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Artist body) {
        if(findAllArtists(1, id, null).isEmpty()) {
            return new ResponseEntity<Artist>(HttpStatus.NOT_FOUND);
        }
        updateArtist(id, body);
        return new ResponseEntity<Artist>(HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> artistsIdSongsGet(@Parameter(in = ParameterIn.PATH, description = "The ID of the artist", required=true, schema=@Schema()) @PathVariable("id") String id, @Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "order-by", required = false) SongFilterKeys orderBy, @Parameter(in = ParameterIn.QUERY, description = "The directorion to order the indicated column" ,schema=@Schema(allowableValues={ "asc", "desc" }
            , defaultValue="desc")) @Valid @RequestParam(value = "order-dir", required = false, defaultValue="desc") String orderDir) {
        String accept = request.getHeader("Accept");

        if(findAllArtists(1, id, null).isEmpty()) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        if(accept != null && request.getContentType().contains("text/csv")) {
            ObjectWriter writer = new CsvConverter<List<Song>>(this.objectMapper).getCsvWriter(getSongsID(id));
            try {
                log.info(writer.writeValueAsString(getSongsID(id)));
                return new ResponseEntity<String>(writer.writeValueAsString(getSongsID(id)), HttpStatus.ACCEPTED);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        //If not csv then return response as json.
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json;
            json = ow.writeValueAsString(getSongsID(id));
            log.info("After checking" + getSongsID(id).toString());
            return new ResponseEntity<String>(json, HttpStatus.ACCEPTED);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Integer> artistsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Artist body) {
        String accept = request.getHeader("Accept");
        this.postArtist(body);
        return new ResponseEntity<Integer>(HttpStatus.ACCEPTED);
    }

    public List<ArtistSummary> findAllArtists(@NotNull @Valid Integer limit, @Valid String artists_id, @Valid String artistsName) {
        String sql = "SELECT * FROM artists";

        if(artistsName != null) {
            log.info("name isnt null");
            sql = sql.concat(" WHERE name =\"" + artistsName + "\"");
        }

        if(artists_id != null) {
            sql = sql.concat(" WHERE id = \"" + artists_id + "\"");
        }

        sql = sql.concat(" LIMIT " + limit);

        log.info(sql);
        log.debug(db.query(sql, new BeanPropertyRowMapper(Artist.class)).toString());
        List<Artist> artists = db.query(sql, new BeanPropertyRowMapper(Artist.class));

        List<ArtistSummary> summaries = new ArrayList<>();

        for (int i = 0; i < artists.size(); i++) {
            Artist artist = artists.get(i);
            String id = artist.getId();
            log.info("artist id = " + id);
            String name = artist.getName();
            Song earliest = null, latest = null;
            List<Song> songs = findSongsFromIdByDate(id);

            switch (songs.size()) {
                case 0:
                    break;
                case 1:
                    earliest = songs.get(0);
                    latest = earliest;
                    break;
                default:
                    earliest = songs.get(0);
                    latest = songs.get(songs.size() - 1);
            }

            ArtistSummary artistSummary = new ArtistSummary();

            artistSummary.setId(id);
            artistSummary.setName(name);
            artistSummary.setNumberOfsongs(songs.size());
            artistSummary.setEarliestRelease(earliest);
            artistSummary.setLatestRelease(latest);
            artistSummary.setHighestPopularity(getHighestPopularity(id));
            summaries.add(artistSummary);
        }
        return summaries;
    }

    public List<Song> findSongsFromIdByDate(String id) {
        String sql = "SELECT * FROM tracks WHERE id_artists LIKE \"%'" + id + "'%\"" + "ORDER BY release_date asc";
        List<Song> songs = db.query(sql, new BeanPropertyRowMapper(Song.class));
        return songs;
    }

    public Integer getHighestPopularity(String id) {
        String sql = "SELECT * FROM tracks WHERE id_artists LIKE \"%'" + id + "'%\"" + "ORDER BY popularity desc";

        List<Song> songs = db.query(sql, new BeanPropertyRowMapper(Song.class));

        if(!songs.isEmpty()) {
            return songs.get(0).getPopularity();
        } else {
            return 0;
        }
    }

    public ArtistSummary getArtistByID(String id){
        return this.findAllArtists(1, id, null).get(0);
    }

    public List<ArtistSummary> getArtistsDate(String date, Integer limit){
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM tracks WHERE tracks.release_date LIKE '%" + date +
                "%' GROUP BY tracks.id_artists ORDER BY AVG(tracks.popularity) DESC");

        query = query.append(" LIMIT " + limit + ";");

        log.debug(db.query(query.toString(), new BeanPropertyRowMapper(Song.class)).toString());
        List<Song> songs = db.query(query.toString(), new BeanPropertyRowMapper(Song.class));

        List<String> ids = new ArrayList<>();
        String currentId;
        for(int i = 0; i < songs.size(); i++) {
            currentId = songs.get(i).getIdArtists().substring(2, songs.get(i).getIdArtists().length()-2);
            log.info(currentId);
            ids.add(i, currentId);
        }

        List<ArtistSummary> artists = new ArrayList<>();
        List<ArtistSummary> currentarr = new ArrayList<>();
        for(int i = 0; i < ids.size(); i++) {
            try {
                currentarr = findAllArtists(1, ids.get(i), null);
                if (currentarr.size() > 0) {
                    artists.add(getArtistByID(ids.get(i)));
                }
            } catch (NullPointerException e) {
                log.info("Null");
            }
        }
        return artists;
    }

    public void deleteArtistByID(String id){
        StringBuilder sql = new StringBuilder();
        log.info(id);
        sql.append("DELETE FROM tracks WHERE id_artists = \"").append(id).append("\";");
        db.update(sql.toString());
    }

    public void postArtist (Artist artist) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO artists (id,followers,genres,name,popularity) VALUES ( \"");
        sql.append(createNewID()).append("\", ").append(artist.getFollowers()).append(", \"").append(artist.getGenres()).append("\",\"");
        sql.append(artist.getName()).append("\", ").append(artist.getPopularity()).append(");");
        db.update(sql.toString());
    }

    public List<Song> getSongsID (String id) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM tracks WHERE id_artists LIKE \"%");
        query.append(id).append("%\";");
        log.info(query.toString());
        log.debug(db.query(query.toString(), new BeanPropertyRowMapper(Song.class)).toString());
        List<Song> songs = db.query(query.toString(), new BeanPropertyRowMapper(Song.class));
        return songs;
    }

    public void updateArtist(String id, Artist artist){
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE artists SET followers = ").append(artist.getFollowers()).append(",");
        sql.append("genres = \"").append(artist.getGenres()).append("\",");
        sql.append("name = \"").append(artist.getName()).append("\",");
        sql.append("popularity = ").append(artist.getPopularity());
        sql.append(" WHERE id LIKE \"%").append(id).append("%\";");
        log.info(sql.toString());
        db.update(sql.toString());
    }

    public String createNewID(){
        String id = generateID();
        String string = db.query("SELECT id FROM artists", new BeanPropertyRowMapper(Artist.class)).toString();
        log.info(id);
        log.info(string);
        while (string.contains(id)) {
            log.info("Attempting To Generate Unique ID");
            id = generateID();
        }
        return id;
    }

    public int deleteSongsByID(String id){
        StringBuilder sql = new StringBuilder();
        log.info(id);
        sql.append("DELETE FROM tracks WHERE id_artists LIKE \"%").append(id).append("%\";");
        db.update(sql.toString());
        return 0;
    }

    public int deleteSongsByName(String name){
        StringBuilder sql = new StringBuilder();
        log.info(name);
        sql.append("DELETE FROM tracks WHERE artists LIKE \"%").append(name).append("%\";");
        db.update(sql.toString());
        return 0;
    }
}