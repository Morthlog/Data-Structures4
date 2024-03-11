
public class CacheImpl11<K, V> implements Cache<K, V> {

    protected NodeIndex<K, V>[] cachedData;
    //* HashMap
    protected SeparateChainingV3<K> dataPointer ;
    int size = 0, sizeMax;
    NodeIndex<K, V> first = null, last = null;
    long misses = 0, hits = 0, lookups = 0;
  
    @SuppressWarnings("unchecked")
    CacheImpl11()
    {
        sizeMax = 100;
        cachedData = new NodeIndex[sizeMax];
    }

    @SuppressWarnings("unchecked")
    CacheImpl11(int size)
    {
        this.sizeMax = size;
        cachedData = new NodeIndex[sizeMax];
        dataPointer = new SeparateChainingV3<K>(sizeMax);
    }

/**
	 * Look for data associated with key. 
	 * @param key the key to look for
	 * @return The associated data or null if it is not found
	 */
	public V lookUp(K key)
    {
		++lookups;
        //! search in HashMap for key and return the node's data
        int index = dataPointer.get(key);
        if (index == -1)
        {
        	++misses;
            return null;
        }
        else
        {
        	++hits;
            if (cachedData[index] == last)
            {
                first.next = last;
                last.prev = first;

                last = last.next;
                first = first.next;
            }
            else if (cachedData[index] != first) // no position update needed
            {
                cachedData[index].prev.next = cachedData[index].next;
                cachedData[index].next.prev = cachedData[index].prev;

                first.next = cachedData[index];
                cachedData[index].prev = first;
                first = cachedData[index];
            }

            return cachedData[index].data;
        }
    }
	
	/**
	 * Stores data with associated with the given key. If required, it evicts a
	 * data record to make room for the new one
	 * @param key the key to associate with the data
	 * @param value the actual data
	 */
	public void store(K key, V value)
    {
        if (sizeMax <= size)
        {
            K lastKey = last.key;
            int ind=last.index;
            first.next = last;
            last.prev = first;
            // replace data of last with new data
            last.data = value;
            last.key = key;

            last = last.next;
            first = first.next;
            
            //! delete lastKey from hashMap
            dataPointer.delete(lastKey);   

            //! add to hashMap which points to index of lastKey, which is now first
            dataPointer.put(key, ind);
        }
        else
        {
            cachedData[size] = new NodeIndex<K, V>(key, value, size);
            if (size == 0)
            {
                first = cachedData[size];
                last = first;
            }
            else
            {
                cachedData[size].prev = first;
                first.next = cachedData[size];
                first = cachedData[size];
            }

            //! add to hashMap which points to first
            dataPointer.put(key, size);
            ++size;
        }
    }
	
	/**
	 * Returns the hit ratio, i.e. the number of times a lookup was successful divided by the number of lookup 
	 * @return the cache hit ratio
	 */
	public double getHitRatio()
    {
		if(lookups>0L)		
			return (double) hits/lookups;
		else	
			return 0;		
    }
	
	/**
	 * Returns the absolute number of cache hits, i.e. the number of times a lookup found data in the cache
	 */
	public long getHits()
    {
        return hits;
    }
	
	/**
	 * Returns the absolute number of cache misses, i.e. the number of times a lookup returned null
	 */
	public long getMisses()
    {
        return misses;
    }
	
	/**
	 * Returns the total number of lookups performed by this cache 
	 */
	public long getNumberOfLookUps()
    {
        return lookups;
    }
	
}

