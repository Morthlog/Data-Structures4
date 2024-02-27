import java.io.PrintStream;
import java.util.NoSuchElementException;

/**
 * Defines the methods for a Double-ended Queue that handles String items
 */
public interface DoubleEndedQueue<KEY, VALUE> 
{
	/**
	 * @return true if the queue is empty
	 */
	public boolean isEmpty();

	/**
	 * inserts data of type T at the front of the queue
	 */
	public void addFirst(KEY key, VALUE data);

	/**
	 * removes and returns the data at the front of the queue
	 * @return data of type T from the front of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	public VALUE removeFirst() throws NoSuchElementException;

	/**
	 * inserts data of type T at the end of the queue
	 */
	public void addLast(KEY key, VALUE data);

	/**
	 * removes and returns the data from the end of the queue
	 * @return data of type T from the end of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	public VALUE removeLast() throws NoSuchElementException;
	
	/**
	 * returns without removing the data at the front of the queue
	 * @return data of type T from the front of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	public Node<KEY, VALUE> getFirst();

	/**
	 * returns without removing the data from the end of the queue
	 * @return data of type T from the end of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	public Node<KEY, VALUE> getLast();
	
	
	/**
	 * prints the data of the queue, starting from the front, 
     	 * to the print stream given as argument. For example, to 
         * print the elements to the
	 * standard output, pass System.out as parameter. E.g.,
	 * printQueue(System.out);
	 */
	public void printQueue(PrintStream stream);

	/**
	 * returns the size of the queue, 0 if empty
	 * @return number of elements in the queue
	 */
	public int size();
}
