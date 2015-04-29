/**
*Yassine Khaliqui
*109050245
*Homework Assignment Number 4
*CSE 214 Recitation Section 06
*Recitation TA: Kevin Flyangolts 
*Grading TA: Zheyuan (Jeffrey) Gao;
*@author Yassine
*/
public class Bus {

	private int max_capacity;
	private int route; // 0 == in     1 == out
	private int nextStop; 
	private int toNextStop;
	private int timeToRest;
	private int size = 0;
	private PassengerQueue onBoard;
	/**
	 * Creates a Bus with an on board queue no parameters.
	 */
	Bus() {
		onBoard = new PassengerQueue();
	}
	/**
	 * Creates a Bus with an on board queue and set parameters.
	 * @param max_capacity: the maximum capacity
	 * @param route: the route taken
	 * @param nextStop: the next stop
	 * @param toNextStop: the time require to reach the next stop
	 * @param timeToRest: the time, it has, to rest
	 */
	Bus(int max_capacity, int route, int nextStop, int toNextStop, int timeToRest){
		this.max_capacity = max_capacity;
		this.route = route;
		this.nextStop = nextStop;
		this.toNextStop = toNextStop;
		this.timeToRest = timeToRest;
		onBoard = new PassengerQueue();
		size = 0;
	}
	/**
	 * Returns the sum of the size of the groups of passengers.
	 * @return sum of the size of the groups
	 */
	public int getSize(){
		return size;
	}
	/**
	 * Returns the maximum capacity of the bus
	 * @return maximum capacity
	 */
	public int getMaxCapacity(){
		return max_capacity;
	}
	/**
	 * Returns the route of the bus.
	 * @return the route of the bus
	 */
	public int getRoute(){
		return route;
	}
	/**
	 * Returns the next stop on the bus.
	 * @return the next stop
	 */
	public int getNextStop(){
		return nextStop;
	}
	/**
	 * Returns the time required to reach the next stop.
	 * @return time to the next stop
	 */
	public int getToNextStop(){
		return toNextStop;
	}
	/**
	 * Returns the time the bus has to rest for.
	 * @return rest time
	 */
	public int getTimeToRest(){
		return timeToRest;
	}
	/**
	 * Returns the passengers on board.
	 * @return a queue of the passengers on board
	 */
	public PassengerQueue getOnBoard(){
		return onBoard;
	}
	/**
	 * Sets the maximum capacity of a bus.
	 * @param max: preferred maximum capacity
	 */
	public void setMaxCapacity(int max){
		max_capacity = max;
	}
	/**
	 * Sets the route of the bus.
	 * @param route: the preferred route of the bus.
	 */
	public void setRoute(int route){
		this.route = route;
	}
	/**
	 * Sets the next stop.
	 * @param nextStop: the preferred next stop
	 */
	public void setNextStop(int nextStop){
		this.nextStop = nextStop;
	}
	/**
	 * Sets the time requires to reach the next stop.
	 * @param nextStop: the preferred time required
	 */
	public void setToNextStop(int toNextStop){
		this.toNextStop = toNextStop;
	}
	/**
	 * Sets the time the bus has to rest for.
	 * @param timeToRest: the preferred rest time
	 */
	public void setTimeToRest(int timeToRest){
		this.timeToRest = timeToRest;
	}
	/**
	 * Sets the passenger queue that's on the bus.
	 * @param p: preferred passenger queue
	 */
	public void setOnBoard(PassengerQueue p){
		this.onBoard = p;
	}
	/**
	 * Loads a passenger onto the bus.
	 * @param p: the passenger that will be loaded onto the bus
	 * <dt><b>Precondition:</b><dd> This Bus object has been initialized.
	 * <dt><b>Postcondition:</b><dd> The passenger has been loaded onto the bus.
	 */
	public void load(Passenger p){
		size += p.getSize();
		PassengerQueue temp = getOnBoard();
		temp.enqueue(p); 
		setOnBoard(temp);
	}
	/**
	 * Unload a passenger that has reach their destination from the bus and returns how many have passengers have been unloaded.
	 * @param dest: the reached destination
	 * <dt><b>Precondition:</b><dd> This Bus object has been initialized.
	 * <dt><b>Postcondition:</b><dd> The passengers have been unloaded.
	 * @return the number of passengers that have reached their destination and have been unloaded
	 */
	public int unload(int dest){
		int unload = 0;
		Passenger cursor = onBoard.peek();
		for(int i = 0; i < onBoard.size(); i++){
			while(cursor != null){
				if(cursor.getDest() == dest){
					size -= cursor.getSize();
					unload++;
					if(onBoard.size() == 1){
						cursor = null;
						onBoard.setHead(null);
						onBoard.setTail(null);
					}
					else if(cursor.getNext() == null && cursor.getPrev() != null){
						cursor = cursor.getPrev();
						onBoard.setHead(cursor);
					}
					else if(cursor.getPrev() == null && cursor.getNext() != null){
						cursor = cursor.getNext();
						onBoard.setTail(cursor);
						cursor = null;
					}
					else if(cursor.getNext() != null && cursor.getPrev() != null){
						cursor.getNext().setPrev(cursor.getPrev());
						cursor.getPrev().setNext(cursor.getNext());
						cursor = cursor.getPrev();
					}
				}
				else
					cursor = cursor.getPrev();
			}
		}
		return unload;
	}
	
}
