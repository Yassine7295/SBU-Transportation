/**
*Yassine Khaliqui
*109050245
*Homework Assignment Number 4
*CSE 214 Recitation Section 06
*Recitation TA: Kevin Flyangolts 
*Grading TA: Zheyuan (Jeffrey) Gao;
*@author Yassine
*/
public class Passenger {
	
	private int size;
	private int dest;
	private int arrivalT;
	private int location;
	private String stops[]  = {"South P", "West", "SAC", "Chapin", "South P", "PathMart", "Walmart", "Target"};
	private Passenger prev;
	private Passenger next;
	/**
	 * Creates a Passenger object with no parameters.
	 */
	Passenger(){
		this.prev = null;
		this.next = null;
	}
	/**
	 * Creates a Passenger object with the given parameters.
	 * @param size: size of the group
	 * @param dest: the destination
	 * @param arrivalT: the time they arrived at the bus stop
	 * @param location: which bus stop they arrive at
	 */
	Passenger(int size, int dest, int arrivalT, int location){
		this.size = size;
		this.dest = dest;
		this.arrivalT = arrivalT;
		this.location = location;
		this.prev = null;
		this.next = null;
	}
	/**
	 * Sets the location.
	 * @param location: the preferred location
	 */
	public void setLocation(int location){
		this.location = location;
	}
	/**
	 * Returns the location.
	 * @return: the location
	 */
	public int getLocation(){
		return location;
	}
	/**
	 * Sets the size.
	 * @param size: the preferred size
	 */
	public void setSize(int size){
		this.size = size;
	}
	/**
	 * Sets the destination.
	 * @param dest: the preferred destination.
	 */
	public void setDest(int dest){
		this.dest = dest;
	}
	/**
	 * Sets the arrival time.
	 * @param arrivalT: the preferred arrival time.
	 */
	public void setArrivalT(int arrivalT){
		this.arrivalT = arrivalT;
	}
	/**
	 * Sets the link to the next Passenger on the queue.
	 * @param next: the next Passenger
	 */
	public void setNext(Passenger next){
		this.next = next;
	}
	/**
	 * Sets the link to the previos Passenger on the queue.
	 * @param prev: the previous Passenger
	 */
	public void setPrev(Passenger prev){
		this.prev = prev;
	}
	/**
	 * Returns the size of the group.
	 * @return the size of the group.
	 */
	public int getSize(){
		return size;
	}
	/**
	 * Returns the destination.
	 * @return the destination.
	 */
	public int getDest(){
		return dest;
	}
	/**
	 * Returns the arrival time.
	 * @return the arrival time.
	 */
	public int getArrivalT(){
		return arrivalT;
	}
	/**
	 * Returns the next Passenger on the queue.
	 * @return the next Passenger
	 */
	public Passenger getNext(){
		return next;
	}
	/**
	 * Returns the previous Passenger on the queue.
	 * @return
	 */
	public Passenger getPrev(){
		return prev;
	}
	/**
	 * Returns a neatly formatted string with the attributes of the passenger.
	 * @return attributes of the passenger
	 */
	public String toString(){
		return "A group of " + getSize() +" passengers arrived at " + stops[getLocation()] + " headed to " + stops[getDest()];
	}
	/**
	 * Returns a formatted string with the attributes of the passenger.
	 * @return attributes of the passenger formatted
	 */
	public String formatted(){
		return "(" + getSize() + ", " + getDest() + " (" + stops[getDest()] + "), " + getArrivalT() + " )";
	}
	
}
