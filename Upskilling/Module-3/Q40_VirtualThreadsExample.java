// Q40 - Virtual Threads (Java 21)
public class Q40_VirtualThreadsExample {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        Thread[] threads = new Thread[100_000];

        for (int i = 0; i < 100_000; i++) {
            int threadNum = i;
            threads[i] = Thread.startVirtualThread(() -> {
                // Each virtual thread prints a message
                System.out.println("Virtual thread " + threadNum + " running.");
            });
        }

        for (Thread t : threads) {
            t.join();
        }

        long end = System.currentTimeMillis();
        System.out.println("All virtual threads done in " + (end - start) + "ms");
    }
}
