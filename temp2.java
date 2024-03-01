// //put get Nodes


// //Parts from code by
// /*  @author Robert Sedgewick
// *  @author Kevin Wayne
// */
// public class LinearProbingHashST<Key,V> 
// {
//    // must be a power of 2
//    private static final int INIT_CAPACITY = 128;

//    private int n;           // number of key-value pairs in the symbol table
//    private int m;           // size of linear probing table
//    private Key[] keys;      // the keys
//    private Node<Key,V>[] indexes;    // the values


//    /**
//     * Initializes an empty symbol table.
//     */
//    public LinearProbingHashST() 
//    {
//        this(INIT_CAPACITY);
//    }

//    /**
//     * Initializes an empty symbol table with the specified initial capacity.
//     *
//     * @param capacity the initial capacity
//     */
//    public LinearProbingHashST(int capacity)
//    {
//        m =2* nearestPowerOfTwo(capacity);
//        n = 0;
//        keys = (Key[]) new Object[m];
//        indexes = new Node[m];
//    }

//    int nearestPowerOfTwo(int capacity)
//    {
// 	   int nearestPower;
// 	   for(nearestPower = 2; nearestPower < capacity; nearestPower *= 2) {} 
// 	   return nearestPower;
//    }
//    /**
//     * Returns the number of key-value pairs in this symbol table.
//     *
//     * @return the number of key-value pairs in this symbol table
//     */
//    public int size() 
//    {
//        return n;
//    }

//    /**
//     * Returns true if this symbol table is empty.
//     *
//     * @return {@code true} if this symbol table is empty;
//     *         {@code false} otherwise
//     */
//    public boolean isEmpty() 
//    {
//        return size() == 0;
//    }

//    // hash function for keys - returns value between 0 and m-1 (assumes m is a power of 2)
//    // (from Java 7 implementation, protects against poor quality hashCode() implementations)
//    private int hash(Key key) 
//    {
//        int h = key.hashCode();
//        h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
//        return h & (m-1);
//    }


//    /**
//     * Inserts the specified key-value pair into the symbol table, overwriting the old
//     * value with the new value if the symbol table already contains the specified key.
//     * Deletes the specified key (and its associated value) from this symbol table
//     * if the specified value is {@code null}.
//     *
//     * @param  key the key
//     * @param  val the value
//     * @throws IllegalArgumentException if {@code key} is {@code null}
//     */
//    public void put(Key key, Node<Key,V> val) 
//    {
//        if (key == null) 
//     	   throw new IllegalArgumentException("first argument to put() is null");

//        // if key is in array, replace value
//        int i;
//        for (i = hash(key); keys[i] != null; i = (i + 1) % m) 
//        {
//            if (keys[i].equals(key)) 
//            {
//                indexes[i] = val;
//                return;
//            }
//        }
       
//        //key is not in array, put key and index in their respective arrays
//        keys[i] = key;
//        indexes[i] = val;
//        ++n;
//    }

//    /**
//     * Returns the value associated with the specified key.
//     * @param key the key
//     * @return the value associated with {@code key};
//     *         {@code null} if no such value
//     * @throws IllegalArgumentException if {@code key} is {@code null}
//     */
//    public Node<Key,V> get(Key key) 
//    {
//        if (key == null) 
//     	   throw new IllegalArgumentException("argument to get() is null");
//        for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
//        {
//     	   if (keys[i].equals(key))
//                return indexes[i];
//        }
          
//        return null;
//    }

//    /**
//     * Removes the specified key and its associated value from this symbol table
//     * (if the key is in this symbol table).
//     *
//     * @param  key the key
//     * @throws IllegalArgumentException if {@code key} is {@code null}
//     */
//    public void delete(Key key) 
//    {
//        if (key == null) 
//     	   throw new IllegalArgumentException("argument to delete() is null");

//        // find position i of key      
//        for (int i = hash(key); keys[i] != null; i = (i + 1) % m) 
//        {
//            if (key.equals(keys[i]))
//            {
//         	   // delete key and associated value
//                keys[i] = null;
//                indexes[i] = null;
               
//                // rehash all keys in same cluster
//                i = (i + 1) % m;
//                while (keys[i] != null) 
//                {
//                    // delete keys[i] and vals[i] and reinsert
//                    Key keyToRehash = keys[i];
//                    Node<Key,V> valToRehash = indexes[i];
//                    keys[i] = null;
//                    indexes[i] = null;
//                    --n;
//                    put(keyToRehash, valToRehash);
//                    i = (i + 1) % m;
//                }

