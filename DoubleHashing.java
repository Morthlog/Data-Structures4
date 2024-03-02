public class DoubleHashing<Key>
{
	enum State
	{
		INITIAL, GET, PUT, DELETE
	}

	// must be a power of 2
	private static final int INIT_CAPACITY = 128;

	private int n = 0; // number of key-value pairs in the symbol table
	private int m; // size of linear probing table
	private int[] keys; // the keys
	private int[] indexes; // the values
	int primeSize;
	State prevState = State.INITIAL;

	int savedHash, savedK;
	int idxToDelete;
	/**
	 * Initializes an empty symbol table.
	 */
	public DoubleHashing()
	{
		this(INIT_CAPACITY);
	}

	/**
	 * Initializes an empty symbol table with the specified initial capacity.
	 *
	 * @param capacity the initial capacity
	 */
	public DoubleHashing(int capacity)
	{
//		 m =2* nearestPowerOfTwo(capacity) ;
		m = closestPrime((int) (capacity / 0.7));

		keys = new int[m];
		indexes = new int[m];
		primeSize = closestPrime(m);
	}

	int nearestPowerOfTwo(int capacity)
	{
		int nearestPower;
		for (nearestPower = 2; nearestPower < capacity; nearestPower *= 2)
		{
		}
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
	 * @return {@code true} if this symbol table is empty; {@code false} otherwise
	 */
	public boolean isEmpty()
	{
		return size() == 0;
	}

	/* Function to get prime number less than table size for myhash2 function */
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

	private int hashToPos(int hashedKey)
	{	
       return hashedKey % m;
//		hashedKey ^= (hashedKey >>> 20) ^ (hashedKey >>> 12) ^ (hashedKey >>> 7) ^ (hashedKey >>> 4);
//	       return hashedKey & (m-1);

	}
	
	private int hashTwo(int hashedKey)
	{
			
		int k = primeSize - (hashedKey % primeSize);
		return k;
//		return (hashedKey % 97)+1;
	}

	/**
	 *
	 * @param key the key
	 * @param val the value
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void put(Key key, int val)
	{
		if (key == null)
			throw new IllegalArgumentException("first argument to put() is null");

		int i;
		int k;
		
		int hashedKey;

		if (prevState == State.GET)
		{
			k = savedK;
			hashedKey = savedHash;
		} 
		else
		{
			hashedKey = key.hashCode();
			k = hashTwo(hashedKey);		
			
//			k=primeSize - (hashedKey % primeSize);
			
		}
		prevState = State.PUT;
		int initialPos = hashToPos(hashedKey);
//		int initialPos= hashedKey % m;
		for (i = initialPos; keys[i] != 0; i = (i + k) % m)
		{
			// if key is in array, replace value
			if (keys[i] == hashedKey)
			{
				indexes[i] = val;
				return;
			}
			// found sentinel, same as empty space
			if (indexes[i] == -2)
				break;
		}

		// key is not in array, put key and index in their respective arrays
		keys[i] = hashedKey;
		indexes[i] = val;
		++n;
	}

	/**
	 * Returns the value associated with the specified key.
	 * 
	 * @param key the key
	 * @return the value associated with {@code key}; {@code null} if no such value
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public int get(Key key)
	{
		// System.out.println("N=" + n);
		if (key == null)
			throw new IllegalArgumentException("argument to get() is null");

		prevState = State.GET;
		int i;
		int hashedKey = key.hashCode();
		int k = hashTwo(hashedKey);	
		int initialPos = hashToPos(hashedKey);
//		int initialPos= hashedKey % m;
//		int k=primeSize - (hashedKey % primeSize);
		
		savedHash = hashedKey;
		savedK = k;

		int loopCount = -1;

		for (i = initialPos; keys[i] != 0; i = (i + k) % m)
		{
			if (i == initialPos)
				++loopCount;

			if (keys[i] == hashedKey && indexes[i] != -2)
			{
				idxToDelete=i;
				return indexes[i];
			}
				

			if (i == initialPos && loopCount == 1)
			{
				break;
			}
		}

		return -1;
	}

	/**
	 * Removes the specified key and its associated value from this symbol table (if
	 * the key is in this symbol table).
	 *
	 * @param key the key
	 * @throws Exception
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
//	public void delete(Key key)
//	{
//		if (key == null)
//			throw new IllegalArgumentException("argument to delete() is null");
//
//		int hashedKey = key.hashCode();
//		
//		int k = hashTwo(hashedKey);	
//		int initialPos =hashToPos(hashedKey);
////		int initialPos= hashedKey % m;
////		int k=primeSize - (hashedKey % primeSize);
//		prevState = State.DELETE;
//		
//		// find position i of key	
//		for (int i = initialPos; keys[i] != 0; i = (i + k) % m)
//		{
//			if (hashedKey == keys[i] && indexes[i] != -2)
//			{
//				// delete key and associated value
//				indexes[i] = -2;
//				--n;
//				break;
//			}
//		}
//	}
	
	public void delete()
	{
		if (indexes[idxToDelete] != -2)
		{
			indexes[idxToDelete]=-2;
		}
		prevState = State.DELETE;
	}
}

