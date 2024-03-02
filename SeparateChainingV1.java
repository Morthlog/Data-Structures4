
public class SeparateChainingV1<KEY>
{
	enum State
	{
		INITIAL, GET, PUT, DELETE
	}

	private NodeSingle[] heads;
	private int N, M;

	int savedHash = 0;
	int savedHashPos = 0;
	State prevState = State.INITIAL;

	SeparateChainingV1(int maxN)
	{
		N = 0;
		M = maxN;
		heads = new NodeSingle[M];
	}

	void put(KEY key, int index)
	{
		int hashedKey;
		int i;
		if (prevState == State.GET)
		{
			hashedKey = savedHash;
			i = savedHashPos;
		} 
		else
		{
			hashedKey = key.hashCode();
			i = hashToPos(hashedKey);
		}

		prevState = State.PUT;

		heads[i] = new NodeSingle(hashedKey, index, heads[i]);
		N++;
	}

	int get(KEY key)
	{
		savedHash = key.hashCode();
		savedHashPos = hashToPos(savedHash);
		
		prevState = State.GET;

		for (NodeSingle node = heads[savedHashPos]; node != null; node = node.next)
		{
			if (node.key == savedHash)
				return node.index;
		}
		return -1;
	}

	public void delete(KEY key)
	{
		prevState = State.DELETE;
		int hashedKey = key.hashCode();
		int hashToPos=hashToPos(hashedKey);
		
		for (NodeSingle node = heads[hashToPos]; node != null; node = node.next)
		{
			if (node.key == hashedKey)
			{
				heads[hashToPos] = node.next;
				--N;
			} 
			else if (node.next != null && node.next.key == hashedKey)
			{
				node.next = node.next.next;
				--N;
			}
		}
	}

	private int hashToPos(int hashedKey)
	{
		hashedKey ^= (hashedKey >>> 20) ^ (hashedKey >>> 12) ^ (hashedKey >>> 7) ^ (hashedKey >>> 4);
		return hashedKey & (M - 1);

	}
}
