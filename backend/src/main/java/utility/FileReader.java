package utility;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Scanner;

/**
 * FileReader class will read text files and has several methods to help process the text.
 */
@Slf4j
public class FileReader extends Loader {
    //private String path;
    private String fileName;

    /**
     * Constructor.
     * @param fileName the name of the file to be read.
     */

    public FileReader (String fileName) {
        this.fileName = fileName;
        //this.path = getWorkingCanonicalDirectory();
    }

    /**
     * Reads file and returns string of the text within.
     * @return a string of the text.
     */
    public String readFileReturnString (){
        StringBuilder fileContents = new StringBuilder();
        try {
            File f = new File(/*this.path + "/" +*/ this.fileName);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                fileContents.append(data);
                fileContents.append('\n');
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            log.error("Error Reading File: " + e);
        }
        return fileContents.toString();
    }

    /**
     * Will return the scanner of the file, not yet converted to text.
     * Useful when text needs to be handled line by line, skips need of processing string data.
     * @return scanner.
     */
    public Scanner readFileReturnScanner (){
        Scanner scanner = null;
        try {
            File f = new File(/*this.path + "/" +*/ this.fileName);
            scanner = new Scanner(f);
        } catch (FileNotFoundException e) {
            log.error("Error Reading File: " + e);
        }
        return scanner;
    }

    public Scanner readFileReturnScannerJAR(){
        String jarPath = FileReader.class.getProtectionDomain().getCodeSource().getLocation().getPath();;
        //assertThat(jarPath).endsWith(".jar").contains("guava");
        //assertThat(new File(jarPath)).exists();
        BufferedReader reader = null;
        InputStream in = getClass().getResourceAsStream("/BOOT-INF/classes!/tracks.csv");
        Scanner s = new Scanner(in).useDelimiter("\\A");
        //String result = s.hasNext() ? s.next() : "";
        return s;
    }

}
