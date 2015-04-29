/**
*Yassine Khaliqui
*109050245
*Homework Assignment Number 4
*CSE 214 Recitation Section 06
*Recitation TA: Kevin Flyangolts 
*Grading TA: Zheyuan (Jeffrey) Gao;
*@author Yassine
*/
import java.util.Scanner;

public class Simulator {

	static final int NUM_BUS_STOPS = 8;
	static int numInBusses, numOutBusses, minSizeGroup, maxSizeGroup, capacity, duration, totalPickedUp;
	static double arrivalProb;
	static PassengerQueue[] busStops = new PassengerQueue[NUM_BUS_STOPS];
	static String inRoute[]  = {"South P", "West", "SAC", "Chapin"};
	static String outRoute[] = {"South P", "PathMart", "Walmart", "Target"};
	static String stops[] = {"South P", "West", "SAC", "Chapin", "South P", "PathMart", "Walmart", "Target"};
	static Bus[] inBusses, outBusses;
	static PassengerQueue q;
	static Passenger p;
	static Bus temp;
	/**
	 * Creates a Simulator with no parameters.
	 */
	Simulator(){}
	/**
	 * Creates a Simulator with set parameters.
	 * @param in: the number of in route busses
	 * @param out: the number of out route busses
	 * @param min: the smallest possible group of passengers
	 * @param max: the largest possible group of passengers
	 * @param prob: the probability that a passenger group will arrive at a bus stop
	 * @param cap: the maximum capacity that the bus can carry
	 */
	Simulator(int in, int out, int min, int max, double prob,int cap){
		numInBusses = in;
		numOutBusses = out;
		minSizeGroup = min;
		maxSizeGroup = max;
		arrivalProb = prob;
		capacity = cap;
		
		inBusses = new Bus[numInBusses];
		outBusses = new Bus[numOutBusses];
		
		int rest = 0;
		
		for(int i = 0; i < numInBusses; i++){
			temp = new Bus();
			temp.setMaxCapacity(cap);
			temp.setRoute(0);
			temp.setNextStop(0);
			temp.setToNextStop(0);
			temp.setTimeToRest(rest);
			inBusses[i] = temp;
			rest += 30;
		}
		rest = 0;
		for(int i = 0; i < numOutBusses; i++){
			temp = new Bus();
			temp.setMaxCapacity(cap);
			temp.setRoute(1);
			temp.setNextStop(4);
			temp.setToNextStop(0);
			temp.setTimeToRest(rest);
			outBusses[i] = temp;
			rest += 30;
		}
	}
	
