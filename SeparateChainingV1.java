
public class SeparateChainingV1<KEY>
{
	enum State
	{
		INITIAL, GET, PUT, DELETE
	}

	private NodeSingle<KEY>[] heads;
	private int N, M;

	int savedHashPos = 0;
	State prevState = State.INITIAL;

	@SuppressWarnings("unchecked")
	SeparateChainingV1(int maxN)
	{
		N = 0;
		M = 2*maxN;
		heads = new NodeSingle[M];
	}

	void put(KEY key, int index)
	{
		int i;
		if (prevState == State.GET)
		{
			i = savedHashPos;
		} 
		else
		{
			i = hashToPos(key.hashCode());
		}

		prevState = State.PUT;

		heads[i] = new NodeSingle<KEY>(key, index, heads[i]);
		++N;
	}

	int get(KEY key)
	{
		savedHashPos = hashToPos(key.hashCode());		
		prevState = State.GET;

		for (NodeSingle<KEY> node = heads[savedHashPos]; node != null; node = node.next)
		{
			if (node.key.equals(key))
				return node.index;
		}
		return -1;
	}

	public void delete(KEY key)
	{
		prevState = State.DELETE;
		int hashToPos=hashToPos(key.hashCode());
		
		for (NodeSingle<KEY> node = heads[hashToPos]; node != null; node = node.next)
		{
			if (node.key.equals(key))
			{
				heads[hashToPos] = node.next;
				--N;
			} 
			else if (node.next != null && node.next.key.equals(key))
			{
				node.next = node.next.next;
				--N;
			}
		}
	}

	private int hashToPos(int hashedKey)
	{
		// copied hashed function from hashmap
		return (hashedKey ^ (hashedKey >>> 16)) & (M - 1);
	//	hashedKey ^= (hashedKey >>> 20) ^ (hashedKey >>> 12) ^ (hashedKey >>> 7) ^ (hashedKey >>> 4);
	//	return hashedKey & (M - 1);

	}
}
