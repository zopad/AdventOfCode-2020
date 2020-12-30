import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class Task6 {

    @Test
    public void testTask6() throws IOException {
        assertEquals(6686, getCombinedAnyYesCount());
    }

    @Test
    public void testTask6_2() throws IOException {
        assertEquals(3476, getCombinedAllYesCount());
    }

    private int getCombinedAnyYesCount() throws IOException {
        String wholeFile = Files.lines(Paths.get("input6.txt")).collect(Collectors.joining("\n"));
        String[] groups = wholeFile.split("\n\n");
        return Arrays.stream(groups).map(line -> line.replace("\n", "")).map(groupLines ->
                ((int) groupLines.chars().distinct().count())).mapToInt(Integer::intValue).sum();
    }

    private int getCombinedAllYesCount() throws IOException {
        String wholeFile = Files.lines(Paths.get("input6.txt")).collect(Collectors.joining("\n"));
        String[] groups = wholeFile.split("\n\n");
        return Arrays.stream(groups).map(groupLines ->
        {
            String[] lines = groupLines.split("\n");
            if (lines.length == 0) {
                return 0;
            }
            Set<Integer> allYes = lines[0].chars().boxed().collect(Collectors.toSet());
            for (String line : lines) {
                allYes.retainAll(line.chars().boxed().collect(Collectors.toSet()));
            }
            return allYes.size();
        }).mapToInt(Integer::intValue).sum();
    }


}
