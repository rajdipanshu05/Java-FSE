// Q29 - Records (Java 16+)
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

record Person(String name, int age) {}

public class Q29_RecordsExample {
    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
            new Person("Alice", 30),
            new Person("Bob", 17),
            new Person("Charlie", 25),
            new Person("Diana", 15)
        );

        System.out.println("All people:");
        people.forEach(System.out::println);

        List<Person> adults = people.stream()
                .filter(p -> p.age() >= 18)
                .collect(Collectors.toList());

        System.out.println("Adults only:");
        adults.forEach(System.out::println);
    }
}
