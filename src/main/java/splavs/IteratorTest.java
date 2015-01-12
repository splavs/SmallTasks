package splavs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class description goes here.
 *
 * @author Vyacheslav Silchenko
 */
public class IteratorTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");

        Iterator<String> i = list.iterator();

        while (i.hasNext()) {
            String next = i.next();
            System.out.println(next);
            if ("four".equals(next)) {
                System.out.println("Removing " + next);
                i.remove();
            }
        }

        System.out.println(list);

        list.add("four");
        list.remove("four");

        System.out.println(list);

    }
}
