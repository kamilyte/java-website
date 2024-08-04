package io.swagger.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import core.DatabaseCommunicator;
import core.DbConfig;
import io.swagger.model.Song;
import io.swagger.model.SongFilterKeys;
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
import java.util.List;

import static core.IDGenerator.generateID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-01-08T23:33:38.407Z[GMT]")
@RestController
public class SongsApiController implements SongsApi {

    private static final Logger log = LoggerFactory.getLogger(SongsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    private JdbcTemplate db;
    private DatabaseCommunicator dbc;


    @org.springframework.beans.factory.annotation.Autowired
    public SongsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        db = new JdbcTemplate(new DbConfig().dataSource());
    }

    public ResponseEntity<String> songsGet(@NotNull  @Max(100) @Parameter(in = ParameterIn.QUERY, description = "Limit the number of returned items" ,required=true,schema=@Schema(allowableValues={  }, maximum="100"
            , defaultValue="50")) @Valid @RequestParam(value = "limit", required = true, defaultValue="50") Integer limit,@Parameter(in = ParameterIn.QUERY, description = "" ,schema=@Schema()) @Valid @RequestParam(value = "order-by", required = false) SongFilterKeys orderBy,@Parameter(in = ParameterIn.QUERY, description = "The directorion to order the indicated column" ,schema=@Schema(allowableValues={ "asc", "desc" }
            , defaultValue="desc")) @Valid @RequestParam(value = "order-dir", required = false, defaultValue="desc") String orderDir,@Parameter(in = ParameterIn.QUERY, description = "Filtering songs by title" ,schema=@Schema()) @Valid @RequestParam(value = "title", required = false) String title,@Parameter(in = ParameterIn.QUERY, description = "Filtering songs by year" ,schema=@Schema()) @Valid @RequestParam(value = "year", required = false) String year,
                                           @Parameter(in = ParameterIn.QUERY, description = "Filtering songs by artists name" ,schema=@Schema()) @Valid @RequestParam(value = "artistsName", required = false) String artistsName, @Parameter(in = ParameterIn.QUERY, description = "Filtering songs by artists id" ,schema=@Schema()) @Valid @RequestParam(value = "artistsID", required = false) String artistsID) {
        String accept = request.getHeader("Accept");

        //If Content-Type == "text/csv"
        if(accept != null && request.getContentType().contains("text/csv")) {
            ObjectWriter writer = new CsvConverter<List<Song>>(this.objectMapper).getCsvWriter(findAll(limit, orderBy, orderDir, title, year, artistsID, artistsName));
            try {
                log.info(writer.writeValueAsString(findAll(limit, orderBy, orderDir, title, year, artistsID, artistsName)));
                return new ResponseEntity<String>(writer.writeValueAsString(findAll(limit, orderBy, orderDir, title, year, artistsID, artistsName)), HttpStatus.ACCEPTED);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        //If not csv then return response as json.
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json;
            json = ow.writeValueAsString(this.findAll(limit, orderBy, orderDir, title, year, artistsID, artistsName));
            log.info("After checking" + this.findAll(limit, orderBy, orderDir, title, year, artistsID, artistsName).toString());
            return new ResponseEntity<String>(json, HttpStatus.ACCEPTED);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Void> songsIdDelete(@Parameter(in = ParameterIn.PATH, description = "The ID of the song", required=true, schema=@Schema()) @PathVariable("id") String id) {
        String accept = request.getHeader("Accept");
        log.info(id);
        int status = this.deleteSongByID(id);
        if(status == -1) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> songsIdGet(@Parameter(in = ParameterIn.PATH, description = "The ID of the song", required=true, schema=@Schema()) @PathVariable("id") String id) {
        String accept = request.getHeader("Accept");
        log.info(this.getSongByID(id).toString());
        if(this.getSongByID(id).isEmpty()) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        if(accept != null && request.getContentType().contains("text/csv")) {
            ObjectWriter writer = new CsvConverter<List<Song>>(this.objectMapper).getCsvWriter(getSongByID(id));
            try {
                log.info(writer.writeValueAsString(getSongByID(id)));
                return new ResponseEntity<String>(writer.writeValueAsString(getSongByID(id)), HttpStatus.ACCEPTED);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        //If not csv then return response as json.
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json;
            json = ow.writeValueAsString(getSongByID(id));
            log.info("After checking" + getSongByID(id).toString());
            return new ResponseEntity<String>(json, HttpStatus.ACCEPTED);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Song> songsIdPost(@Parameter(in = ParameterIn.PATH, description = "The ID of the song", required=true, schema=@Schema()) @PathVariable("id") String id,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Song body) {
        String accept = request.getHeader("Accept");
        if(this.getSongByID(id).isEmpty()) {
            return new ResponseEntity<Song>(HttpStatus.NOT_FOUND);
        }
        updateSong(id,body);
        return new ResponseEntity<Song>(HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Integer> songsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody Song body) {
        String accept = request.getHeader("Accept");
        this.createSong(body);
        return new ResponseEntity<Integer>(HttpStatus.ACCEPTED);

    }

    public List<Song> findAll(Integer limit, SongFilterKeys orderBy, String direction, String title, String year, String artistsID, String artistsName) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM tracks");
        if (year != null){
            query.append(" WHERE release_date ").append("LIKE \"%").append(year).append("%\"");
        } else if (title != null){
            query.append(" WHERE title = \"").append(title).append("\"");
        } else if (artistsID != null) {
            query.append(" WHERE id_artists LIKE \"%").append(artistsID).append("%\"");
        } else if (artistsName != null) {
            query.append(" WHERE artists LIKE \"%").append(artistsName).append("%\"");
        }
        if (orderBy != null){
            query.append(" ORDER BY ").append(orderBy.toString()).append(" ").append(direction.toUpperCase());
        }
        switch (limit) {
            case 10:
                limit = 10;
                break;
            case 20:
                limit = 20;
                break;
            case 50:
                limit = 50;
                break;
            case 100:
                limit = 100;
                break;
            default:
                limit = 10;
        }
        query.append(" LIMIT ").append(limit).append(";");
        log.info(query.toString());
        log.debug(db.query(query.toString(), new BeanPropertyRowMapper(Song.class)).toString());
        List<Song> songs = db.query(query.toString(), new BeanPropertyRowMapper(Song.class));
        return songs;
    }

    public List<Song> getSongByID(String id){
        String sql = "SELECT * FROM tracks WHERE id = \'" +  id + '\'';
        log.debug(db.query(sql, new BeanPropertyRowMapper(Song.class)).toString());
        List<Song> resp = db.query(sql, new BeanPropertyRowMapper(Song.class));
        return resp;
    }

    public int deleteSongByID(String id){
        List<Song> songs = getSongByID(id);
        if(songs.isEmpty()) {
            return -1;
        }
        StringBuilder sql = new StringBuilder();
        log.info(id);
        sql.append("DELETE FROM tracks WHERE id = \"").append(id).append("\";");
        db.update(sql.toString());
        return 0;
    }

    public void updateSong (String id, Song song) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tracks SET title = \"").append(song.getTitle()).append("\",");
        sql.append("popularity = ").append(song.getPopularity()).append(",");
        sql.append("duration_ms = ").append(song.getDurationMs()).append(",");
        sql.append("explicit = ").append(song.isExplicit()).append(",");
        sql.append("artists = \"").append(song.getArtists()).append("\",");
        sql.append("id_artists = \"").append(song.getIdArtists()).append("\",");
        sql.append("release_date = \"").append(song.getReleaseDate()).append("\" ");
        sql.append("WHERE id LIKE \"%").append(id).append("%\";");
        log.info(sql.toString());
        db.update(sql.toString());
    }

    public void createSong (Song song) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO tracks (id, title, popularity, duration_ms, explicit, artists, id_artists, release_date) VALUES (\"");
        sql.append(createNewID()).append("\", \"").append(song.getTitle()).append("\", ").append(song.getPopularity()).append(", ");
        sql.append(song.getDurationMs()).append(", ").append(song.isExplicit()).append(", \"").append(song.getArtists());
        sql.append("\", \"").append(song.getIdArtists()).append("\", \"").append(song.getReleaseDate()).append("\");");
        db.update(sql.toString());
    }

    public String createNewID(){
        String id = generateID();
        String string = db.query("SELECT id FROM tracks", new BeanPropertyRowMapper(Song.class)).toString();
        log.info(id);
        log.info(string);
        while (string.contains(id)) {
            log.info("Attempting To Generate Unique ID");
            id = generateID();
        }
        return id;
    }
}
