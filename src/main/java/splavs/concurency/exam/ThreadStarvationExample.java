package splavs.concurency.exam;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vyacheslav Silchenko on 07.06.2016.
 */
public class ThreadStarvationExample {
    public static volatile boolean IS_RUNNING = true;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Runnable starvedTask = () -> {
            Thread.currentThread().setName("Starved thread");
            int counter = 0;
            while (IS_RUNNING) {
                counter++;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName() + " " + counter);

        };

        Runnable normalTask = () -> {
            Thread.currentThread().setName("Normal thread");
            int counter = 0;
            while (IS_RUNNING) {
                counter++;
            }

            System.out.println(Thread.currentThread().getName() + " " + counter);

        };

        executorService.submit(starvedTask);
        executorService.submit(normalTask);

        TimeUnit.SECONDS.sleep(3);
        IS_RUNNING = false;

        executorService.shutdown();


    }
}
