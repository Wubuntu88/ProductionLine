import java.util.Date;
import java.util.Random;

/**
 * 
 * @author William Gillespie
 * Course: Computer Operating Systems
 * Date: 2015-05-30
 *
 * This class is the producer that creates all the widgets.
 * When It calls run() it creates a widget and places it on a conveyer belt.
 *
 *This class was modified from the Producer class that was made available
 *by the instructor, Matt Evett.
 */

public class Producer extends Worker {
	private BoundedBuffer buffer;
	private int widgetsToProduce;
	private int widgetNum = 0;
	private Widget itemProducing;
	public Producer (BoundedBuffer b, int widgets){
		buffer = b;
		widgetsToProduce = widgets;
	}
	/**
	 * creates a widget and places it on the conveyer belt
	 */
	public void run(){
		while(widgetsToProduce > 0){
			Widget widget = new Widget(chooseItem(widgetNum), widgetNum);
			widgetNum++;
			this.itemProducing = widget;
			this.makeIsWorking();
			BoundedBuffer.napping();
			itemProducing = null;
			
			//widget.workUpon(this);
	        buffer.enter(widget);
	        
	        BoundedBuffer.napping();
	        --widgetsToProduce;
	        //System.out.println("Widgets left to produce: " + widgetsToProduce);
		}
	}
	/**
	 * provides the corresponding name of the production Item for the given widget number.
	 * @param num - the number for a widget
	 * @return The product string that corresponds to that number
	 */
	private String chooseItem(int num){
		switch (num){
			case 0:
				return "Frog";
			case 1:
				return "Biscuit";
			case 2:
				return "Basket";
			case 3:
				return "Salt";
			case 4:
				return "Quesarito";
			case 5:
				return "Spicy";
			case 6:
				return "Alien";
			case 7:
				return "Baby";
			case 8:
				return "Bat";
			case 9:
				return "Cactus";
			case 10:
				return "Carrot";
			case 11:
				return "Cash";
			case 12:
				return "Cheese";
			case 13:
				return "Chicken";
			case 14:
				return "Clean";
			case 15:
				return "Extinguisher";
			case 16:
				return "ForceField";
			case 17:
				return "Goat";
			case 18:
				return "Hat";
			case 19:
				return "IceCream";
			case 20:
				return "Lemon";
			case 21:
				return "Murloc";
			case 22:
				return "Pie";
			case 23:
				return "Robot";
			default:
				return "Skull";
		}
	}
	/**
	 * Returns the item that the producer is currently working on.
	 */
	public Widget getItemProducing(){
		return this.itemProducing;
	}
}
