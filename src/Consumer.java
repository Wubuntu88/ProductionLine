/**
 * 
 * @author William Gillespie
 * Course: Computer Operating Systems
 * Date: 2015-05-30
 *
 *This class consumes widgets from the last buffer and is the last
 *destination for widgets.
 *
 *This class was modified from the Consumer class that was made available
 *by the instructor, Matt Evett.
 */
public class Consumer extends Worker{
	private BoundedBuffer buffer;
	private int widgetsToConsume;
	private Widget itemConsuming;
	public Consumer(BoundedBuffer b, int widgets){
		buffer = b;
		widgetsToConsume = widgets;
	}
	/**
	 * Consumes a widget from the buffer
	 */
	public void run(){
	   while (widgetsToConsume > 0){
		   BoundedBuffer.napping();
		   this.itemConsuming = null;

		   this.itemConsuming = (Widget)buffer.remove();
		   
		   BoundedBuffer.napping();
		   this.itemConsuming = null;
		   --widgetsToConsume;
	      }
	   System.exit(0);//exits after last widget is fully consumed.
	   }
	/**
	 * Returns the item that the Consumer is currently working on.
	 */
	public Widget getItemConsuming(){
		return this.itemConsuming;
	}
}
