package splavs;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class description goes here.
 *
 * @author Vyacheslav Silchenko
 */
public class LruCache<key, value> extends LinkedHashMap<key, value> {
    private final int maxEntries;

    // The most important is true
    // accessOrder - the ordering mode - true for access-order, false for insertion-order
    public LruCache(int maxEntries) {
        super(maxEntries + 1, 0.75f, true);
        this.maxEntries = maxEntries;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<key, value> eldest) {
        return super.size() > maxEntries;
    }
}