//                --n;               
//                break;
//            	}
//        }       
//    }
// }

// //==================================================================================================
// //==================================================================================================

// //? Optimization idea
// //? Nodes will know the index for prev and next, instead of having a copy of the nodes
// //? Nodes will require info of their own index

// public class CacheImpl<K, V> implements Cache<K, V> {

//     protected Node<K, V>[] cachedData;
//     //* HashMap
//     protected LinearProbingHashST<K,V> dataPointer ;
//     int size = 0, sizeMax;
//     Node<K, V> first = null, last = null;
//     long misses = 0, hits=0, lookups=0;
  
//     @SuppressWarnings("unchecked")
//     CacheImpl()
//     {
//         sizeMax = 100;
//         cachedData = new Node[sizeMax];
//     }

//     @SuppressWarnings("unchecked")
//     CacheImpl(int size)
//     {
//         this.sizeMax = size;
//         cachedData = new Node[sizeMax];
//         dataPointer = new LinearProbingHashST<K,V>(sizeMax);
//     }

// /**
// 	 * Look for data associated with key. 
// 	 * @param key the key to look for
// 	 * @return The associated data or null if it is not found
// 	 */
// 	public V lookUp(K key)
//     {
// 		lookups++;
//         Node<K,V> index;
//         //! search in HashMap for key and return the node's data
//         index = dataPointer.get(key);
//         if (index == null)
//         {
//         	misses++;
//             return null;
//         }
//         else
//         {
//         	hits++;
//             if (index == last)
//             {
//                 first.next = last;
//                 last = last.next;
                
//                 last.prev = null;
//                 first.next.next = null;
                
//                 first.next.prev = first;
//                 first = first.next;
//             }
//             else if (index != first) // no position update needed
//             {
//                 index.prev.next = index.next;
//                 index.next.prev = index.prev;

//                 first.next = index;
//                 index.prev = first;
//                 index.next = null;
//                 first = index;
//             }

//             return index.getData();
//         }
//     }
	
// 	/**
// 	 * Stores data with associated with the given key. If required, it evicts a
// 	 * data record to make room for the new one
// 	 * @param key the key to associate with the data
// 	 * @param value the actual data
// 	 */
// 	public void store(K key, V value)
//     {
//         if (sizeMax<=size)
//         {
//             K lastKey = last.getKey();
//             Node<K,V> ind = dataPointer.get(lastKey);
//             first.next = last;
//             // replace data of last with new data
//             last.data = value;
//             last.key = key;
//             last.prev = first;

//             // remove connection between last and second from last
//             last = last.next;
//             last.prev.next = null;
//             last.prev = null;

//             // finish movement of last to the start
//             first.next.prev = first;
//             first = first.next;
            
//             //! delete lastKey from hashMap
//             dataPointer.delete(lastKey);   

//             //! add to hashMap which points to index of lastKey, which is now first
//             dataPointer.put(key, ind);
//         }
//         else
//         {
//             cachedData[size] = new Node<K, V>(key, value);
//             if (size == 0)
//             {
//                 first = cachedData[size];
//                 last = first;
//             }
//             else
//             {
//                 cachedData[size].prev = first;
//                 first.next = cachedData[size];
//                 first = cachedData[size];
//             }

//             //! add to hashMap which points to first
//             dataPointer.put(key, cachedData[size]);
//             ++size;
//         }
//     }
	
// 	/**
// 	 * Returns the hit ratio, i.e. the number of times a lookup was successful divided by the number of lookup 
// 	 * @return the cache hit ratio
// 	 */
// 	public double getHitRatio()
//     {
// 		if(lookups>0L)		
// 			return (double) hits/lookups;	
			
// 		else	
// 			return 0;		
//     }
	
// 	/**
// 	 * Returns the absolute number of cache hits, i.e. the number of times a lookup found data in the cache
// 	 */
// 	public long getHits()
//     {
//         return hits;
//     }
	
// 	/**
// 	 * Returns the absolute number of cache misses, i.e. the number of times a lookup returned null
// 	 */
// 	public long getMisses()
//     {
//         return misses;
//     }
	
// 	/**
// 	 * Returns the total number of lookups performed by this cache 
// 	 */
// 	public long getNumberOfLookUps()
//     {
//         return lookups;
//     }
	
// }

