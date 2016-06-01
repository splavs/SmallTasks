package splavs.concurency.module3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Viacheslav_Silchenko on 24-May-16.
 */
public class AlphabetSample {

  public static void main(String[] args) {
    final BlockingQueue<Character> bq = new ArrayBlockingQueue<>(2);


    Runnable producer = () -> {
      for (char ch = 'A';ch <= 'Z'; ch++) {
        try {
          bq.put(ch);
          System.out.println(ch + " was produced");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

    Runnable consumer = () -> {
      char ch = '\0';
      do {
        try {
          ch = bq.take();
          System.out.println(ch + " was taken");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

      } while (ch != 'Z');
    };

    new Thread(producer).start();

    new Thread(consumer).start();


  }



}
