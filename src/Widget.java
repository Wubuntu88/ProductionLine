import java.util.ArrayList;

/**
 * 
 * @author William Gillespie
 * Course: Computer Operating Systems
 * Date: 2015-05-30
 *
 */
public class Widget {
	
	private String name;
	private int widgetNumber;
	private int numberOfWorkersWhoHaveWorkedOnThisWidget;
	private ArrayList<Worker> workersThatWorkedOnWidget; 

	public Widget(String name, int widgetNumber) {
		this.name = name;
		this.widgetNumber = widgetNumber;
		numberOfWorkersWhoHaveWorkedOnThisWidget = 0;
		workersThatWorkedOnWidget = new ArrayList<Worker>();
	}

	public String getName() {
		return name;
	}

	public int getWidgetNumber() {
		return widgetNumber;
	}
	
	public void workUpon(Worker worker){
		numberOfWorkersWhoHaveWorkedOnThisWidget++;
		workersThatWorkedOnWidget.add(worker);
	}
	
	public ArrayList<Worker>getWorkersThatWorkedOnWidget(){
		return workersThatWorkedOnWidget;
	}

}
