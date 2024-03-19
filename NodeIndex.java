
/**
 * ListNode represents a list node
 * Each node contains a generic type T as data and a reference to the next node in the list.
 */
public class NodeIndex<KEY, DATA> 
{
	protected KEY key ;
    protected DATA data;
    protected int index;
    protected NodeIndex<KEY, DATA> next = null;
    protected NodeIndex<KEY, DATA> prev = null;

    /**
     * Constructor. Sets data
     *
     * @param data the data stored
     */
    NodeIndex(KEY key, DATA data, int index) 
    {
    	this.key = key;
        this.data = data;
        this.index = index;
    }
}