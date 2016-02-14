import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author William Gillespie
 * Course: Computer Operating Systems
 * Date: 2015-05-30
 *
 *This class creates a GUI that simulates a production line.
 *The workers are the producerA, ProdConsB and C, and ConsumerD.
 *The Buffers are the left, center, and right buffers.
 *
 *As for the worker states, the orc is the one that is working, the 
 *sleepy guy is the one who is idle, and the angry guy who is shouting
 *is the one who is waiting to place a widget on the conveyer belt.
 */

public class Factory {
	public final static int NUMBER_OF_WIDGETS = 24;
	public static void main(String args[]) {
		
		BoundedBuffer leftBuffer = new BoundedBuffer();
		BoundedBuffer centerBuffer = new BoundedBuffer();
		BoundedBuffer rightBuffer = new BoundedBuffer();

		
		Producer producerA = new Producer(leftBuffer, NUMBER_OF_WIDGETS);
		producerA.setName("producerA");
		ProdCons prodConsB = new ProdCons(leftBuffer, centerBuffer, NUMBER_OF_WIDGETS);
		prodConsB.setName("prodConsB");
		ProdCons prodConsC = new ProdCons(centerBuffer, rightBuffer, NUMBER_OF_WIDGETS);
		prodConsC.setName("prodConsC");
  		Consumer consumerD = new Consumer(rightBuffer, NUMBER_OF_WIDGETS);
  		consumerD.setName("consumerD");
  		
  		FactoryFrame facFr = new FactoryFrame(leftBuffer, centerBuffer, rightBuffer,
  				producerA, prodConsB, prodConsC, consumerD);
  		facFr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        facFr.setVisible(true);
  		
  		
  		producerA.start();
  		prodConsB.start();
  		prodConsC.start();
  		consumerD.start();
	}
}
/**
 * This class is the factory frame that holds the factory panel.
 */
class FactoryFrame extends JFrame
{
	public static final int DEFAULT_WIDTH = 1200;
	public static final int DEFAULT_HEIGHT = 600;
	

	public FactoryFrame(BoundedBuffer leftBuffer, 
						BoundedBuffer centerBuffer, 
						BoundedBuffer rightBuffer,
						Producer producerA,
						ProdCons prodConsB,
						ProdCons prodConsC,
						Consumer consumerD) {
		setTitle("Factory");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		FactoryPanel panel = new FactoryPanel(leftBuffer, centerBuffer, rightBuffer,
				producerA, prodConsB, prodConsC, consumerD);
		getContentPane().add(panel);
	}
}

/**
 * This factory panel is the GUI that shows all of the information about the factory processes.
 * It shows the state of the buffers and the workers.
 * @author will
 *
 */
class FactoryPanel extends JPanel
{
	private static final int TIME_BETWEEN_FRAMES = 100; // msec between rendering each frame of animation   
	private static final int TABLE_WIDTH = (int)(1200/7.0), TABLE_HEIGHT = 100;
	private static final int BUFFER_HEIGHT = (int)(TABLE_HEIGHT*(2.0/3.0));
	private static final int Y_POSITION = 300;
	private static final int imageWidth = 60, imageHeight = 60;

	/**
	 * This methods uses the information of the buffers and the workers to draw 
	 * the current state of the factory.
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);  // Clear Background
		for(int i = 0; i <= 6; i++){//iterate through workers, buffers
			int startingX = i*TABLE_WIDTH;
			if(i%2==0){//draws a table
				g.setColor(Color.ORANGE);
				g.fillRect(startingX, Y_POSITION, TABLE_WIDTH, TABLE_HEIGHT);
				Image imageToUse = null;
				/*
				 * These if statements draw the current worker's state (working, idle, busy)
				 */
				if(i == 0){
					if(producerA.isWorking()){
						imageToUse = orc;
					}else if (producerA.isIdle()){
						imageToUse = sleepy;
					}else if (producerA.isWaiting()){
						imageToUse = sparta;
					}
				}else if(i == 2){
					if(prodConsB.isWorking()){
						imageToUse = orc;
					}else if (prodConsB.isIdle()){
						imageToUse = sleepy;
					}else if (prodConsB.isWaiting()){
						imageToUse = sparta;
					}
				}else if(i == 4){
					if(prodConsC.isWorking()){
						imageToUse = orc;
					}else if (prodConsC.isIdle()){
						imageToUse = sleepy;
					}else if (prodConsC.isWaiting()){
						imageToUse = sparta;
					}
				}else if(i == 6){
					if(consumerD.isWorking()){
						imageToUse = orc;
					}else if (consumerD.isIdle()){
						imageToUse = sleepy;
					}else if (consumerD.isWaiting()){
						imageToUse = sparta;
					}
				}
				g.drawImage(imageToUse, startingX + (int)(TABLE_WIDTH/2.0) - (int)(imageWidth/2.0)
							, Y_POSITION - imageHeight, imageWidth, imageHeight, null);
				
