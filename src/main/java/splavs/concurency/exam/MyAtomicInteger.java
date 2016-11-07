package splavs.concurency.exam;

/**
 * Created by Vyacheslav Silchenko on 07.06.2016.
 */
public class MyAtomicInteger  {
    private volatile int value;

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public final int getAndIncrement() {
        return getAndAdd(1);
    }

    public final int getAndDecrement() {
        return getAndAdd(-1);
    }

    private int getAndAdd(int addValue) {
        while (true) {
            int current = getValue();
            if (compareAndSet(current, addValue)) {
                return current;
            }
        }
    }

    private synchronized boolean compareAndSet(int expectedValue, int newValue) {
        if (value == expectedValue) {
            value = newValue;
            return true;
        }

        return false;
    }
}
