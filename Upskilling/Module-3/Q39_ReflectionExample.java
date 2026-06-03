// Q39 - Reflection in Java
import java.lang.reflect.Method;

class SampleClass {
    public void sayHello() {
        System.out.println("Hello from SampleClass!");
    }

    public int square(int n) {
        return n * n;
    }
}

public class Q39_ReflectionExample {
    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("SampleClass");

        System.out.println("Methods in SampleClass:");
        for (Method m : cls.getDeclaredMethods()) {
            System.out.println("  " + m.getName());
        }

        Object obj = cls.getDeclaredConstructor().newInstance();
        Method sayHello = cls.getMethod("sayHello");
        sayHello.invoke(obj);

        Method square = cls.getMethod("square", int.class);
        Object result = square.invoke(obj, 5);
        System.out.println("square(5) = " + result);
    }
}
