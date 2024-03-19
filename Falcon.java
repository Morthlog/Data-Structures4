/*
The peregrine falcon (Falco peregrinus) is the fastest known creature
in the animal kingdom, reaching speeds of over 387 kilometers per hour.
Authors: Vasileios Papadimas (3220150) & Mario Matsas (3220120)
*/

import java.lang.reflect.Array;

public class Falcon<K, V> implements Cache<K, V> {

    private class Record<thisK, thisV> {
        thisK key;
        thisV value;
        int left = -1;
        int right = -1;
    }

    private final Record<K, V>[] data;
    private int head = -1;
    private int tail = -1;

    private static int totalCapacity; // how many cells in the hashtable
    private int cacheSize; // how much space remaining to store data
    private long lookups = 0;
    private long hits = 0;

    @SuppressWarnings("unchecked")
    public <thisK, thisV> Falcon(int N) {
        totalCapacity = (int) Math.ceil(N / 0.75);
        data = (Record<K, V>[]) Array.newInstance((new Record<thisK, thisV>()).getClass(), totalCapacity);
        for (int i = 0; i < totalCapacity; i++) {
            data[i] = new Record<K, V>();
        }
        cacheSize = N;
    }

    /**
     * Look for data associated with key.
     * 
     * @param key the key to look for
     * @return The associated data or null if it is not found
     */

    @Override
    public V lookUp(K key) {
        ++lookups;
        int start = key.hashCode() % totalCapacity; // hash(key.hashCode());
        int pos = start;
        do {
            if (data[pos].key == null) {
                return null;
            }
            if (key.equals(data[pos].key)) {
                remove(pos);
                add(pos);
                ++hits;
                return data[pos].value;
            }
            pos = (pos + 1) % totalCapacity;
        } while (pos != start);
        return null;
    }

    /**
     * Stores data with associated with the given key. If required, it evicts a
     * data record to make room for the new one
     * 
     * @param key   the key to associate with the data
     * @param value the actual data
     */

    @Override
    public void store(K key, V value) {
        int pos = key.hashCode() % totalCapacity; // hash(key.hashCode());
        if (cacheSize == 0) {
            int currentHead = head;
            remove(currentHead);
            shift(currentHead);
            ++cacheSize;
            do {
                // we simply search for the new empty spot
                if (data[pos].key == null) {
                    data[pos].key = key;
                    data[pos].value = value;
                    add(pos);
                    --cacheSize;
                    return;
                }
                pos = (pos + 1) % totalCapacity; // Wraps around the array
            } while (true);
        }
        do {
            if (data[pos].key != null) { // if cell isn't empty
                if (data[pos].key.equals(key)) { // if key already at data[hash]
                    data[pos].value = value; // we update the value
                    remove(pos); // move it to tail, updating priority
                    add(pos);
                    return;
                }
            } else {
                // If the position is null then the key isn't in the cache yet
                // We have space, so just insert it
                data[pos].key = key;
                data[pos].value = value;
                add(pos);
                --cacheSize;
                return;
            }
            pos = (pos + 1) % totalCapacity; // Wraps around the array
        } while (true);
    }

    /**
     * Returns the hit ratio, i.e. the number of times a lookup was successful
     * divided by the number of lookup
     * 
     * @return the cache hit ratio
     */
    @Override
    public double getHitRatio() {
        return (double) hits / lookups;
    }

    /**
     * Returns the absolute number of cache hits, i.e. the number of times a lookup
     * found data in the cache
     */
    @Override
    public long getHits() {
        return hits;
    }

    /**
     * Returns the absolute number of cache misses, i.e. the number of times a
     * lookup returned null
     */
    @Override
    public long getMisses() {
        return lookups - hits;
    }

    /**
     * Returns the total number of lookups performed by this cache
     */
    @Override
    public long getNumberOfLookUps() {
        return lookups;
    }

    /*
     * Helpers for store
     */

    /*
    private static int hash(long h) { // based on murmur64
        h ^= h >>> 33;
        h *= 0xff51afd7ed558ccdL;
        h ^= h >>> 33;
        h *= 0xc4ceb9fe1a85ec53L;
        h ^= h >>> 33;
        return Math.floorMod(h, totalCapacity);
    }
    */

    private void remove(int pos) {
        // If there is another object to the left of the one to be deleted then set that
        // one's right as the right of the selected one
        if (data[pos].left >= 0) {
            data[data[pos].left].right = data[pos].right;
        }
        // otherwise change the head pointer
        else {
            head = data[pos].right;
        }
        // If there is another object to the right of the one to be deleted then set
        // that one's left as the left of the selected one
        if (data[pos].right >= 0) {
            data[data[pos].right].left = data[pos].left;
        }
        // otherwise change the tail pointer
        else {
            tail = data[pos].left;
        }
    }

    private void add(int pos) {
        // Place the data back at the end (in a way updating it's priority in the cache)
        if (tail >= 0) {
            data[tail].right = pos;
        }
        data[pos].left = tail;
        data[pos].right = -1;
        tail = pos;
        if (head < 0) {
            head = tail;
        }
    }

    private void shift(int pos) {
        int free;
        int current;
        do {
            free = pos;
            pos = (pos + 1) % totalCapacity;
            while (true) {
                if (data[pos].key == null) {
                    data[free].key = null;
                    return;
                }
                current = data[pos].key.hashCode() % totalCapacity; // hash(data[pos].key.hashCode());
                if ((free <= pos && (free >= current || current > pos)) ||
                        (free > pos && (pos < current && current <= free))) {
                    break;
                }
                pos = (pos + 1) % totalCapacity;
            }
            // needs to be done this way to avoid shallow copying
            data[free].key = data[pos].key;
            data[free].value = data[pos].value;
            data[free].left = data[pos].left;
            data[free].right = data[pos].right;
            if (data[free].left >= 0) {
                data[data[free].left].right = free;
            }
            if (data[free].right >= 0) {
                data[data[free].right].left = free;
            }
            if (pos == tail) {
                tail = free;
            }
            if (pos == head) {
                head = free;
            }
        } while (true);
    }
}
