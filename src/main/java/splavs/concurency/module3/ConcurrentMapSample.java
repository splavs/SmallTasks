package splavs.concurency.module3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Viacheslav_Silchenko on 24-May-16.
 */
public class ConcurrentMapSample {

  public static void main(String[] args) {

    Map<Integer, Integer> resource = new ConcurrentHashMap<>();
    List<Thread> pool = new ArrayList<>();

    for (int i = 0; i < 10_000; i++) {
      final int val = i;
      Thread t = new Thread(()->{
        try {
          Thread.sleep(ThreadLocalRandom.current().nextInt(100));

//          synchronized (resource) {
            resource.put(val, val);
//          }

        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });

      pool.add(t);
      t.setName(String.valueOf(val));
    }

    long startTime = System.currentTimeMillis();

    pool.forEach(e->e.start());
    pool.forEach(e -> {
      try {
        e.join();
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }
    });

    System.out.println(System.currentTimeMillis() - startTime);

    System.out.println("Size is " + resource.size());


  }
}
