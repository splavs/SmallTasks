package splavs.concurency.module5;

import java.util.concurrent.ForkJoinPool;

/**
 * Created by Vyacheslav Silchenko on 07.06.2016.
 */
public class ForkJoinSample {
    public static final Integer FIBONACCI_INDEX = 40;

    public static void main(String[] args) {
        final ForkJoinPool pool = new ForkJoinPool();

        FibonacciTask task = new FibonacciTask(FIBONACCI_INDEX);

        final long startTime = System.currentTimeMillis();
        Integer fibNumber = pool.invoke(task);
        System.out.println(fibNumber);
        System.out.println(System.currentTimeMillis() - startTime);

        System.out.println(FibonacciTask.getTaskCounter());


    }

}
