// Q41 - Executor Service and Callable
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceExample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<Integer>> results = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            int taskNum = i;
            Callable<Integer> task = () -> {
                System.out.println("Task " + taskNum + " running on " + Thread.currentThread().getName());
                return taskNum * taskNum;
            };
            results.add(executor.submit(task));
        }

        for (Future<Integer> future : results) {
            System.out.println("Result: " + future.get());
        }

        executor.shutdown();
    }
}
