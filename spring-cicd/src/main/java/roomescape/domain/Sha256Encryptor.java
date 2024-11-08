package roomescape.domain;

import org.springframework.stereotype.Component;
import roomescape.exception.RoomescapeException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static roomescape.exception.ExceptionType.ENCRYPT_FAIL;

@Component
public class Sha256Encryptor {
    public String encrypt(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RoomescapeException(ENCRYPT_FAIL, e);
        }
    }
}
