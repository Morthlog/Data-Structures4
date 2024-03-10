public class SeparateChainingV1<KEY>
{
	enum State
	{
		INITIAL, GET, PUT, DELETE
	}

	private NodeSingle<KEY>[] heads;
	private int M;

	int savedHashPos = 0;
	State prevState = State.INITIAL;

	@SuppressWarnings("unchecked")
	SeparateChainingV1(int maxN)
	{
		M = 2*maxN;
		heads = new NodeSingle[M];
	}
	
	void put(KEY key, int index)
	{		
		int i;
		if (prevState == State.DELETE)
		{
			i = hashToPos(key.hashCode());			
		} 
		else
		{
			i = savedHashPos;			
		}

		prevState = State.PUT;

		heads[i] = new NodeSingle<KEY>(key, index, heads[i]);
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
		
		if (heads[savedHashPos].key.equals(key))
		{
			heads[savedHashPos] = heads[savedHashPos].next;
			return;
		} 
		for (NodeSingle<KEY> node = heads[savedHashPos]; node.next != null; node = node.next)
		{		
			if (node.next.key.equals(key))
			{
				node.next = node.next.next;
				break;
			}
		}
	}
	
	private int hashToPos(int hashedKey)
	{
		return (hashedKey ^ (hashedKey >>> 16)) & (M - 1);
	}
}