// //==================================================================================================
// //==================================================================================================
// //==================================================================================================
// //==================================================================================================
// //==================================================================================================
// //==================================================================================================



// //Parts from code by
// /*  @author Robert Sedgewick
// *  @author Kevin Wayne
// */
// public class LinearProbingHashST<Key> 
// {
//    // must be a power of 2
//    private static final int INIT_CAPACITY = 128;

//    private int n;           // number of key-value pairs in the symbol table
//    private int m;           // size of linear probing table
//    private Key[] keys;      // the keys
//    private int[] indexes;    // the values


//    /**
//     * Initializes an empty symbol table.
//     */
//    public LinearProbingHashST() 
//    {
//        this(INIT_CAPACITY);
//    }

//    /**
//     * Initializes an empty symbol table with the specified initial capacity.
//     *
//     * @param capacity the initial capacity
//     */
//    public LinearProbingHashST(int capacity)
//    {
//        m =2* nearestPowerOfTwo(capacity);
//        n = 0;
//        keys = (Key[]) new Object[m];
//        indexes = new int[m];
//    }

//    int nearestPowerOfTwo(int capacity)
//    {
// 	   int nearestPower;
// 	   for(nearestPower = 2; nearestPower < capacity; nearestPower *= 2) {} 
// 	   return nearestPower;
//    }
//    /**
//     * Returns the number of key-value pairs in this symbol table.
//     *
//     * @return the number of key-value pairs in this symbol table
//     */
//    public int size() 
//    {
//        return n;
//    }

//    /**
//     * Returns true if this symbol table is empty.
//     *
//     * @return {@code true} if this symbol table is empty;
//     *         {@code false} otherwise
//     */
//    public boolean isEmpty() 
//    {
//        return size() == 0;
//    }

//    // hash function for keys - returns value between 0 and m-1 (assumes m is a power of 2)
//    // (from Java 7 implementation, protects against poor quality hashCode() implementations)
//    private int hash(Key key) 
//    {
//        int h = key.hashCode();
//        h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
//        return h & (m-1);
//    }


//    /**
//     * Inserts the specified key-value pair into the symbol table, overwriting the old
//     * value with the new value if the symbol table already contains the specified key.
//     * Deletes the specified key (and its associated value) from this symbol table
//     * if the specified value is {@code null}.
//     *
//     * @param  key the key
//     * @param  val the value
//     * @throws IllegalArgumentException if {@code key} is {@code null}
//     */
//    public void put(Key key, int val) 
//    {
//        if (key == null) 
//     	   throw new IllegalArgumentException("first argument to put() is null");

//        // if key is in array, replace value
//        int i;
//        for (i = hash(key); keys[i] != null; i = (i + 1) % m) 
//        {
//            if (keys[i].equals(key)) 
//            {
//                indexes[i] = val;
//                return;
//            }
//        }
       
//        //key is not in array, put key and index in their respective arrays
//        keys[i] = key;
//        indexes[i] = val;
//        ++n;
//    }

//    /**
//     * Returns the value associated with the specified key.
//     * @param key the key
//     * @return the value associated with {@code key};
//     *         {@code null} if no such value
//     * @throws IllegalArgumentException if {@code key} is {@code null}
//     */
//    public int get(Key key) 
//    {
//        if (key == null) 
//     	   throw new IllegalArgumentException("argument to get() is null");
//        for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
//        {
//     	   if (keys[i].equals(key))
//                return indexes[i];
//        }
          
//        return -1;
//    }

//    /**
//     * Removes the specified key and its associated value from this symbol table
//     * (if the key is in this symbol table).
//     *
//     * @param  key the key
//     * @throws IllegalArgumentException if {@code key} is {@code null}
//     */
//    public void delete(Key key) 
//    {
//        if (key == null) 
//     	   throw new IllegalArgumentException("argument to delete() is null");

//        // find position i of key      
//        for (int i = hash(key); keys[i] != null; i = (i + 1) % m) 
//        {
//            if (key.equals(keys[i]))
//            {
//         	   // delete key and associated value
//                keys[i] = null;
//                indexes[i] = -1;
               
//                // rehash all keys in same cluster
//                i = (i + 1) % m;
//                while (keys[i] != null) 
//                {
//                    // delete keys[i] and vals[i] and reinsert
//                    Key keyToRehash = keys[i];
//                    int valToRehash = indexes[i];
//                    keys[i] = null;
//                    indexes[i] = -1;
//                    --n;
//                    put(keyToRehash, valToRehash);
//                    i = (i + 1) % m;
//                }

