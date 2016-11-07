package splavs;

/**
 * Class description goes here.
 *
 * @author Vyacheslav Silchenko
 */
public class WaitNotifyTest {

    public static void main(String[] args) {
        TestThread testThread = new TestThread();
        Thread thread = new Thread(testThread);
        thread.start();
        System.out.println("Waiting 10 sec for wakeup");
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        testThread.wakeUp();

    }
}

class TestThread implements Runnable {
    Object monitor = new Object();

    @Override
    public void run() {
        synchronized (monitor) {
            System.out.println("Before wait");
            try {
                monitor.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("After wait");

            System.out.println("Before notify");
            try {
                monitor.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("After notify");
        }
    }

    public void wakeUp() {
        synchronized (monitor) {
            monitor.notify();
        }
    }
}