				/*
				 * These pieces of code draw the widgets that a current worker is working on.
				 */
				
				//draw item on producer A's table
				Widget producerItem = producerA.getItemProducing();
				if(producerItem != null && i == 0){
					Image image1 = this.imageFromString(producerItem.getName());
					g.drawImage(image1, startingX + (int)(TABLE_WIDTH/2.0) - (int)(imageWidth/2.0),
							Y_POSITION,//100
							imageWidth, imageHeight, null);
				}
				//draw items on prodConsB and C's table
				//prodCons B's item
				Widget prodConsBsItem = prodConsB.getItemProcessing();
				if(prodConsBsItem != null && i == 2){
					Image image2 = this.imageFromString(prodConsBsItem.getName());
					g.drawImage(image2, startingX + (int)(TABLE_WIDTH/2.0) - (int)(imageWidth/2.0),
							Y_POSITION,//100
							imageWidth, imageHeight, null);
				}
				//prodCons C's item
				Widget prodConsCsItem = prodConsC.getItemProcessing();
				if(prodConsCsItem != null && i == 4){
					Image image3 = this.imageFromString(prodConsCsItem.getName());
					g.drawImage(image3, startingX + (int)(TABLE_WIDTH/2.0) - (int)(imageWidth/2.0),
							Y_POSITION,//100
							imageWidth, imageHeight, null);
				}
				
