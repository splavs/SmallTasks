package splavs;

import java.util.Map;

/**
 * Class description goes here.
 *
 * @author Vyacheslav Silchenko
 */
public class LruCacheTest {
    public static void main(String[] args) {
        Map<Integer, String> cache = new LruCache<Integer, String>(10);

        System.out.println("Populating data into cache");
        for (int i = 0; i < 10; i++) {
            cache.put(i, String.valueOf(i));
        }

        System.out.println("Now Cache is " + cache);

        System.out.println("Reading data from the cache");
        for (int i = 9; i >= 0; i--) {
            System.out.println(i + "key has value " + cache.get(i));
        }

        System.out.println("Now Cache is " + cache);

        System.out.println("Adding new element 10,10");
        cache.put(10, "10");

        System.out.println("Now Cache is " + cache);

    }

}
