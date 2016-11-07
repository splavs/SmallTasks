package splavs.concurency.module4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Vyacheslav Silchenko on 05.06.2016.
 */
public class MyBlockingQueue<E>  {

    private List<E> queue = new LinkedList<>();

    private Lock lock = new ReentrantLock();

    public void enque(E e) {
        lock.lock();
        try {
            queue.add(e);
        } finally {
            lock.unlock();
        }
    }

    public E deque() {
        lock.lock();
        try {
            if (!isEmpty())
                return queue.remove(0);
            else
                return null;

        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        MyBlockingQueue<Integer> queue = new MyBlockingQueue<>();

        Runnable producer = () -> {
            for (int i = 0; i < 10; i++) {
                queue.enque(i);
                System.out.println("PRODUCER: Added" + i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable consumer = () -> {
            for (int i = 0; i < 10; i++) {
                final Integer val = queue.deque();
                System.out.println("CONSUMER: Got" + val);
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t1 = new Thread(producer);
        Thread t2 = new Thread(consumer);

        t1.start();
        t2.start();


    }


}
