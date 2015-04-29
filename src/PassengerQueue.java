/**
*Yassine Khaliqui
*109050245
*Homework Assignment Number 4
*CSE 214 Recitation Section 06
*Recitation TA: Kevin Flyangolts 
*Grading TA: Zheyuan (Jeffrey) Gao;
*@author Yassine
*/
public class PassengerQueue {
	
	private Passenger front;
	private Passenger rear;
	private int size;
	/**
	 * Creates a PassengerQueue with no passenger.
	 */
	PassengerQueue(){
		front = null;
		rear = null;
		size = 0;
	}
	/**
	 * Sets a passenger to the front of the queue (used for reference).
	 * @param p: the passenger
	 * <dt><b>Precondition:</b><dd> This PassengerQueue object has been initialized.
	 * <dt><b>Postcondition:</b><dd> The passenger is now at the front of the queue.
	 */
	public void setHead(Passenger p){
		this.front = p;
	}
	/**
	 * Sets a passenger to the rear of the queue (used for reference).
	 * @param p: the passenger
	 * <dt><b>Precondition:</b><dd> This PassengerQueue object has been initialized.
	 * <dt><b>Postcondition:</b><dd> The passenger is now at the back of the queue.
	 */
	public void setTail(Passenger p){
		this.rear = p;
	}
	/**
	 * Adds a passenger to the back of the queue.
	 * @param p: the passenger
	 * <dt><b>Precondition:</b><dd> This PassengerQueue object has been initialized.
	 * <dt><b>Postcondition:</b><dd> The passenger is now at the back of the queue.
	 */
	public void enqueue(Passenger p){
		if (front == null) {
			front = p; 
			rear = front;
			size++;
		}
		else {
			rear.setPrev(p);
			p.setNext(rear);
			rear = p;
			size++;
		}
	}
	/**
	 * Removes a passenger to the front of the queue.
	 * @param p: the passenger
	 * <dt><b>Precondition:</b><dd> This PassengerQueue object has been initialized.
	 * <dt><b>Postcondition:</b><dd> The passenger is now no longer on the queue.
	 * @throws EmptyQueueException: Cannot remove from an empty queue.
	 */
	public Passenger dequeue() throws EmptyQueueException {
		Passenger p = new Passenger();
		if (front == null)
			throw new EmptyQueueException();
		p.setSize(front.getSize());
		p.setDest(front.getDest());
		p.setArrivalT(front.getArrivalT());
		p.setLocation(front.getLocation());
		front = front.getPrev();
		size--;
		if (front == null) 
			rear = null;
		return p;
	}
	/**
	 * Returns the passenger at the front of the queue.
	 * <dt><b>Precondition:</b><dd> This PassengerQueue object has been initialized.
	 * @return the passenger at the front
	 */
	public Passenger peek(){
		return front;
	}
	/**
	 * Returns the size of the queue.
	 * <dt><b>Precondition:</b><dd> This PassengerQueue object has been initialized.
	 * @return the size
	 */
	public int size(){
		return size;
	}
	/**
	 * Returns whether or not the queue is empty.
	 * @return true if the queue is empty
	 */
	public boolean isEmpty(){
		return front == null;
	}
}
