// Q8 - Operator Precedence
public class OperatorPrecedence {
    public static void main(String[] args) {
        int result1 = 10 + 5 * 2;
        System.out.println("10 + 5 * 2 = " + result1);  // 20, * first

        int result2 = (10 + 5) * 2;
        System.out.println("(10 + 5) * 2 = " + result2); // 30, () first

        int result3 = 10 + 6 / 2 - 1;
        System.out.println("10 + 6 / 2 - 1 = " + result3); // 12

        int result4 = 2 + 3 * 4 - 8 / 2;
        System.out.println("2 + 3 * 4 - 8 / 2 = " + result4); // 10
    }
}
