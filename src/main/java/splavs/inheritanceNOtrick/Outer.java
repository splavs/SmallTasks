package splavs.inheritanceNOtrick;

/**
 * Class description goes here.
 *
 * @author Vyacheslav Silchenko
 */
public class Outer {
    public static void main(String[] args) {
        Outer outer = new Outer();
        Inner inner = outer.new Inner();
    }
    class Inner {
        int value;
    }
}