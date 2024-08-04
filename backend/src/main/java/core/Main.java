package core;

import io.swagger.Swagger2SpringBoot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

import static utility.PackagedFileReader.readPackagedFile;

/**
 * Main class starts api and handles database operations.
 * THIS CLASS IS NO LONGER USED. PLEASE START BACKEND THROUGH SWAGGER2SPRINGBOOT > io.swagger CLASS
 * USE THIS WHEN ONLY INSTANTIATING TABLES
 */
@Slf4j
public class Main {
    public static void main(String[] args){
        DatabaseCommunicator dbc = new DatabaseCommunicator("SQLiteDataSource");
        dbc.createConnection();
        log.info("Database at " + dbc.getDbUrl());
        //dbc.instantiateDatabaseV1("src/main/resources/artists.csv");
        //dbc.instantiateDatabaseV1("dsrc/main/resources/tracks.csv");
        dbc.instantiateDatabaseV3(readPackagedFile("tracks.csv"));
        dbc.instantiateDatabaseV3(readPackagedFile("artists.csv"));
        log.info("Complete");
        // Query for tracks
        //log.info(dbc.queryDB("SELECT * FROM tracks WHERE id = \"26NE3ev5xnI3WfANAjsmhM\";", "JSON"));
        //log.info(dbc.queryDB("SELECT * FROM tracks WHERE title = \"Indigo\";", "JSON"));
        // Query for artists
        //log.info(dbc.queryDB("SELECT * FROM artists WHERE id = \"1Xyo4u8uXC1ZmMpatF05PJ\";", "JSON"));
        //log.info(dbc.queryDB("SELECT * FROM artists WHERE name = \"BTS\";", "JSON"));
        // Delete tracks
        //dbc.updateDB("DELETE FROM tracks WHERE title = \"SHERO\";");
        //dbc.updateDB("DELETE FROM tracks WHERE id = \"7dc9jcxR0GgRMoNasSt424\";");
        // Delete artists
        //dbc.updateDB("DELETE FROM artists WHERE name = \"Justin Bieber\";");
        //dbc.updateDB("DELETE FROM artists WHERE id = \"7dc9jcxR0GgRMoNasStnmZ\";");
        // Update tracks
        //dbc.updateDB("UPDATE tracks SET popularity = 100, explicit = 1 WHERE id = \"27Y1N4Q4U3EfDU5Ubw8ws2\";");
        //dbc.updateDB("UPDATE tracks SET release_date = \"2012-03-12\" WHERE id = \"1pX06KpGgu6KA1mGrJ4XUK\";");
        // Update artists
        //dbc.updateDB("UPDATE artists SET popularity = 97 WHERE id = \"1mcTU81TzQhprhouKaTkpq\";");
        // Insert track
        //dbc.updateDB("INSERT INTO tracks (id,title,popularity,duration_ms,explicit,artists,id_artists,release_date) " +
        //        "VALUES (\"7dc9jcxR0GgRMoNasSt424\",\"One Kiss\",85,20000,0,\"['Dua Lipa']\",\"['6M2wZ9GZgrQXHCFfjv46we']\",\"2018-04-18\");");
        // Insert artist
        //dbc.updateDB("INSERT INTO artists (id,followers,genres,name,popularity)\n" +
        //        "VALUES (\"7dc9jcxR0GgRMoNasStnmZ\", 4335, \"['pop']\", \"David Baroni\", 5);");
        // Next is to obtain the songs from an artist id. We just need the artist id and we can look it up against our
        // other artist ids. Taking into account that id_artists are in a list, so we will have to search the list too.
        //log.info(dbc.queryDB("SELECT * FROM tracks WHERE id_artists LIKE \"%'4LUYOHM73gVlfY5TzRbIgX'%\";", "JSON"));
        // Next one will delete all the songs from a given artist.
        //dbc.updateDB("DELETE FROM tracks WHERE id_artists LIKE \"%'4l7F7EEXy93Jr0uIITY7bN'%\";");
        // Point 5. Retrieve a batch of most popular song for a given year.
        //log.info(dbc.queryDB("SELECT * FROM (SELECT * FROM (SELECT * FROM tracks WHERE release_date = \"2013-06-14\") " +
        //        "ORDER BY popularity DESC) LIMIT 5", "JSON"));
        // Count number songs
        //log.info(dbc.queryDB("SELECT COUNT(*) FROM tracks WHERE id_artists LIKE \"%'47tuSUJMhsa3twW6wgKdIW'%\";", "JSON"));
        // Earliest and Latest song
        //log.info(dbc.queryDB("SELECT MIN(release_date), MAX(release_date) FROM tracks WHERE id_artists LIKE \"%'47tuSUJMhsa3twW6wgKdIW'%\";", "JSON"));
        // Popularity amongst all songs.
        //log.info(dbc.queryDB("SELECT AVG(popularity) FROM tracks WHERE id_artists LIKE \"%'47tuSUJMhsa3twW6wgKdIW'%\";", "JSON"));
        dbc.closeConnection();
    }
}
