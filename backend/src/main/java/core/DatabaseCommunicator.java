package core;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utility.DatabaseLoader;
import utility.FileReader;
import utility.Loader;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * DatabaseCommunicator class facilitates communication between the database and the backend.
 */
@Slf4j
public class DatabaseCommunicator {
    private String DB_URL = "jdbc:sqlite:";
    private Connection connection = null;
    private Statement statement= null;

    /**
     * Constructor.
     * @param databaseRootPath location of database.
     */
    public DatabaseCommunicator(String databaseRootPath){
        String currentPath;

        currentPath = Loader.getWorkingCanonicalDirectory();

        //this.DB_URL = DB_URL + currentPath + "/" + databaseRootPath;
        this.DB_URL = DB_URL + "SQLiteDataSource";
    }

    /**
     * Connects to database.
     */
    public void createConnection(){
        try {
            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement();
            log.info("Connection to SQLite has been established.");
        } catch (SQLException e) {
            log.error("Failure during instantiation: " + e);
        }
    }

    /**
     * Will initialize the database using the data files at a given path.
     * @param path datasource path.
     */
    public void instantiateDatabaseV1(String path){
        FileReader fr = new FileReader(path);
        DatabaseLoader databaseArtistLoader = new DatabaseLoader(this, fr.readFileReturnScannerJAR());
        databaseArtistLoader.readData();
        log.info("Instantiation Successful.");
    }

    public void instantiateDatabaseV2(String path) {
        updateDB("CREATE TABLE IF NOT EXISTS tracks (id STRING PRIMARY KEY UNIQUE,title STRING NOT NULL,popularity INTEGER NOT NULL,duration_ms INTEGER NOT NULL,explicit INTEGER NOT NULL,artists STRING NOT NULL,id_artists STRING NOT NULL,release_date STRING NOT NULL);");
        updateDB("INSERT INTO tracks (id,title,popularity,duration_ms,explicit,artists,id_artists,release_date) VALUES (\"730liKpFFmDr99NajbtZQf\",\"Jesus Loves Me This I Know\",43,282160,0,\"['David Baroni']\",\"['7dc9jcxR0GgRMoNasStnmZ']\",\"2010-08-26\");");
        updateDB("INSERT INTO tracks (id,title,popularity,duration_ms,explicit,artists,id_artists,release_date) VALUES (\"26NE3ev5xnI3WfANAjsmhM\",\"Al-Masad, Chapter 111\",35,31493,0,\"['New Year's Party 2016']\",\"['5jTdjuYoUW8xAHPKryK5hl']\",\"2010-06-15\");");
        updateDB("CREATE TABLE IF NOT EXISTS artists (id STRING PRIMARY KEY UNIQUE,followers INTEGER NOT NULL,genres STRING NOT NULL,name STRING NOT NULL,popularity INTEGER NOT NULL);");
        updateDB("INSERT INTO artists (id,followers,genres,name,popularity) VALUES (\"1uNFoZAHBGtllmzznpCI3s\",44606973.0,\"['canadian pop', 'pop', 'post-teen pop']\",\"Justin Bieber\",100);");
        updateDB("INSERT INTO artists (id,followers,genres,name,popularity) VALUES (\"5jTdjuYoUW8xAHPKryK5hl\",155.0,\"[]\",\"New Year's Party 2016\",1);");
    }

    public void instantiateDatabaseV3(Scanner scanner){
        //FileReader fr = new FileReader(path);
        DatabaseLoader databaseArtistLoader = new DatabaseLoader(this, scanner);
        databaseArtistLoader.readData();
        log.info("Instantiation Successful.");
    }
    /**
     * Send update to the database.
     * @param update the SQLite update to be executed.
     */
    public void updateDB(String update){
        try {
            statement.executeUpdate(update);
        } catch (SQLException e) {
            log.error("Update UnSuccessful: " + e);
        }
    }

    /**
     * Sends query to database and returns result.
     * Only returns string at the moment. Should return CSV and JSON in the future.
     * @param query query to be executed.
     * @return the result of the query.
     */
    //temp returns string. Later may return csv or json
    public String queryDB(String query, String returnFormat){
        String response;
        try {
            ResultSet result = statement.executeQuery(query);
            if (Objects.equals(returnFormat, "JSON")){
                return returnJSONResponse(result);
            } else {
                log.info("RETURNING CSV NOT IMPLEMENTED");
            }
        } catch (SQLException e) {
            log.error("Query UnSuccessful: " + e);
        }
        return "Failed to Get Response";
    }

    /**
     * Takes result set and returns JSON format string.
     * @param resultSet query result
     */
    public String returnJSONResponse(ResultSet resultSet)  {
        ResultSetMetaData data;
        List<String> colNames = null;
        try {
            data = resultSet.getMetaData();
            colNames = IntStream.range(0, data.getColumnCount())
                    .mapToObj(i -> {
                        String d = "System Failure";
                        try {
                            d = data.getColumnName(i + 1);
                        } catch (SQLException e) {
                            log.error("Failed to Get Column Name: " + e);
                        } finally {
                            return d;
                        }
                    })
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            log.error("Failed to Map Names to Results: " + e);
        }

        JSONArray result = new JSONArray();
        StringBuilder response = new StringBuilder();
        int j = 0;
        while (true) {
            try {
                if (!resultSet.next()){
                    break;
                }
            } catch (SQLException e) {
                log.error("Failed to Convert to JSON String: " + e);
                throw new RuntimeException(e);
            }
            JSONObject row = new JSONObject();
            for (String columnName : colNames){
                try {
                    row.put(columnName, resultSet.getObject(columnName));
                } catch (JSONException | SQLException e) {
                    log.info("Error Converting Response To JSON: " + e);
                    break;
                }
            }
            result.put(row);
            response.append(result.get(j).toString());
            j++;
        }
        return response.toString();
    }

    /**
     * Will attempt to terminate database connection.
     */
    public void closeConnection(){
        try {
            connection.close();
            log.info("Connection Closed.");
        } catch (SQLException e) {
            log.error("Failure Closing Database Connection: " + e);
        }
    }

    public String getDbUrl() {
        return this.DB_URL;
    }
}
