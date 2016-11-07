package splavs.concurency.module4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Vyacheslav Silchenko on 05.06.2016.
 */
public class CallableCubeInFuture {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        List<Future<Long>> results = new ArrayList<>();

        for (int i = 0; i < 1_000; i++) {
            final int val = i;

            Callable<Long> task = () -> {
                TimeUnit.SECONDS.sleep(1);
                long result = val * val * val;
                System.out.println(Thread.currentThread().getName());
                return result;
            };

            Future<Long> result = pool.submit(task);
            results.add(result);
        }

        try {
            pool.shutdown();
            pool.awaitTermination(10, TimeUnit.SECONDS);

            long sum = 0;

            for (Future<Long> result : results) {
                System.out.println(result.isDone());
                sum += result.get();
                System.out.println(result.isDone());
            }
            System.out.println("Sum is = " + sum);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pool.shutdownNow();
        }


    }
}
