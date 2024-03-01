// // with node saved 
// 57069     50558
// 55783     52790 
// 49244     51322
// 56900     56644
// 50286     55861
// 57000     56393
// 55694     50225
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
// 		if(lookups>0)
//         {
// 			return (double) hits/lookups;}
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

// //========================================================================================================
// //========================================================================================================




// /**
//  * ListNode represents a list node
//  * Each node contains a generic type T as data and a reference to the next node in the list.
//  */
// public class Node<KEY, DATA> 
// {
// 	protected KEY key ;
//     protected DATA data;
//     protected Node<KEY, DATA> next = null;
//     protected Node<KEY, DATA> prev = null;

//     /**
//      * Constructor. Sets data
//      *
//      * @param data the data stored
//      * @return
//      */
//     Node(KEY key, DATA data) 
//     {
//     	this.key = key;
//         this.data = data;
//     }

//     /**
//      * Returns this node's data
//      *
//      * @return the reference to node's data
//      */
//     public DATA getData() 
//     {
//         // return data stored in this node
//         return data;
//     }
    
//     public KEY getKey() 
//     {
//         // return key stored in this node
//         return key;
//     }
//     /**
//      * Get reference to next node
//      *
//      * @return the next node
//      */
//     public Node<KEY, DATA> getNext() 
//     {
//         // get next node
//         return next;
//     }

//     public Node<KEY, DATA> getPrev() 
//     {
//         // get next node
//         return prev;
//     }

//     /**
//      * Sets reference to next node
//      *
//      * @param next reference
//      */
//     public void setNext(Node<KEY, DATA> next) 
//     {
//         this.next = next;
//     }
    
    
//     /**
//      * Sets reference to previous node
//      *
//      * @param prev reference
//      */
//     public void setPrev(Node<KEY, DATA> prev) 
//     {
//         this.prev = prev;
//     }
// }



 
// //========================================================================================================
// //========================================================================================================
// //========================================================================================================
// //========================================================================================================
// //========================================================================================================
// //========================================================================================================
// //========================================================================================================
// //========================================================================================================
// // with Index saved

// //? Optimization idea
// //? Nodes will know the index for prev and next, instead of having a copy of the nodes
// //? Nodes will require info of their own index

// public class CacheImpl<K, V> implements Cache<K, V> {

//     protected Node<K, V>[] cachedData;
//     //* HashMap
//     protected LinearProbingHashST<K> dataPointer ;
//     int size = 0, sizeMax;
//     int first = -1, last = -1;
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
//             if (index == last)
//             {
//                 cachedData[first].next = last;
//                 last = cachedData[last].next;
                
//                 cachedData[last].prev = -1;
//                 cachedData[cachedData[first].next].next = -1;
                
//                 cachedData[cachedData[first].next].prev = first;
//                 first = cachedData[first].next;
//             }
//             else if (index != first) // no position update needed
//             {
//                 cachedData[cachedData[index].prev].next = cachedData[index].next;
//                 cachedData[cachedData[index].next].prev = cachedData[index].prev;

//                 cachedData[first].next = index;
//                 cachedData[index].prev = first;
//                 cachedData[index].next = -1;
//                 first = index;
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
//             K lastKey = cachedData[last].getKey();
//             cachedData[first].next = last;
//             // replace data of last with new data
//             cachedData[last].data = value;
//             cachedData[last].key = key;
//             cachedData[last].prev = first;

//             // remove connection between last and second from last
//             last = cachedData[last].next;
//             cachedData[cachedData[last].prev].next = -1;
//             cachedData[last].prev = -1;

//             // finish movement of last to the start
//             cachedData[cachedData[first].next].prev = first;
//             first = cachedData[first].next;
            
//             //! delete lastKey from hashMap
//             dataPointer.delete(lastKey);   

//             //! add to hashMap which points to index of lastKey, which is now first
//             dataPointer.put(key, first);
//         }
//         else
//         {
//             cachedData[size] = new Node<K, V>(key, value);
//             if (size == 0)
//             {
//                 first = size;
//                 last = first;
//             }
//             else
//             {
//                 cachedData[size].prev = first;
//                 cachedData[first].next = size;
//                 first = size;
//             }

//             //! add to hashMap which points to first
//             dataPointer.put(key, first);
//             ++size;
//         }
//     }
	
// 	/**
// 	 * Returns the hit ratio, i.e. the number of times a lookup was successful divided by the number of lookup 
// 	 * @return the cache hit ratio
// 	 */
// 	public double getHitRatio()
//     {
// 		if(lookups>0)
//         {
// 			return (double) hits/lookups;}
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

// //========================================================================================================
// //========================================================================================================

// /**
//  * ListNode represents a list node
//  * Each node contains a generic type T as data and a reference to the next node in the list.
//  */
// public class Node<KEY, DATA> 
// {
// 	protected KEY key ;
//     protected DATA data;
//     protected int next = -1;
//     protected int prev = -1;

//     /**
//      * Constructor. Sets data
//      *
//      * @param data the data stored
//      * @return
//      */
//     Node(KEY key, DATA data) 
//     {
//     	this.key = key;
//         this.data = data;
//     }

//     /**
//      * Returns this node's data
//      *
//      * @return the reference to node's data
//      */
//     public DATA getData() 
//     {
//         // return data stored in this node
//         return data;
//     }
    
//     public KEY getKey() 
//     {
//         // return key stored in this node
//         return key;
//     }
//     /**
//      * Get reference to next node
//      *
//      * @return the next node
//      */
//     public int getNext() 
//     {
//         // get next node
//         return next;
//     }

//     public int getPrev() 
//     {
//         // get next node
//         return prev;
//     }

//     /**
//      * Sets reference to next node
//      *
//      * @param next reference
//      */
//     public void setNext(int next) 
//     {
//         this.next = next;
//     }
    
    
//     /**
//      * Sets reference to previous node
//      *
//      * @param prev reference
//      */
//     public void setPrev(int prev) 
//     {
//         this.prev = prev;
//     }
// }



