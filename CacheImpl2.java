//? Optimization idea
//? Nodes will know the index for prev and next, instead of having a copy of the nodes
//? Nodes will require info of their own index

public class CacheImpl2<K, V> implements Cache<K, V> {

    protected Node<K, V>[] cachedData;
    //* HashMap
    protected LinearProbingHashST2<K,V> dataPointer ;
    int size = 0, sizeMax;
    Node<K, V> first = null, last = null;
    long misses = 0, hits=0, lookups=0;
  
    @SuppressWarnings("unchecked")
    CacheImpl2()
    {
        sizeMax = 100;
        cachedData = new Node[sizeMax];
    }

    @SuppressWarnings("unchecked")
    CacheImpl2(int size)
    {
        this.sizeMax = size;
        cachedData = new Node[sizeMax];
        dataPointer = new LinearProbingHashST2<K,V>(sizeMax);
    }

/**
	 * Look for data associated with key. 
	 * @param key the key to look for
	 * @return The associated data or null if it is not found
	 */
	public V lookUp(K key)
    {
		lookups++;
        Node<K,V> index;
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
            if (index == last)
            {
                first.next = last;
                last = last.next;
                
                last.prev = null;
                first.next.next = null;
                
                first.next.prev = first;
                first = first.next;
            }
            else if (index != first) // no position update needed
            {
                index.prev.next = index.next;
                index.next.prev = index.prev;

                first.next = index;
                index.prev = first;
                index.next = null;
                first = index;
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
            K lastKey = last.getKey();
            Node<K,V> ind = dataPointer.get(lastKey);
            first.next = last;
            // replace data of last with new data
            last.data = value;
            last.key = key;
            last.prev = first;

            // remove connection between last and second from last
            last = last.next;
            last.prev.next = null;
            last.prev = null;

            // finish movement of last to the start
            first.next.prev = first;
            first = first.next;
            
            //! delete lastKey from hashMap
            dataPointer.delete(lastKey);   

            //! add to hashMap which points to index of lastKey, which is now first
            dataPointer.put(key, ind);
        }
        else
        {
            cachedData[size] = new Node<K, V>(key, value);
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
            dataPointer.put(key, cachedData[size]);
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