package splavs;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Class description goes here.
 *
 * @author Vyacheslav Silchenko
 */
public class Reflection1 {
    public static void main(String[] args) throws Exception {
        Integer number = 7;
        System.out.println(number); // out -> 7

        //some magic
        Field f  = Integer.class.getDeclaredField("value");
        f.setAccessible(true);
        f.set(7, 42);

        System.out.println(number); // out -> 42
    }


}
