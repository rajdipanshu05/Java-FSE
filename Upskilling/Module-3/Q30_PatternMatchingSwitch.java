// Q30 - Pattern Matching for switch (Java 21)
public class Q30_PatternMatchingSwitch {

    static void checkType(Object obj) {
        String result = switch (obj) {
            case Integer i -> "Integer: " + i;
            case String s  -> "String: " + s;
            case Double d  -> "Double: " + d;
            default        -> "Unknown type: " + obj.getClass().getSimpleName();
        };
        System.out.println(result);
    }

    public static void main(String[] args) {
        checkType(42);
        checkType("Hello");
        checkType(3.14);
        checkType(true);
    }
}
