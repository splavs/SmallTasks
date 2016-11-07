package splavs.concurency.homework;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Vyacheslav Silchenko on 06.06.2016.
 */
@Deprecated
public class HighLoad {
//    public static final int SIZE = 1_000_000_000;
    public static final int SIZE = 1_000;
    private int[] array = new int[SIZE];

    public void initArray() {
        for (int i = 0; i < SIZE; i++) {
            array[i] = i;
        }
    }

    public static void main(String[] args) {

        HighLoad highLoad = new HighLoad();
        highLoad.initArray();

        Runnable readTask = () -> {
            final int i = ThreadLocalRandom.current().nextInt(SIZE);
            synchronized (highLoad.array) {
                final int val = highLoad.array[i];
            }

        };

        Runnable writeTask = () -> {
            final int i = ThreadLocalRandom.current().nextInt(SIZE);
            synchronized (highLoad.array) {
                highLoad.array[i] = i;
            }
        };

        int N = 10;
        for (int i = 0; i < N; i++) {
            final Thread readThread = new Thread(readTask);
            readThread.setName("ReadThread" + i);
            readThread.start();
            System.out.println(readThread.getName() + " started");
        }

        int M = 1;
        for (int i = 0; i < N; i++) {
            final Thread writeThread = new Thread(writeTask);
            writeThread.setName("WriteThread" + i);
            writeThread.start();
            System.out.println(writeThread.getName() + " started");
        }



    }


}
