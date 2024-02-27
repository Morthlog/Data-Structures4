
/**
 * ListNode represents a list node
 * Each node contains a generic type T as data and a reference to the next node in the list.
 */
public class Node<KEY, DATA> 
{
	KEY key ;
    protected DATA data;
    protected Node<KEY, DATA> next = null;
    protected Node<KEY, DATA> prev = null;

    /**
     * Constructor. Sets data
     *
     * @param data the data stored
     * @return
     */
    Node(DATA data) 
    {
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
    
    /**
     * Get reference to next node
     *
     * @return the next node
     */
    public Node<KEY, DATA> getNext() 
    {
        // get next node
        return next;
    }

    public Node<KEY, DATA> getPrev() 
    {
        // get next node
        return prev;
    }

    /**
     * Sets reference to next node
     *
     * @param next reference
     */
    public void setNext(Node<KEY, DATA> next) 
    {
        this.next = next;
    }
    
    
    /**
     * Sets reference to previous node
     *
     * @param prev reference
     */
    public void setPrev(Node<KEY, DATA> prev) 
    {
        this.prev = prev;
    }
}


