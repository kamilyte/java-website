package utility;

import core.DatabaseCommunicator;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * CLASS IS NO LONGER NECESSARY
 * HOWEVER MAY STILL BE USEFUL IN FUTURE
 */
public class CSVLoader {
    private Scanner data;
    private String dataFile;
    private FileReader fileReader;
    public CSVLoader (String dataFile, DatabaseCommunicator dbCom) {
        this.dataFile = dataFile;
        this.fileReader = new FileReader(dataFile);
        data = this.fileReader.readFileReturnScanner();
    }

    public void isolateData (){
        String tableName = null;
        ArrayList<String> cols = new ArrayList<>();
        int counter = 0;
        while(data.hasNext()){
            //create table
            if (counter == 0){
                tableName = data.nextLine();
                String columns = data.nextLine();
            } else {

            }
            counter++;
        }
        this.data.close();
    }
}
