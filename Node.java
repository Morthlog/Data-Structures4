
/**
 * ListNode represents a list node
 * Each node contains a generic type T as data and a reference to the next node in the list.
 */
public class Node<KEY, DATA> 
{
	protected KEY key ;
    protected DATA data;
    protected int next = -1;
    protected int prev = -1;

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

    /**
     * Returns this node's data
     *
     * @return the reference to node's data
     */
    public DATA getData() 
    {
        // return data stored in this node
        return data;
    }
    
    public KEY getKey() 
    {
        // return key stored in this node
        return key;
    }
    /**
     * Get reference to next node
     *
     * @return the next node
     */
    public int getNext() 
    {
        // get next node
        return next;
    }

    public int getPrev() 
    {
        // get next node
        return prev;
    }

    /**
     * Sets reference to next node
     *
     * @param next reference
     */
    public void setNext(int next) 
    {
        this.next = next;
    }
    
    
    /**
     * Sets reference to previous node
     *
     * @param prev reference
     */
    public void setPrev(int prev) 
    {
        this.prev = prev;
    }
}


