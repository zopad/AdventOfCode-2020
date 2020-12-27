import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class Task1 {

    @Test
    public void testTask1() {
        assertEquals(177337980, getProduct());
    }

    private static int getProduct() {
        AtomicInteger product = new AtomicInteger();
        List<Integer> inputList = getInputList();
        inputList.forEach(outer -> inputList.forEach(inner -> {
            inputList.forEach(third -> {
                if (outer + inner + third == 2020) {
                    System.out.println("FOUND: " + inner + " " + outer + " " + third);
                    System.out.println("SOLUTION: " + inner * outer * third);
                    product.set(inner * outer * third);
                }
            });
        }));
        return product.get();
    }

    private static List<Integer> getInputList() {
        try {
            return Files.lines(Paths.get("input1.txt")).map(Integer::parseInt).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
