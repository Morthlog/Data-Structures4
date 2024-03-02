//? Fixed put and delete, not feeling it like doing deeper checks for being more efficient, results seem ok

public class SeparateChaining<KEY>
{

	private NodeSingle[] heads;
	private int N, M;

	
	SeparateChaining(int maxN)
	{
		N = 0;
		M = (int) (maxN /5);
		heads = new NodeSingle[M];
	}

	void put(KEY key, int index)
	{
		int hashedKey = key.hashCode();
		int i = hashToPos(hashedKey);
		NodeSingle temp = heads[i];
		if (heads[i]== null)
		{
			heads[i] = new NodeSingle(hashedKey, index, null);
			++N;
			return;
		}
		while (temp.next!=null) temp = temp.next;
			temp.next = new NodeSingle(hashedKey, index, null);
		N++;
	}
	
	int get(KEY key)
	{
		int hashedKey = key.hashCode();
		return searchR(heads[hashToPos(hashedKey)], hashedKey);
	}

	private int searchR(NodeSingle t, int hashedKey)
	{
		if (t == null)
			return -1;
		if (t.key == hashedKey)
			return t.index;
		return searchR(t.next, hashedKey);
	}

	public void delete(KEY key)
	{
		int hashedKey = key.hashCode();
		if (deleteR(heads[hashToPos(hashedKey)], hashedKey) ==0)
			N--;
	}

	int deleteR(NodeSingle node, int hashedKey)
	{
		if (node == null)
			return -1;
		if(heads[hashToPos(hashedKey)] == node && node.key == hashedKey)
		{
			heads[hashToPos(hashedKey)] = node.next;
			return 0;
		}
		else if (node.next!=null && node.next.key == hashedKey)
		{
			node.next = node.next.next;
			return 0;
		}	
		return deleteR(node.next, hashedKey);
	}

	private int hashToPos(int hashedKey)
	{
		hashedKey ^= (hashedKey >>> 20) ^ (hashedKey >>> 12) ^ (hashedKey >>> 7) ^ (hashedKey >>> 4);
	       return hashedKey & (M-1);

	}
}

