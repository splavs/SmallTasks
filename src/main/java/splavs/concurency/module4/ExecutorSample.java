package splavs.concurency.module4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vyacheslav Silchenko on 05.06.2016.
 */
public class ExecutorSample {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Executors.newCachedThreadPool();

        Runnable task1 = () -> {
            System.out.println("Hello from task 1 " + Thread.currentThread().getName());

        };
        Runnable task2 = () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("Hello from task 2 " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        };

        executor.submit(task1);
        executor.submit(task2);

        System.out.println("Trying to shutdown");

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.println("Task was interrupted");
        } finally {
//            if (!executor.isTerminated()) {
//                System.out.println("Start tasks cancellation");
//            }
            executor.shutdownNow();
            System.out.println("Pool is dead");

        }



    }
}
