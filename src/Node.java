/**
 * ListNode represents a list node
 * Each node contains a generic type T as data and a reference to the next node in the list.
 */
public class Node<KEY, DATA> 
{
	public KEY key ;
    public DATA data;
    public Node<KEY, DATA> next = null;
    public Node<KEY, DATA> prev = null;

    /**
     * Constructor. Sets data
     *
     * @param data the data stored
     * @return
     */
    Node(KEY key, DATA data) 
    {
    	this.key = key;
        this.data = data;
    }

}