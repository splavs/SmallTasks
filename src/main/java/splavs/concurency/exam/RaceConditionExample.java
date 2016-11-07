package splavs.concurency.exam;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vyacheslav Silchenko on 07.06.2016.
 */
public class RaceConditionExample {

    int x = 7;

    public static void main(String[] args) {
        RaceConditionExample example = new RaceConditionExample();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Runnable task1 = () -> {
            if (example.x == 7) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(example.x * 3);
            }
        };

        Runnable task2 = () -> example.x = 14;

        executorService.submit(task1);
        executorService.submit(task2);
        executorService.shutdown();


    }
}
