


/*  @author Robert Sedgewick
*  @author Kevin Wayne
*/
public class LinearProbingHashST<Key, Value> 
{

   // must be a power of 2
   private static final int INIT_CAPACITY = 4;

   private int n;           // number of key-value pairs in the symbol table
   private int m;           // size of linear probing table
   private Key[] keys;      // the keys
   private Value[] vals;    // the values


   /**
    * Initializes an empty symbol table.
    */
   public LinearProbingHashST() 
   {
       this(INIT_CAPACITY);
   }

   /**
    * Initializes an empty symbol table with the specified initial capacity.
    *
    * @param capacity the initial capacity
    */
   public LinearProbingHashST(int capacity)
   {
       m = nearestPowerOfTwo(capacity);
       n = 0;
       keys = (Key[])   new Object[m];
       vals = (Value[]) new Object[m];
   }

   int nearestPowerOfTwo(int capacity)
   {
	   int nearestPower;
	   for(nearestPower = 2; nearestPower < capacity; nearestPower *= 2) {} 
	   return nearestPower;
   }
   /**
    * Returns the number of key-value pairs in this symbol table.
    *
    * @return the number of key-value pairs in this symbol table
    */
   public int size() 
   {
       return n;
   }

   /**
    * Returns true if this symbol table is empty.
    *
    * @return {@code true} if this symbol table is empty;
    *         {@code false} otherwise
    */
   public boolean isEmpty() 
   {
       return size() == 0;
   }


   // hash function for keys - returns value between 0 and m-1
   private int hashTextbook(Key key) 
   {
       return (key.hashCode() & 0x7fffffff) % m;
   }

   // hash function for keys - returns value between 0 and m-1 (assumes m is a power of 2)
   // (from Java 7 implementation, protects against poor quality hashCode() implementations)
   private int hash(Key key) 
   {
       int h = key.hashCode();
       h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
       return h & (m-1);
   }

   // resizes the hash table to the given capacity by re-hashing all of the keys
   private void resize(int capacity) 
   {
       LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<Key, Value>(capacity);
       for (int i = 0; i < m; i++) 
       {
           if (keys[i] != null) 
           {
               temp.put(keys[i], vals[i]);
           }
       }
       keys = temp.keys;
       vals = temp.vals;
       m    = temp.m;
   }

   /**
    * Inserts the specified key-value pair into the symbol table, overwriting the old
    * value with the new value if the symbol table already contains the specified key.
    * Deletes the specified key (and its associated value) from this symbol table
    * if the specified value is {@code null}.
    *
    * @param  key the key
    * @param  val the value
    * @throws IllegalArgumentException if {@code key} is {@code null}
    */
   public void put(Key key, Value val) 
   {
       if (key == null) 
    	   throw new IllegalArgumentException("first argument to put() is null");

       if (val == null) 
       {
           delete(key);
           return;
       }

       // double table size if 50% full
       if (n >= m/2) resize(2*m);

       int i;
       for (i = hash(key); keys[i] != null; i = (i + 1) % m) 
       {
           if (keys[i].equals(key)) 
           {
               vals[i] = val;
               return;
           }
       }
       keys[i] = key;
       vals[i] = val;
       ++n;
   }

   /**
    * Returns the value associated with the specified key.
    * @param key the key
    * @return the value associated with {@code key};
    *         {@code null} if no such value
    * @throws IllegalArgumentException if {@code key} is {@code null}
    */
   public Value get(Key key) 
   {
       if (key == null) 
    	   throw new IllegalArgumentException("argument to get() is null");
       for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
       {
    	   if (keys[i].equals(key))
               return vals[i];
       }
          
       return null;
   }

   /**
    * Removes the specified key and its associated value from this symbol table
    * (if the key is in this symbol table).
    *
    * @param  key the key
    * @throws IllegalArgumentException if {@code key} is {@code null}
    */
   public void delete(Key key) 
   {
       if (key == null) 
    	   throw new IllegalArgumentException("argument to delete() is null");

       // find position i of key      
       for (int i = hash(key); keys[i] != null; i = (i + 1) % m) 
       {
           if (key.equals(keys[i]))
           {
        	   // delete key and associated value
               keys[i] = null;
               vals[i] = null;
               
               // rehash all keys in same cluster
               i = (i + 1) % m;
               while (keys[i] != null) 
               {
                   // delete keys[i] and vals[i] and reinsert
                   Key   keyToRehash = keys[i];
                   Value valToRehash = vals[i];
                   keys[i] = null;
                   vals[i] = null;
                   --n;
                   put(keyToRehash, valToRehash);
                   i = (i + 1) % m;
               }

               --n;
               // halves size of array if it's 12.5% full or less
               if (n > 0 && n <= m/8) resize(m/2);
               
               break;
           	}
       }       
   }
}

