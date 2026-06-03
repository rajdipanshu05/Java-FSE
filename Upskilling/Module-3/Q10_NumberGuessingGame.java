// Q10 - Number Guessing Game
import java.util.Random;
import java.util.Scanner;

public class Q10_NumberGuessingGame {
    public static void main(String[] args) {
        Random rand = new Random();
        int secret = rand.nextInt(100) + 1;
        Scanner sc = new Scanner(System.in);
        int guess;

        System.out.println("Guess the number between 1 and 100:");

        do {
            System.out.print("Your guess: ");
            guess = sc.nextInt();

            if (guess < secret) {
                System.out.println("Too low!");
            } else if (guess > secret) {
                System.out.println("Too high!");
            } else {
                System.out.println("Correct! The number was " + secret);
            }
        } while (guess != secret);

        sc.close();
    }
}
