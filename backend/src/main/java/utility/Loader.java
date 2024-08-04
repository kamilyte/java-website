package utility;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

/**
 * Functionality commonly used by file related classes.
 */
@Slf4j
public class Loader {
    /**
     * returns directory.
     * @return directory.
     */
    public static String getWorkingCanonicalDirectory(){
        String currentPath = null;
        try {
            currentPath = new java.io.File(".").getCanonicalPath();
        } catch (IOException e) {
            log.error("Error Determining Working Directory: " + e);
        }
        return currentPath;
    }
}