				//draw item on consumer D's table
				Widget consumerItem = consumerD.getItemConsuming();
				if(consumerItem != null && i == 6){
					Image image4 = this.imageFromString(consumerItem.getName());
					g.drawImage(image4, startingX + (int)(TABLE_WIDTH/2.0) - (int)(imageWidth/2.0),
						Y_POSITION,//100
						imageWidth, imageHeight, null);
				}
				
			}else if(i%2==1){//draw a buffer
				/*
				 * These pieces of code draw the buffers
				 */
				g.setColor(Color.gray);
				g.fillRect(startingX, Y_POSITION, TABLE_WIDTH, BUFFER_HEIGHT);
				
				g.setColor(Color.RED);
				int x = startingX + (int)(TABLE_WIDTH/3);
				g.drawLine(x, Y_POSITION, x, Y_POSITION + BUFFER_HEIGHT);
				x = startingX + (int)(TABLE_WIDTH*2/3.0);
				g.drawLine(x, Y_POSITION, x, Y_POSITION + BUFFER_HEIGHT);
				
				//draw the items for the buffers
				Widget item = null;
				BoundedBuffer buffer = null;
				if(i == 1){
					buffer = leftBuffer;
				}else if(i == 3){
					buffer = centerBuffer;
				}else if(i == 5){
					buffer = rightBuffer;
				}
				/*
				 * This code draws the objects that are in a given buffer
				 */
				for(int j = 0; j <=2; j++){
					item = (Widget)buffer.getItemAtIndex(j);
					
					if(item == null){
						continue;
					}
					//draw items in buffer
					Image image = this.imageFromString(item.getName());
					
					if(image != null){
						g.drawImage(image, startingX + (int)(j*(TABLE_WIDTH/3.0)), Y_POSITION, 
								imageWidth, imageHeight, null);
					}
				}		
			}
		}
	}
	
	
	/*
	 * Images of widgets
	 */
	Image Frog;
	Image Biscuit;
	Image Basket;
	Image Salt;
	Image Quesarito;
	Image Spicy;
	Image Alien;
	Image Baby;
	Image Bat;
	Image Cactus;
	Image Carrot;
	Image Cash;
	Image Cheese;
	Image Chicken;
	Image Clean;
	Image Extinguisher;
	Image ForceField;
	Image Goat;
	Image Hat;
	Image IceCream;
	Image Lemon;
	Image Murloc;
	Image Pie;
	Image Robot;
	
	/*
	 * returns the Image that corresponds to the string
	 */
	private Image imageFromString(String item){
		Image image = null;
		if(item == "Biscuit"){
			image = this.Biscuit;
		}else if(item == "Frog"){
			image = this.Frog;
		}else if(item == "Basket"){
			image = this.Basket;
		}else if(item == "Salt"){
			image = this.Salt;
		}else if(item == "Quesarito"){
			image = this.Quesarito;
		}else if(item == "Spicy"){
			image = this.Spicy;
		}else if(item == "Alien"){
			image = this.Alien;
		}else if(item == "Baby"){
			image = this.Baby;
		}else if(item == "Bat"){
			image = this.Bat;
		}else if(item == "Cactus"){
			image = this.Cactus;
		}else if(item == "Carrot"){
			image = this.Carrot;
		}else if(item == "Cash"){
			image = this.Cash;
		}else if(item == "Cheese"){
			image = this.Cheese;
		}else if(item == "Chicken"){
			image = this.Chicken;
		}else if(item == "Clean"){
			image = this.Clean;
		}else if(item == "Extinguisher"){
			image = this.Extinguisher;
		}else if(item == "ForceField"){
			image = this.ForceField;
		}else if(item == "Goat"){
			image = this.Goat;
		}else if(item == "Hat"){
			image = this.Hat;
		}else if(item == "IceCream"){
			image = this.IceCream;
		}else if(item == "Lemon"){
			image = this.Lemon;
		}else if(item == "Murloc"){
			image = this.Murloc;
		}else if(item == "Pie"){
			image = this.Pie;
		}else if(item == "Robot"){
			image = this.Robot;
		}
		return image;//returns null if invalid string
	}
	
	/*
	 * Loads Images for workers
	 */
	
	Image orc = new ImageIcon("Orc.png").getImage();//the orc means working
	Image sleepy = new ImageIcon("Sleepy.png").getImage();//means is idle
	Image sparta = new ImageIcon("Sparta.png").getImage();//means is waiting to place item on buffer
	
	/*
	 * Data structures for conveyer belts (buffers) and wokers
	 */
	
	BoundedBuffer leftBuffer;
	BoundedBuffer centerBuffer;
	BoundedBuffer rightBuffer;
	Producer producerA;
	ProdCons prodConsB;
	ProdCons prodConsC;
	Consumer consumerD;
	/**
	 * Constructor for objects of class FactoryPanel
	 */
	public FactoryPanel(BoundedBuffer leftBuffer, 
						BoundedBuffer centerBuffer, 
						BoundedBuffer rightBuffer,
						Producer producerA,
						ProdCons prodConsB,
						ProdCons prodConsC,
						Consumer consumerD)
	{
		/*
		 * Loads all of the images for the widgets
		 */
		Biscuit = new ImageIcon("Biscuit.png").getImage();
		Frog = new ImageIcon("Frog.png").getImage();
		Basket = new ImageIcon("Basket.png").getImage();
		Salt = new ImageIcon("Salt.png").getImage();
		Quesarito = new ImageIcon("Quesarito.png").getImage();
		Spicy = new ImageIcon("Spicy.png").getImage();
		Alien = new ImageIcon("Alien.png").getImage();
		Baby = new ImageIcon("Baby.png").getImage();
		Bat = new ImageIcon("Bat.png").getImage();
		Cactus = new ImageIcon("Cactus.png").getImage();
		Carrot = new ImageIcon("Carrot.png").getImage();
		Cash = new ImageIcon("Cash.png").getImage();
		Cheese = new ImageIcon("Cheese.png").getImage();
		Chicken = new ImageIcon("Chicken.png").getImage();
		Clean = new ImageIcon("Clean.png").getImage();
		Extinguisher = new ImageIcon("Extinguisher.png").getImage();
		ForceField = new ImageIcon("ForceField.png").getImage();
		Goat = new ImageIcon("Goat.png").getImage();
		Hat = new ImageIcon("Hat.png").getImage();
		IceCream = new ImageIcon("IceCream.png").getImage();
		Lemon = new ImageIcon("Lemon.png").getImage();
		Murloc = new ImageIcon("Murloc.png").getImage();
		Pie = new ImageIcon("Pie.png").getImage();
		Robot = new ImageIcon("Robot.png").getImage();
		
		/*
		 * sets the data structures up
		 */
		this.leftBuffer = leftBuffer;
		this.centerBuffer = centerBuffer;
		this.rightBuffer = rightBuffer;
		this.producerA = producerA;
		this.producerA.makeIsWorking();
		this.prodConsB = prodConsB;
		this.prodConsB.makeIsIdle();
		this.prodConsC = prodConsC;
		this.prodConsC.makeIsIdle();
		this.consumerD = consumerD;
		this.consumerD.makeIsIdle();
		
		
		TimerListener handler = new TimerListener();  // used to redraw
		Timer t = new Timer(TIME_BETWEEN_FRAMES, handler);
		t.start();
	}


	class TimerListener implements ActionListener
	{
		/**
		 * This method will call repaint().  Every time in between TIME_BETWEEN_FRAMES,
		 * This method will call and repaint the screen to update the user on what
		 * is going on in the factory.
		 */
		public void actionPerformed(ActionEvent event)
		{  
			repaint();  //See the comment about repaint() in ButtonListener.actionPerformed
		} 
	}
}



