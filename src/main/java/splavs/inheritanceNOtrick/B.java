package splavs.inheritanceNOtrick;

/**
 * Class description goes here.
 *
 * @author Vyacheslav Silchenko
 */
class B extends A {
    int b;
    public B() {
        this(25);
    }
    public B(int b) {
        this.b = b;
    }

    public static void main(String[] args) {
        B b1 = new B();
        B b2 = new B(42);

        System.out.println(b1.a + " " + b1.b);
        System.out.println(b2.a + " " + b2.b);
    }
}