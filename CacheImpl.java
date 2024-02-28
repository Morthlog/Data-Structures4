//? Optimization idea
//? Nodes will know the index for prev and next, instead of having a copy of the nodes
//? Nodes will require info of their own index

public class CacheImpl<K, V> implements Cache<K, V> {

    protected Node<K, V>[] cachedData;
    //* HashMap
    protected LinearProbingHashST<K> dataPointer ;
    int size = 0, sizeMax;
    int first = -1, last = -1;
    long misses = 0, hits=0, lookups=0;
  
    @SuppressWarnings("unchecked")
    CacheImpl()
    {
        sizeMax = 100;
        cachedData = new Node[sizeMax];
    }

    @SuppressWarnings("unchecked")
    CacheImpl(int size)
    {
        this.sizeMax = size;
        cachedData = new Node[sizeMax];
        dataPointer = new LinearProbingHashST<K>(sizeMax);
    }

/**
	 * Look for data associated with key. 
	 * @param key the key to look for
	 * @return The associated data or null if it is not found
	 */
	public V lookUp(K key)
    {
		lookups++;
        int index = 0;
        //! search in HashMap for key and return the node's data
        index = dataPointer.get(key);
        if (index == -1)
        {
        	misses++;
            return null;
        }
        else
        {
        	hits++;
            if (index == last)
            {
                cachedData[first].next = last;
                last = cachedData[last].next;
                
                cachedData[last].prev = -1;
                cachedData[cachedData[first].next].next = -1;
                
                cachedData[cachedData[first].next].prev = first;
                first = cachedData[first].next;
            }
            else if (index != first) // no position update needed
            {
                cachedData[cachedData[index].prev].next = cachedData[index].next;
                cachedData[cachedData[index].next].prev = cachedData[index].prev;

                cachedData[first].next = index;
                cachedData[index].prev = first;
                cachedData[index].next = -1;
                first = index;
            }

            return cachedData[index].getData();
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
        if (sizeMax<=size)
        {
            K lastKey = cachedData[last].getKey();
            cachedData[first].next = last;
            // replace data of last with new data
            cachedData[last].data = value;
            cachedData[last].key = key;
            cachedData[last].prev = first;

            // remove connection between last and second from last
            last = cachedData[last].next;
            cachedData[cachedData[last].prev].next = -1;
            cachedData[last].prev = -1;

            // finish movement of last to the start
            cachedData[cachedData[first].next].prev = first;
            first = cachedData[first].next;
            
            //! delete lastKey from hashMap
            dataPointer.delete(lastKey);   

            //! add to hashMap which points to index of lastKey, which is now first
            dataPointer.put(key, first);
        }
        else
        {
            cachedData[size] = new Node<K, V>(key, value);
            if (size == 0)
            {
                first = size;
                last = first;
            }
            else
            {
                cachedData[size].prev = first;
                cachedData[first].next = size;
                first = size;
            }

            //! add to hashMap which points to first
            dataPointer.put(key, first);
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
