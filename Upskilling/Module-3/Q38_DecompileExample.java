// Q38 - Decompile a Class File
// Step 1: Compile this file -> javac Q38_DecompileExample.java
// Step 2: Open DecompileExample.class in JD-GUI or run:
//         java -jar cfr.jar Q38_DecompileExample.class

public class Q38_DecompileExample {

    private String name;
    private int age;

    Q38_DecompileExample(String name, int age) {
        this.name = name;
        this.age  = age;
    }

    void display() {
        System.out.println("Name: " + name + ", Age: " + age);
    }

    public static void main(String[] args) {
        Q38_DecompileExample obj = new Q38_DecompileExample("Alice", 25);
        obj.display();
    }
}
