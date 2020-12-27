import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class Task2_2 {

    @Test
    public void testTask2_part2() {
        assertEquals(616, countValidPasswords2());
    }

    private static int countValidPasswords2() {
        try {
            return (int) Files.lines(Paths.get("input2.txt")).filter(Task2_2::isValidPassword2).count();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static boolean isValidPassword2(String line) {
        String[] parts = line.split("[-: ]");
        PasswordRule2 rule = new PasswordRule2(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2].charAt(0));
        return rule.matches(parts[4]);
    }

    private static class PasswordRule2 {
        int firstPlace;
        int lastPlace;
        char ruleLetter;

        public PasswordRule2(int firstPlace, int lastPlace, char ruleLetter) {
            this.firstPlace = firstPlace;
            this.lastPlace = lastPlace;
            this.ruleLetter = ruleLetter;
        }

        public boolean matches(String password) {
            return isLetterAtPlace(password, firstPlace) ^ isLetterAtPlace(password, lastPlace);
        }

        private boolean isLetterAtPlace(String password, int place) {
            if (password.length() < place) {
                return false;
            }
            return password.charAt(place - 1) == ruleLetter;
        }
    }
}
