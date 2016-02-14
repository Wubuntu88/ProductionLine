import java.util.ArrayList;
//import java.util.Random;
/**
 * 
 * @author William Gillespie
 * Course: Computer Operating Systems
 * Date: 2015-05-30
 *
 *This class represents the conveyer belt between two workers.
 *Workers add things to the conveyer belt and take things off it.
 *
 *This class was modified from the BoundedBuffer class that was made available
 *by the instructor, Matt Evett.
 */
public class BoundedBuffer {
	private static final int BUFFER_SIZE = 3;
	private int itemCount;
	private int in; //index of next free position in the buffer
	private int out; //index of next full position in the buffer
	private Object[] buffer;
	public static final int NAP_TIME = 5;
	//private static Random rng;
	
	public BoundedBuffer(){
		itemCount = 0;
		in = 0;
		out = 0;
		buffer = new Object[BUFFER_SIZE];
		//rng = new Random();
	}
	/**
	 * Causes the thread executing this code to sleep for a semi-random amount of time
	 */
	public static void napping() {
	     //int sleepTime = (int) (NAP_TIME + (rng.nextGaussian()/2.0));//for more realistic work times
		int sleepTime = (int) (NAP_TIME * Math.random());
		try { 
			Thread.sleep(sleepTime*1000);
		}
		catch(InterruptedException e) {
	    	 
		}
	}
	
	/**
	 * Allows a worker to add an item to the conveyer belt.
	 * @param item
	 */
	public synchronized void enter(Object item){
		Worker worker = (Worker)Thread.currentThread();
		String workerName = worker.getName();
		Widget widget = (Widget)item;
		while (itemCount == BUFFER_SIZE){
			try {
				System.out.println("WARNING: " + workerName + " is waiting to put " + widget.getName() + " on conveyer");
				worker.makeIsWaiting();
				wait();
				
			} catch (InterruptedException e) {	}
		}
		Thread currentThread = Thread.currentThread();
		
		widget.workUpon((Worker)currentThread);
		
		worker.makeIsIdle();
		
		String currentThreadName = currentThread.getName();
		System.out.println(currentThreadName + " is placing " + widget.getName() + " on belt");

		itemCount++;
		buffer[in] = item;
	    in = (in + 1) % BUFFER_SIZE;
	    BoundedBuffer.napping();
	    notify();
	    
	    
	}//end of enter()
	/**
	 * Allows a worker to remove an object from the conveyer belt
	 * @return the object that was removed
	 */
	public synchronized Object remove() {
	      Widget widget;
	      Worker worker = (Worker)Thread.currentThread();
	      while (itemCount == 0) {
	         try {
	        	 String workerName = worker.getName();
	        	 System.out.println("WARNING: " + workerName + " is idle");
	        	worker.makeIsIdle();
	            wait();
	         }
	         catch (InterruptedException e) { }
	      }
	      
	      
	      worker.makeIsWorking();  
	      
	      // removes an item from the buffer
	      itemCount--;
	      widget = (Widget)buffer[out];
	      buffer[out] = null;
	      out = (out + 1) % BUFFER_SIZE;
	      
	      //creates a string that says which workers have worked on the given widget
	      ArrayList<Worker> workersThatWorkedOnWidget = widget.getWorkersThatWorkedOnWidget();
	      StringBuffer workerList = new StringBuffer("");
	      for(Worker localWorker : workersThatWorkedOnWidget){
	    	  if(workerList.length() == 0){
	    		  workerList.append(localWorker.getName());
	    	  }else{
	    		  workerList.append(", " + localWorker.getName());
	    	  }
	      }
	      
	      String currentThreadName = Thread.currentThread().getName();
	      System.out.println(currentThreadName + " is has retrieved " + widget.getName() + "<handled by " + workerList.toString() + ">");
	      System.out.println(currentThreadName + " is working on " + widget.getName() + "<handled by " + workerList.toString() + ">");
	      
	      notify();
	      
	      return widget;
	}//end of remove()
	
	/**
	 * gets the object at the index of a conveyer belt.
	 * returns null if there is no object at the index.
	 * @param index of the conveyer belt
	 * @return the object at that index
	 */
	public Object getItemAtIndex(int index){
		return buffer[index];	
	}
}