	public static double[] simulate(int duration){
		
		totalPickedUp = 0;
		int available = 0;
		int pickup = 0;
		int dropoff = 0;
		
		double groupsServed = 0;
		double totalTimeWaited = 0;
		for(int k = 0; k < NUM_BUS_STOPS; k++) {
		    busStops[k] = new PassengerQueue();
		}
		
		for(int i = 0; i < duration; i++){
			
			System.out.println("Minute: " + i);
			
			//ADDING PASSENGERS TO BUS STOPS
			for(int j = 0; j < inRoute.length; j++){
				if(Math.random() < arrivalProb){
					p = new Passenger((int)randomInt(minSizeGroup, maxSizeGroup + 1), (int) randomInt(j, inRoute.length), i, j);
					System.out.println(p.toString());
					busStops[j].enqueue(p);				
				}
				if(Math.random() < arrivalProb){
					p = new Passenger((int)randomInt(minSizeGroup, maxSizeGroup + 1), (int) randomInt(j + 4, inRoute.length + outRoute.length), i, j);
					busStops[j + 4].enqueue(p);
					System.out.println(p.toString());
				}
			}
			//MOVING THE IN BUSSES AROUND
			for(int j = 0; j < inBusses.length; j++){
				//Bus is resting
				if(inBusses[j].getTimeToRest() != 0){
					System.out.println("In Route Bus " + j + " is resting at South P for " + inBusses[j].getTimeToRest() + " minutes.");
					inBusses[j].setTimeToRest(inBusses[j].getTimeToRest() - 1);
				}
				//Bus is moving
				else{
					//Bus at the end of it's circuit
					if(inBusses[j].getNextStop() == 4 && inBusses[j].getToNextStop() == 20){
						inBusses[j].setToNextStop(0);
						inBusses[j].setNextStop(0);
						inBusses[j].setTimeToRest(30);
						System.out.println("Out Route Bus " + j + " has reached it's final stop.");
					}
					//Stops at a bus stop
					else if(inBusses[j].getToNextStop() == 0){
						//Unloading
						if(inBusses[j].getSize() != 0){
							dropoff = inBusses[j].unload(inBusses[j].getNextStop());
							groupsServed += dropoff;
							System.out.println(dropoff + " passenger were dropped off at " + stops[inBusses[j].getNextStop()] + ".");
						}	
						//Loading
						if(inBusses[j].getSize() != capacity && busStops[inBusses[j].getNextStop()].size() != 0 && inBusses[j].getTimeToRest() == 0){
							available = capacity - inBusses[j].getSize();
							p = busStops[inBusses[j].getNextStop()].peek();
							for(int r = 0; r <= busStops[inBusses[j].getNextStop()].size(); r++){
								if(p.getSize() < available){
									available -= p.getSize();
									totalTimeWaited += (i - p.getArrivalT());
									try {
										inBusses[j].load(busStops[inBusses[j].getNextStop()].dequeue());
									} catch (EmptyQueueException e) {
										e.getMessage();
									}
									pickup++;
									totalPickedUp++;
									p = busStops[inBusses[j].getNextStop()].peek();
								}
								else
									p = p.getPrev();
							}
						}
						System.out.println("In Route Bus " + j + " arrives at " + stops[inBusses[j].getNextStop()] + ". " + pickup + " passengers were picked up.");
						inBusses[j].setNextStop(inBusses[j].getNextStop() + 1);
						inBusses[j].setToNextStop(20);
						pickup = 0;
					}
					//Moving from stop to stop.
					else {
						inBusses[j].setToNextStop(inBusses[j].getToNextStop() - 1);
						System.out.println("In Route Bus " + j + " is moving towards " + stops[inBusses[j].getNextStop()] + "; arrive in " + inBusses[j].getToNextStop() + ".");
					}
				}
			}
			//MOVING THE OUT BUSSES AROUND
			for(int j = 0; j < outBusses.length; j++){
				//Bus is resting
				if(outBusses[j].getTimeToRest() != 0){
					System.out.println("Out Route Bus " + j + " is resting at South P for " + outBusses[j].getTimeToRest() + " minutes.");
					outBusses[j].setTimeToRest(outBusses[j].getTimeToRest() - 1);
				}
				//Bus is moving
				else{
					//Bus at the end of it's circuit
					if(outBusses[j].getNextStop() == 8 && outBusses[j].getToNextStop() == 20){
						outBusses[j].setToNextStop(0);
						outBusses[j].setNextStop(4);
						outBusses[j].setTimeToRest(30);
						System.out.println("Out Route Bus " + j + " has reached it's final stop.");
					}
					//Stops at a bus stop
					else if(outBusses[j].getToNextStop() == 0){
						//Unloading
						if(outBusses[j].getSize() != 0){
							dropoff = outBusses[j].unload(outBusses[j].getNextStop());
							groupsServed += dropoff;
							System.out.println(dropoff + " passenger were dropped off at " + stops[outBusses[j].getNextStop()]);
						}
						//Loading
						if(outBusses[j].getSize() != capacity && busStops[outBusses[j].getNextStop()].size() != 0 && outBusses[j].getTimeToRest() == 0){
							available = capacity - outBusses[j].getSize();
							p = busStops[outBusses[j].getNextStop()].peek();
							for(int r = 0; r <= busStops[outBusses[j].getNextStop()].size(); r++){
								if(p.getSize() < available){
									available -= p.getSize();
									try {
										outBusses[j].load(busStops[outBusses[j].getNextStop()].dequeue());
									} catch (EmptyQueueException e) {
										e.getMessage();
									}
									pickup++;
									totalPickedUp++;
									p = busStops[outBusses[j].getNextStop()].peek();
								}
								else
									p = p.getPrev();
							}
						}
						System.out.println("Out Route Bus " + j + " arrives at " + stops[outBusses[j].getNextStop()] + ". " + pickup + " passengers were picked up.");
						outBusses[j].setNextStop(outBusses[j].getNextStop() + 1);
						outBusses[j].setToNextStop(20);
						pickup = 0;
					}
					//Moving from stop to stop.
					else {
						outBusses[j].setToNextStop(outBusses[j].getToNextStop() - 1);
						System.out.println("Out Route Bus " + j + " is moving towards " + stops[outBusses[j].getNextStop()] + "; arrive in " + outBusses[j].getToNextStop() + ".");
					}
				}
			}
			//Summary
			for(int y = 0; y < NUM_BUS_STOPS; y++){
				System.out.print(y + " (" + stops[y] +"): ");
				if(!busStops[y].isEmpty()){
					p = busStops[y].peek();
					for(int z = 0; z < busStops[y].size(); z++){
						System.out.print(p.formatted() + " ");
						p = p.getPrev();
					}
				}
				System.out.println();
			}
			System.out.println();
		}
		
		double[] array2 = {groupsServed, totalTimeWaited};
		return array2;
	}
	
	public static double randomInt(double min, double max){
		double r = Math.random();
		return (r * (max - min)) + min;
	}
	
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		String input = "";
		double prob = 0;
		do{
			System.out.println("Enter the number of In Route busses: ");
			int in = s.nextInt();
			System.out.println("Enter the number of Out Route busses: ");
			int out = s.nextInt();
			System.out.println("Enter the minimum group size of passengers: ");
			int min = s.nextInt();
			System.out.println("Enter the maximum group size of passengers: ");
			int max = s.nextInt();
			while(prob <= 0 || prob >= 1){
				System.out.println("Enter the arrival probability: ");
				prob = s.nextDouble();
			}
			System.out.println("Enter the capacity of a bus: ");
			int cap = s.nextInt();
			System.out.println("Enter the duration of the simulation: ");
			int dur = s.nextInt();
			Simulator sim = new Simulator(in, out, min, max, prob, cap);
			double[] result = simulate(dur);
			System.out.println((int)result[0] + " group of passengers served. Average wait time is " + result[1]/totalPickedUp + " minutes.");
			System.out.println("Will you like to try another simulation? (Y/N)");
			input = s.nextLine();
			input = s.nextLine();
			prob = 0;
		}
		while(!input.equalsIgnoreCase("N"));
		System.out.println("The simulation has terminated...");
	}
	
}
