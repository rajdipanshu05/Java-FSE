// Q15 - String Reversal
import java.util.Scanner;

public class Q15_StringReversal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = sc.nextLine();

        String reversed = new StringBuilder(input).reverse().toString();
        System.out.println("Reversed: " + reversed);
        sc.close();
    }
}
