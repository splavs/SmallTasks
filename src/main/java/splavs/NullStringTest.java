package splavs;

/**
 * Class description goes here.
 *
 * @author Vyacheslav Silchenko
 */
public class NullStringTest {
    public boolean test(String str) {
        return str.equals("test");
    }

    public static void main(String[] args) {
        new NullStringTest().test(null);
    }

}
