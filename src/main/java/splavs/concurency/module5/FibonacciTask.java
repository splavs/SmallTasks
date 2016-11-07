package splavs.concurency.module5;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Vyacheslav Silchenko on 07.06.2016.
 */
public class FibonacciTask extends RecursiveTask<Integer> {

    private Integer fibIndex;

    private static AtomicInteger taskCounter = new AtomicInteger(0);

    public FibonacciTask(Integer fibIndex) {
        this.fibIndex = fibIndex;
    }

    public static AtomicInteger getTaskCounter() {
        return taskCounter;
    }

    @Override
    protected Integer compute() {
        if (fibIndex == 0) {
            return 0;
        }

        if (fibIndex == 1) {
            return 1;
        }

        if (fibIndex == 2) {
            return 1;
        }

        if (fibIndex == 3) {
            return 2;
        }

        final FibonacciTask task1 = new FibonacciTask(fibIndex - 1);
        task1.fork();
        taskCounter.getAndIncrement();
        final Integer result1 = task1.join();

        final FibonacciTask task2 = new FibonacciTask(fibIndex - 2);
        final Integer result2 = task2.compute();
        taskCounter.getAndIncrement();


        return result1 + result2;
    }
}
