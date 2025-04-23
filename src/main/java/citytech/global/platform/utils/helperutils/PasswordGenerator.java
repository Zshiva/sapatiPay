package citytech.global.platform.utils.helperutils;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.inject.Singleton;

@Singleton
public class PasswordGenerator {

    public static String generateRandomPassword(int length) {
        Dotenv env = Dotenv.load();
        String randomString = env.get("RANDOM_STRING");
        StringBuilder generatePassword = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * randomString.length());
            generatePassword.append(randomString.charAt(index));
        }
        return generatePassword.toString();
    }
}