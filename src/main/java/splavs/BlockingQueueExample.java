package splavs;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description goes here.
 *
 * @author Vyacheslav Silchenko
 */
public class BlockingQueueExample<T> implements Runnable {
    private int capacity;
    private List<T> queue;

    public static void main(String[] args) throws InterruptedException{
        BlockingQueueExample<String> blockingQueueExample = new BlockingQueueExample<String>(10);

        System.out.println("Putting 10 element to the queue");
        for (int i = 0; i < 10; i++) {
            blockingQueueExample.put(Integer.toString(i));
        }
        System.out.println("Done Putting 10 element to the queue");

        new Thread(blockingQueueExample).start();

        System.out.println("Putting ANOTHER 10 element to the queue");
        for (int i = 10; i < 20; i++) {
            blockingQueueExample.put(Integer.toString(i));
        }
        System.out.println("Done Putting ANOTHER 10 element to the queue");


    }

    public BlockingQueueExample(int capacity) {
        this.capacity = capacity;
        queue = new ArrayList<>();
    }

    @Override
    public void run() {
        System.out.println("GETTING 10 element to the queue");
        for (int i = 0; i < 10; i++) {
            try {
                get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Done GETTING 10 element to the queue");
    }

    public synchronized void put(T t) throws InterruptedException{
        while (queue.size() == capacity) {
            wait();
        }

        queue.add(t);
        notify();

    }

    public synchronized T get() throws InterruptedException{
        while (queue.isEmpty()) {
            wait();
        }

        T item = queue.remove(queue.size() - 1);
        notify();
        return item;
    }
}
