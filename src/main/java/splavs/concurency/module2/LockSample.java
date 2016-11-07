package splavs.concurency.module2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Vyacheslav Silchenko on 05.06.2016.
 */
public class LockSample {
    public Integer counter = 0;
    public Lock lock = new ReentrantLock();



    public static void main(String[] args) throws InterruptedException {

        LockSample resource = new LockSample();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                resource.lock.lock();
                try {
                    resource.counter++;
                } finally {
                    resource.lock.unlock();
                }
            }

        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                resource.lock.lock();
                try {
                    resource.counter--;
                } finally {
                    resource.lock.unlock();
                }
            }

        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(resource.counter);


    }
}
