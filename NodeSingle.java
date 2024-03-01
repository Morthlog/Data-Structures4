
public class NodeSingle
{
	int key = 0;
	int index = -1;
	NodeSingle next = null;

	NodeSingle(int key, int index, NodeSingle node)
	{
		this.key = key;
		this.index = index;
		next = node;
	}
}
