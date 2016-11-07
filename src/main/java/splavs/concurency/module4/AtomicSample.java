package splavs.concurency.module4;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Vyacheslav Silchenko on 05.06.2016.
 */
public class AtomicSample {
    public AtomicInteger counter = new AtomicInteger();
    public Lock lock = new ReentrantLock();



    public static void main(String[] args) throws InterruptedException {

        AtomicSample resource = new AtomicSample();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                resource.counter.getAndIncrement();
            }

        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                resource.counter.getAndDecrement();
            }

        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(resource.counter);


    }
}
