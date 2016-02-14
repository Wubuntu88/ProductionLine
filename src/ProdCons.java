/**
 * 
 * @author William Gillespie
 * Course: Computer Operating Systems
 * Date: 2015-05-30
 *
 *This class acts as producer and a consumer.  It consumes a widget from a
 *buffer and later places it on another buffer.  It does this in the run() method.
 */
public class ProdCons extends Worker {
	private BoundedBuffer consumtionConveyerBelt;
	private BoundedBuffer productionConveyerBelt;
	private int widgetsToProcess;
	private Widget itemProcessing;
	public ProdCons (BoundedBuffer consumtionConveyerBelt, BoundedBuffer productionConveyerBelt, int widgets){
		this.consumtionConveyerBelt = consumtionConveyerBelt;
		this.productionConveyerBelt = productionConveyerBelt;
		widgetsToProcess = widgets;
	}
	/**
	 * This run method consumes a widget from the consumption belt
	 * and places it on the production belt.
	 */
	public void run(){
		Widget widget;
		while(widgetsToProcess > 0){
			//BoundedBuffer.napping();//I just removed
			widget = (Widget)consumtionConveyerBelt.remove();
			this.itemProcessing = widget;
			BoundedBuffer.napping();
			this.itemProcessing = null;
			
			//widget.workUpon(this);
	        productionConveyerBelt.enter(widget);
	        
	        BoundedBuffer.napping();
	        --widgetsToProcess;
	        //System.out.println("ProdCons widgets left to produce: " + widgetsToProcess);
		}
	}
	/**
	 * Returns the item that the prodCons is currently working on.
	 */
	public Widget getItemProcessing(){
		return this.itemProcessing;
	}
}
