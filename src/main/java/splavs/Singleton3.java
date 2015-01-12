package splavs;

/**
 * lazy sync singleton with on demand holder
 *
 * @author Vyacheslav Silchenko
 */
public class Singleton3 {

    private static class SingletonHolder {
        public static final Singleton3 instance = new Singleton3();
    }

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        return SingletonHolder.instance;
    }
}