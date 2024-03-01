//Parts from code by
/*  @author Robert Sedgewick
*  @author Kevin Wayne
*/
public class LinearProbingHashST2<Key,V> 
{
   // must be a power of 2
   private static final int INIT_CAPACITY = 128;

   private int n;           // number of key-value pairs in the symbol table
   private int m;           // size of linear probing table
   private Key[] keys;      // the keys
   private Node<Key,V>[] indexes;    // the values


   /**
    * Initializes an empty symbol table.
    */
   public LinearProbingHashST2() 
   {
       this(INIT_CAPACITY);
   }

   /**
    * Initializes an empty symbol table with the specified initial capacity.
    *
    * @param capacity the initial capacity
    */
   public LinearProbingHashST2(int capacity)
   {
       m = closestPrime((int) (capacity/0.7));
       n = 0;
       keys = (Key[]) new Object[m];
       indexes = new Node[m];
   }

   public int closestPrime(int upper_bound)  
   {  
       for (int i = upper_bound - 1; i >= 1; i--)  
           {  
               int fact = 0;  
               for (int j = 2; j <= (int) Math.sqrt(i); j++)  
                   if (i % j == 0)  
                       fact++;  
               if (fact == 0)  
                   return i;  
           }  
      /* Return a prime number */  
       return 3;  
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

   // hash function for keys - returns value between 0 and m-1 (assumes m is a power of 2)
   // (from Java 7 implementation, protects against poor quality hashCode() implementations)
   private int hash(Key key) 
   {
       int h = key.hashCode();
       h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
       return h & (m-1);
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
   public void put(Key key, Node<Key,V> val) 
   {
       if (key == null) 
    	   throw new IllegalArgumentException("first argument to put() is null");

       // if key is in array, replace value
       int i;
       for (i = hash(key); keys[i] != null; i = (i + 1) % m) 
       {
           if (keys[i].equals(key)) 
           {
               indexes[i] = val;
               return;
           }
       }
       
       //key is not in array, put key and index in their respective arrays
       keys[i] = key;
       indexes[i] = val;
       ++n;
   }

   /**
    * Returns the value associated with the specified key.
    * @param key the key
    * @return the value associated with {@code key};
    *         {@code null} if no such value
    * @throws IllegalArgumentException if {@code key} is {@code null}
    */
   public Node<Key,V> get(Key key) 
   {
       if (key == null) 
    	   throw new IllegalArgumentException("argument to get() is null");
       for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
       {
    	   if (keys[i].equals(key))
               return indexes[i];
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
               indexes[i] = null;
               
               // rehash all keys in same cluster
               i = (i + 1) % m;
               while (keys[i] != null) 
               {
                   // delete keys[i] and vals[i] and reinsert
                   Key keyToRehash = keys[i];
                   Node<Key,V> valToRehash = indexes[i];
                   keys[i] = null;
                   indexes[i] = null;
                   --n;
                   put(keyToRehash, valToRehash);
                   i = (i + 1) % m;
               }

               --n;               
               break;
           	}
       }       
   }
}