//                --n;               
//                break;
//            	}
//        }       
//    }
// }

// //==================================================================================================
// //==================================================================================================
// //? Optimization idea
// //? Nodes will know the index for prev and next, instead of having a copy of the nodes
// //? Nodes will require info of their own index

// public class CacheImpl<K, V> implements Cache<K, V> {

//     protected Node<K, V>[] cachedData;
//     //* HashMap
//     protected LinearProbingHashST<K> dataPointer ;
//     int size = 0, sizeMax;
//     Node<K, V> first = null, last = null;
//     long misses = 0, hits=0, lookups=0;
  
//     @SuppressWarnings("unchecked")
//     CacheImpl()
//     {
//         sizeMax = 100;
//         cachedData = new Node[sizeMax];
//     }

//     @SuppressWarnings("unchecked")
//     CacheImpl(int size)
//     {
//         this.sizeMax = size;
//         cachedData = new Node[sizeMax];
//         dataPointer = new LinearProbingHashST<K>(sizeMax);
//     }

// /**
// 	 * Look for data associated with key. 
// 	 * @param key the key to look for
// 	 * @return The associated data or null if it is not found
// 	 */
// 	public V lookUp(K key)
//     {
// 		lookups++;
//         int index = 0;
//         //! search in HashMap for key and return the node's data
//         index = dataPointer.get(key);
//         if (index == -1)
//         {
//         	misses++;
//             return null;
//         }
//         else
//         {
//         	hits++;
//             if (cachedData[index] == last)
//             {
//                 first.next = last;
//                 last = last.next;
                
//                 last.prev = null;
//                 first.next.next = null;
                
//                 first.next.prev = first;
//                 first = first.next;
//             }
//             else if (cachedData[index] != first) // no position update needed
//             {
//                 cachedData[index].prev.next = cachedData[index].next;
//                 cachedData[index].next.prev = cachedData[index].prev;

//                 first.next = cachedData[index];
//                 cachedData[index].prev = first;
//                 cachedData[index].next = null;
//                 first = cachedData[index];
//             }

//             return cachedData[index].getData();
//         }
//     }
	
// 	/**
// 	 * Stores data with associated with the given key. If required, it evicts a
// 	 * data record to make room for the new one
// 	 * @param key the key to associate with the data
// 	 * @param value the actual data
// 	 */
// 	public void store(K key, V value)
//     {
//         if (sizeMax<=size)
//         {
//             K lastKey = last.getKey();
//             int ind = dataPointer.get(lastKey);
//             first.next = last;
//             // replace data of last with new data
//             last.data = value;
//             last.key = key;
//             last.prev = first;

//             // remove connection between last and second from last
//             last = last.next;
//             last.prev.next = null;
//             last.prev = null;

//             // finish movement of last to the start
//             first.next.prev = first;
//             first = first.next;
            
//             //! delete lastKey from hashMap
//             dataPointer.delete(lastKey);   

//             //! add to hashMap which points to index of lastKey, which is now first
//             dataPointer.put(key, ind);
//         }
//         else
//         {
//             cachedData[size] = new Node<K, V>(key, value);
//             if (size == 0)
//             {
//                 first = cachedData[size];
//                 last = first;
//             }
//             else
//             {
//                 cachedData[size].prev = first;
//                 first.next = cachedData[size];
//                 first = cachedData[size];
//             }

//             //! add to hashMap which points to first
//             dataPointer.put(key, size);
//             ++size;
//         }
//     }
	
// 	/**
// 	 * Returns the hit ratio, i.e. the number of times a lookup was successful divided by the number of lookup 
// 	 * @return the cache hit ratio
// 	 */
// 	public double getHitRatio()
//     {
// 		if(lookups>0L)		
// 			return (double) hits/lookups;	
			
// 		else	
// 			return 0;		
//     }
	
// 	/**
// 	 * Returns the absolute number of cache hits, i.e. the number of times a lookup found data in the cache
// 	 */
// 	public long getHits()
//     {
//         return hits;
//     }
	
// 	/**
// 	 * Returns the absolute number of cache misses, i.e. the number of times a lookup returned null
// 	 */
// 	public long getMisses()
//     {
//         return misses;
//     }
	
// 	/**
// 	 * Returns the total number of lookups performed by this cache 
// 	 */
// 	public long getNumberOfLookUps()
//     {
//         return lookups;
//     }
	
// }