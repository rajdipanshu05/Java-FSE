// Q27 - Lambda Expressions
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LambdaExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Zara", "Alice", "Mona", "Bob", "Charlie");

        Collections.sort(names, (a, b) -> a.compareTo(b));

        System.out.println("Sorted list:");
        names.forEach(name -> System.out.println(name));
    }
}
