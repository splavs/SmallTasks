package splavs;

/**
 * lazy sync singleton with on demand holder
 *
 * @author Vyacheslav Silchenko
 */
public enum Singleton4 {
    INSTANCE;

    public void echo() {
        System.out.println(INSTANCE);
        double d = 5.5;
        Double.hashCode(d);
    }
}