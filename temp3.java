// //double hashing



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
//    int primeSize;

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
//        //m =2* nearestPowerOfTwo(capacity) ;
//         m = closestPrime((int) (capacity/0.7));
//        //m=211;

//        n = 0;
//        keys = (Key[]) new Object[m];
//        indexes = new int[m];
       
//        primeSize = closestPrime(m);
//         n=0;
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

//    /* Function to get prime number less than table size for myhash2 function */  
//    public int closestPrime(int upper_bound)  
//    {  
//        for (int i = upper_bound - 1; i >= 1; i--)  
//            {  
//                int fact = 0;  
//                for (int j = 2; j <= (int) Math.sqrt(i); j++)  
//                    if (i % j == 0)  
//                        fact++;  
//                if (fact == 0)  
//                    return i;  
//            }  
//       /* Return a prime number */  
//        return 3;  
//    }
//    // hash function for keys - returns value between 0 and m-1 (assumes m is a power of 2)
//    // (from Java 7 implementation, protects against poor quality hashCode() implementations)
//    private int hash(Key key) 
//    {
//     //! ===================================================================================================================
//     //! test and pick best hash
//        int h = key.hashCode();
//     //   h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
//     //   return h & (m-1);
//     int k =h%m;
//         return k;
	   
// //	   return (key.hashCode() & 0x7fffffff)%(m-1);
//    }

//    private int hashTwo(Key key) 
//    {
//     //! test and pick best hash
// 	   int hash2=(key.hashCode()); // & 0x7fffffff);
// 	   int k = primeSize-(hash2 % primeSize) ;
//        return k;
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

      
//        int i;
//        int k= hashTwo(key);
//        int initialPos = hash(key);
//        //! ===================================================================================================================
//        //! ignore first initial is kind of sloopy, kindly do better
//        int temp =0;
//        for (i = hash(key); keys[i] != null; i = (i + k) % m) 
//        {
//             if (i== initialPos) ++temp;
// //    	   System.out.println("put "+" key= "+key+" i="+ i +" k="+k);
//     	   // if key is in array, replace value
//            if (keys[i].equals(key)) 
//            {
//                indexes[i] = val;
//                return;
//            }
//            //found sentinel, same as empty space
//            if( indexes[i]==-2)
//         	   break;
//            if(initialPos==i & temp == 2)
//            {
//         	   //looping
//         	   System.out.println("looping "+ "N="+n+" m= "+m);
//     //    	   resize(2*m);
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

//        int i;
//        int k= hashTwo(key);
//         //! ===================================================================================================================
//        //! ignore first initial is kind of sloopy, kindly do better
//        int initial = hash(key);
//        int temp = 0;
//        for ( i = hash(key); keys[i] != null ; i = (i + k) % m)
//        {   
//             if (i== initial) ++temp; 
//     	//   System.out.println("get "+" key= "+key+" i="+ i +" k="+k);     
//     	   if (keys[i].equals(key) &&  indexes[i] != -2)
//                return indexes[i];
//     	   if(i==initial & temp==2)
//     	   {
//     		   //System.out.println("looping "+ initial);
//     		   break;
//     	   } 
//        }
          
//        return -1;
//    }

//    private void resize(int capacity) 
//    {
//        LinearProbingHashST<Key> temp = new LinearProbingHashST<Key>(capacity);
//        for (int i = 0; i < m; i++) 
//        {
//            if (keys[i] != null) 
//            {
//                temp.put(keys[i], indexes[i]);
//            }
//        }
//        keys = temp.keys;
//        indexes = temp.indexes;
//        m    = temp.m;
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
//        int k= hashTwo(key);

//        for (int i = hash(key); keys[i] != null; i = (i +k) % m) 
//        { 
//     	   //System.out.println("delete "+" key= "+key+" i="+ i +" k="+k);     
//            if (key.equals(keys[i]) && indexes[i] != -2 )
//            {
//         	   // delete key and associated value
// //               keys[i] = null;
//                indexes[i] = -2;
// //               
// //               // rehash all keys in same cluster
// //               i = (i + 1) % m;
// //               while (keys[i] != null) 
// //               {
// //                   // delete keys[i] and vals[i] and reinsert
// //                   Key keyToRehash = keys[i];
// //                   int valToRehash = indexes[i];
// //                   keys[i] = null;
// //                   indexes[i] = -1;
// //                   --n;
// //                   put(keyToRehash, valToRehash);
// //                   i = (i + 1) % m;
// //               }

//                --n;               
//                break;
//            	}
//        }       
//    }
// }

// //=========================================================================================================
// //=========================================================================================================
// //=========================================================================================================
// //=========================================================================================================
// //=========================================================================================================
// //=========================================================================================================
// //=========================================================================================================
// //=========================================================================================================



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

