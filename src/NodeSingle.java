
public class NodeSingle<Key>
{
	Key key = null;
	int index = -1;
	NodeSingle<Key> next = null;

	NodeSingle(Key key, int index, NodeSingle<Key> node)
	{
		this.key = key;
		this.index = index;
		next = node;
	}
}
