package core;

import java.util.Random;

public class IDGenerator {
    public static String generateID (){
        String possbileChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 22; i++) {
            int randomInt = random.nextInt((61 - 0) + 1) + 0 % 62;
            id.append((char) possbileChars.charAt(randomInt));
        }
        return id.toString();
    }
}
