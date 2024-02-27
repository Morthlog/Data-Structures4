import java.util.*;
import java.io.PrintStream;

public class  DoubleEndedQueueImpl<KEY, VALUE> implements DoubleEndedQueue<KEY, VALUE> 
{
    private Node<KEY, VALUE> head = null;
    private Node<KEY, VALUE> tail = null;
    private int size=0;
    
    public boolean isEmpty()
    {
        return head == null;
    }

    /**
	 * inserts data of type T at the front of the queue
	 */	
	public void addFirst(KEY key, VALUE data)
	{
		Node<KEY, VALUE> n = new Node<KEY, VALUE>(key, data);

        if (isEmpty()) 
        {
            head = n;
            tail = n;
        } 
        else 
        {
            n.setNext(head);
            head.setPrev(n);
            head = n;
        }
        size++;
    }

	/**
	 * removes and returns the data at the front of the queue
	 * @return data of type T from the front of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	public VALUE  removeFirst() throws NoSuchElementException
	{
		if (isEmpty()) 
		{  
			throw new NoSuchElementException(); 
		}
          
        VALUE data = head.getData();

        if (head == tail)
        {
        	 head = tail = null;
        }
           
        else
        {
        	Node<KEY, VALUE> secondNode = head.getNext();
        	secondNode.setPrev(null);
        	head = secondNode;
        }
          
        size--;
        return data;
    }

	/**
	 * inserts data of type T at the end of the queue
	 */
	public void addLast(KEY key, VALUE data)
	{
		Node<KEY, VALUE> n = new Node<KEY, VALUE>(key,data);

        if (isEmpty()) 
        {
            head = n;
            tail = n;
        } 
        else 
        {
            tail.setNext(n);
            n.setPrev(tail);
            tail = n;
        }
        size++;
    }

	/**
	 * removes and returns the data from the end of the queue
	 * @return data of type T from the end of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	public VALUE removeLast() throws NoSuchElementException
	{
		if (isEmpty())
		{
			 throw new NoSuchElementException();
		}
          
		VALUE data = tail.getData();

        if (head == tail)
        {
        	 head = tail = null;
        }
           
        else 
        {
        	Node<KEY, VALUE> beforeLast= tail.getPrev();
            beforeLast.setNext(null);
            tail=beforeLast;
        }
        size--;
        return data;
    }
	
	/**
	 * returns without removing the data at the front of the queue
	 * @return data of type T from the front of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	public Node<KEY, VALUE> getFirst()
	{
		if (isEmpty())
		{
			 throw new NoSuchElementException();
		}
		
		return head;    	
    }

	/**
	 * returns without removing the data from the end of the queue
	 * @return data of type T from the end of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	public Node<KEY, VALUE> getLast()
	{
		if (isEmpty())
		{
			 throw new NoSuchElementException();
		}
		
		return tail;    
    }	
	
	/**
	 * prints the data of the queue, starting from the front, 
     	 * to the print stream given as argument. For example, to 
         * print the elements to the
	 * standard output, pass System.out as parameter. E.g.,
	 * printQueue(System.out);
	 */
	public void printQueue(PrintStream stream)
	{
		if (isEmpty()) 
		{
			stream.println("List is empty");
			return;
		}

		Node<KEY, VALUE> current = head;
		
		StringBuilder message = new StringBuilder();
		
		// while not at end of list, output current node's data
	//	message.append("HEAD -> "); //? Not wanted for this exercise
		
		while (current != null) 
		{					
			message.append(current.getData().toString());
			
		    if (current.getNext() != null)
	        {
		    	// message.append(" <-> "); //? Not wanted for this exercise
				message.append("\n\n");
	        }
		  	    		
		    current = current.next;
		}
		
	//	message.append(" <- TAIL"); //? Not wanted for this exercise
		stream.println(message);
    }


	/**
	 * returns the size of the queue, 0 if empty
	 * @return number of elements in the queue
	 */
	public int size()
	{
        return size;
    }
}

