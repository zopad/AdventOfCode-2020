import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class Task5 {

    @Test
    public void testTask5() throws IOException {
        assertEquals(842, getHighestSeatId());
    }

    @Test
    public void testTask5_2() throws IOException {
        assertEquals(617, getMissingSeatId());
    }

    private int getMissingSeatId() throws IOException {
        List<Integer> seatIds = Files.lines(Paths.get("input5.txt")).map(this::convertToSeat).map(Seat::getId).collect(Collectors.toList());
        Optional<Integer> first = IntStream.range(28, 1000).boxed().filter(number ->
                !seatIds.contains(number)
        ).findFirst();
        return first.orElse(0);
    }

    private int getHighestSeatId() throws IOException {
        Optional<Seat> max = Files.lines(Paths.get("input5.txt")).map(this::convertToSeat).max(Comparator.comparingInt(Seat::getId));
        return max.map(Seat::getId).orElse(0);
    }

    private Seat convertToSeat(String binaryPartitioned) {
        String rows = binaryPartitioned.substring(0, 7);
        rows = rows.replace('F', '0');
        rows = rows.replace('B', '1');
        String cols = binaryPartitioned.substring(7, 10);
        cols = cols.replace('L', '0');
        cols = cols.replace('R', '1');
        return new Seat(Integer.parseInt(cols, 2), Integer.parseInt(rows, 2));
    }

    private static class Seat {
        int column; // 0-7
        int row; // 0-127

        public Seat(int column, int row) {
            this.column = column;
            this.row = row;
        }

        public int getId() {
            return row * 8 + column;
        }
    }
}
