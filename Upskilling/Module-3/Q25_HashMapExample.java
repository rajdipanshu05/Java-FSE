// Q25 - HashMap Example
import java.util.HashMap;
import java.util.Scanner;

public class Q25_HashMapExample {
    public static void main(String[] args) {
        HashMap<Integer, String> students = new HashMap<>();
        Scanner sc = new Scanner(System.in);

        System.out.print("How many entries to add? ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter student ID: ");
            int id = sc.nextInt();
            System.out.print("Enter student name: ");
            String name = sc.next();
            students.put(id, name);
        }

        System.out.print("Enter ID to search: ");
        int searchId = sc.nextInt();

        if (students.containsKey(searchId)) {
            System.out.println("Student: " + students.get(searchId));
        } else {
            System.out.println("No student found with ID " + searchId);
        }

        sc.close();
    }
}
