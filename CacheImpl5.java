//? Optimization idea
//? Nodes will know the index for prev and next, instead of having a copy of the nodes
//? Nodes will require info of their own index

public class CacheImpl5<K, V> implements Cache<K, V> {

    protected Node2<K, V>[] cachedData;
    //* HashMap
    protected LinearProbingHashST3<K,V> dataPointer ;
    int size = 0, sizeMax;
    int first = -1, last = -1;
    long misses = 0, hits=0, lookups=0;
  
    @SuppressWarnings("unchecked")
    CacheImpl5()
    {
        sizeMax = 100;
        cachedData = new Node2[sizeMax];
    }

    @SuppressWarnings("unchecked")
    CacheImpl5(int size)
    {
        this.sizeMax = size;
        cachedData = new Node2[sizeMax];
        dataPointer = new LinearProbingHashST3<K,V>(sizeMax);
    }

/**
	 * Look for data associated with key. 
	 * @param key the key to look for
	 * @return The associated data or null if it is not found
	 */
	public V lookUp(K key)
    {
		lookups++;
        Node2<K,V> index;
        //! search in HashMap for key and return the node's data
        index = dataPointer.get(key);
        if (index == null)
        {
        	misses++;
            return null;
        }
        else
        {
        	hits++;
            if (index == cachedData[last])
            {
                cachedData[first].next = last;
                last = cachedData[last].next;
                
                cachedData[last].prev = -1;
                cachedData[cachedData[first].next].next = -1;
                
                cachedData[cachedData[first].next].prev = first;
                first = cachedData[first].next;
            }
            else if (index != cachedData[first]) // no position update needed
            {
                cachedData[index.prev].next = index.next;
                cachedData[index.next].prev = index.prev;

                cachedData[first].next = index.pos;
                index.prev = first;
                index.next = -1;
                first = index.pos;
            }

            return index.getData();
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
            int lastpos = cachedData[last].pos;
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
            dataPointer.put(key, cachedData[first]);
            cachedData[lastpos].pos = first;
        }
        else
        {
            cachedData[size] = new Node2<K, V>(key, value);
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
            cachedData[size].pos = first;
            //! add to hashMap which points to first
            dataPointer.put(key, cachedData[first]);
            ++size;
        }
    }
	
	/**
	 * Returns the hit ratio, i.e. the number of times a lookup was successful divided by the number of lookup 
	 * @return the cache hit ratio
	 */
	public double getHitRatio()
    {
		if(lookups>0)
        {
			return (double) hits/lookups;}
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

