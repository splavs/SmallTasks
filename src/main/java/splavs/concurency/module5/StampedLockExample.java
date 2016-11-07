package splavs.concurency.module5;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by Vyacheslav Silchenko on 07.06.2016.
 */
public class StampedLockExample {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Map<String, Integer> map = new HashMap<>();
        StampedLock lock = new StampedLock();

        service.submit(() -> {
            long stamp = lock.writeLock();

            try {
                TimeUnit.SECONDS.sleep(1);
                map.put("Moscow", 11_000_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlockWrite(stamp);
            }

        });

        Runnable readTask = () -> {
            long stamp;

            if ((stamp = lock.tryOptimisticRead()) != 0L) {
                final Integer population = map.get("Moscow");
                if (lock.validate(stamp)) {
                    System.out.println(population + " from optimistic reading");
                }
            }

            stamp = lock.readLock();

            try {
                System.out.println(map.get("Moscow") + " from readlock reading");
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlockRead(stamp);
            }


        };

        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);

    }



}
