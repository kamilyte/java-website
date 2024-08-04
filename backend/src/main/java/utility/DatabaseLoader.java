package utility;

import core.DatabaseCommunicator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

/**
 * DatabaseLoader handles instantiation of the database.
 */
@Slf4j
public class DatabaseLoader extends Loader {
    private final Scanner scanner;
    private final DatabaseCommunicator databaseCommunicator;
    private String tableName;
    private final ArrayList<String> attributes = new ArrayList<>();
    private final ArrayList<String> attributeTypes = new ArrayList<>();
    private final ArrayList<String> attributeTypeQualities = new ArrayList<>();

    /**
     * Constructor
     * @param databaseCommunicator communicator which facilitates communication with database.
     * @param scanner scanner with file data.
     */
    public DatabaseLoader(DatabaseCommunicator databaseCommunicator, Scanner scanner){
        this.databaseCommunicator = databaseCommunicator;
        this.scanner = scanner;
    }

    /**
     * Will process the lines of the text and identify the table parameters and the data.
     */
    public void readData(){
        String line;
        int counter = 0;
        while (this.scanner.hasNextLine()) {
            line = this.scanner.nextLine();
            if (!line.isEmpty()) {
                switch (counter) {
                    case 0:
                        tableName = line;
                        break;
                    case 1:
                        Collections.addAll(attributes, line.split(";"));
                        break;
                    case 2:
                        Collections.addAll(attributeTypes, line.split(";"));
                        break;
                    case 3:
                        Collections.addAll(attributeTypeQualities, line.split(";"));
                        break;
                    case 4:
                        createTable();
                        generateDataUpdate(line);
                        break;
                    default:
                        generateDataUpdate(line);
                }
                counter++;
            }
        }
    }

    /**
     * Will create table in database.
     */
    public void createTable() {
        StringBuilder update = new StringBuilder();
        update.append("CREATE TABLE IF NOT EXISTS ");
        update.append(tableName);
        update.append(" (");
        int i = 0;
        while (i < attributes.size()){
            update.append(attributes.get(i));
            update.append(" ").append(attributeTypes.get(i));
            update.append(" ").append(attributeTypeQualities.get(i));
            if (i != attributes.size() - 1){
                update.append(",");
            }
            i++;
        }
        update.append(");");
        log.info(update.toString());
        databaseCommunicator.updateDB(update.toString());
    }

    /**
     * Will add data to given by input line.
     * @param line the raw text before being processed and split accordinly.
     */
    public void generateDataUpdate(String line){
        StringBuilder dataLine = new StringBuilder();
        dataLine.append("INSERT INTO ");
        dataLine.append(tableName);
        dataLine.append(" (");
        int i = 0;
        while (i < attributes.size()){
            dataLine.append(attributes.get(i).replaceAll("\"",""));
            if (i != attributes.size() - 1){
                dataLine.append(",");
            }
            i++;
        }
        dataLine.append(") VALUES (");
        int j = 0;
        ArrayList<String> tempList = new ArrayList<>();
        Collections.addAll(tempList, line.split(";"));
        while (j < attributeTypes.size())  {
            if (Objects.equals(attributeTypes.get(j), "STRING") || Objects.equals(attributeTypes.get(j), "PRIMARY KEY")){
                dataLine.append("\"");
                dataLine.append(tempList.get(j).replaceAll("\"", ""));
                dataLine.append("\"");
            } else {
                dataLine.append(tempList.get(j));
            }
            if (j != attributeTypes.size() - 1) {
                dataLine.append(",");
            }
            j++;
        }
        dataLine.append(");");

        log.info(dataLine.toString());
        databaseCommunicator.updateDB(dataLine.toString());
    }
}
