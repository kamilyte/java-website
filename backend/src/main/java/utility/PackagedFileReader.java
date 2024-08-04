package utility;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

import java.util.Scanner;

@Slf4j
public class PackagedFileReader {
    public static Scanner readPackagedFile(String path) {
        PackagedFileReader instance = new PackagedFileReader();
        try {
            return instance.pPath(path);
        } catch (IOException e) {
            log.error("Cannot Locate Resource or Convert to Scanner Class");
        }
        return null;
    }
    private Scanner pPath(String path) throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        Scanner scanner = new Scanner(is).useDelimiter("\\A");
        return scanner;

    }
}
