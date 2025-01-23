package chord;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
    public static int hash(String input, int maxNodes) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hashBytes = md.digest(input.getBytes());
            int hash = 0;
            for (int i = 0; i < 4; i++) {
                hash = (hash << 8) | (hashBytes[i] & 0xFF);
            }
            return Math.abs(hash % maxNodes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 not available");
        }
    }
}