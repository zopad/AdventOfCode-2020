import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class Task3 {

    private static final char TREE = '#';
    private static final char EMPTY = '.';

    @Test
    public void testTask3() {
        assertEquals(64, countEncounteredTrees(1, 1));
        assertEquals(259, countEncounteredTrees(3, 1));
        assertEquals(65, countEncounteredTrees(5, 1));
        assertEquals(59, countEncounteredTrees(7, 1));
        assertEquals(35, countEncounteredTrees(1, 2));
        System.out.println("Final solution: " + BigInteger.valueOf(64*259*65*59*35L));
    }

    private static int countEncounteredTrees(int rightStep, int downStep) {
        int count = 0;
        int horizontalPos = 0;
        try {
            String[] lines = Files.lines(Paths.get("input3.txt")).skip(downStep).toArray(String[]::new);
            for (int i = 0; i < lines.length; i += downStep) {
                String line = lines[i];
                horizontalPos += rightStep;
                if (horizontalPos >= line.length()) {
                    horizontalPos = horizontalPos % line.length(); // rows keep repeating (wrap back to start)
                }
                if (line.charAt(horizontalPos) == TREE) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}
