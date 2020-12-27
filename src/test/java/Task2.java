import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class Task2 {

    @Test
    public void testTask2() {
        assertEquals(418, countValidPasswords());
    }

    private static int countValidPasswords() {
        try {
            return (int) Files.lines(Paths.get("input2.txt")).filter(Task2::isValidPassword).count();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static boolean isValidPassword(String line) {
        String[] parts = line.split("[-: ]");
        PasswordRule rule = new PasswordRule(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2].charAt(0));
        return rule.matches(parts[4]);
    }


    private static class PasswordRule {
        int minOcc;
        int maxOcc;
        char ruleLetter;

        public PasswordRule(int minOcc, int maxOcc, char ruleLetter) {
            this.minOcc = minOcc;
            this.maxOcc = maxOcc;
            this.ruleLetter = ruleLetter;
        }

        public boolean matches(String password) {
            int occurrences = Math.toIntExact(password.chars().filter(ch -> ch == ruleLetter).count());
            return minOcc <= occurrences && maxOcc >= occurrences;
        }
    }
}
