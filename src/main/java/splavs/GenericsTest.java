package splavs;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description goes here.
 *
 * @author Vyacheslav Silchenko
 */
public class GenericsTest {
    public static void main(String[] args) {
        List<?>[] a = {}; //ok
        //List<? extends Object>[] b = {}; //fail

        //a instanceof ArrayList<?> //ok
        //a instanceof ArrayList<? extends Object> //fail

    }

}
