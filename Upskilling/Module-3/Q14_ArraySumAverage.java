// Q14 - Array Sum and Average
import java.util.Scanner;

public class Q14_ArraySumAverage {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of elements: ");
        int n = sc.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Element " + (i + 1) + ": ");
            arr[i] = sc.nextInt();
        }

        int sum = 0;
        for (int val : arr) {
            sum += val;
        }

        System.out.println("Sum: " + sum);
        System.out.println("Average: " + (double) sum / n);
        sc.close();
    }
}
