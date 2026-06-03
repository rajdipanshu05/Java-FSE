// Q37 - Using javap to Inspect Bytecode
// Compile this file and then run: javap -c BytecodeInspection
// Output will show the JVM bytecode instructions.

public class BytecodeInspection {

    static int addNumbers(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        int result = addNumbers(5, 10);
        System.out.println("Result: " + result);
    }
}

// Steps:
// 1. javac BytecodeInspection.java
// 2. javap -c BytecodeInspection
