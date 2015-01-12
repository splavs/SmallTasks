package splavs;

import java.util.Calendar;

/**
 * Class description goes here.
 *
 * @author Vyacheslav Silchenko
 */
public class ArbitraryNumberOfArguments {
    public static void main(String[] args) {
        ArbitraryNumberOfArguments arbitraryNumberOfArguments = new ArbitraryNumberOfArguments();
        arbitraryNumberOfArguments.test("1", "2", "3");

        String[] strings = {"str1", "str2", "str3"};
        arbitraryNumberOfArguments.test(strings);
    }

    public void test(Object... objects) {
        for (Object obj : objects) {
            System.out.print(obj + " ");


        }

    }

}
