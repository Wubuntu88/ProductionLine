/**
 * 
 * @author William Gillespie
 * Course: Computer Operating Systems
 * Date: 2015-05-30
 *
 */
public class Worker extends Thread{
	
	private boolean isWorking;
	private boolean isWaiting;
	private boolean isIdle;
	
	public void makeIsWorking(){
		setWaiting(false);
		setWorking(true);
		setIdle(false);
	}
	
	public void makeIsWaiting(){
		setWaiting(true);
		setWorking(false);
		setIdle(false);
	}
	
	public void makeIsIdle(){
		setWaiting(false);
		setWorking(false);
		setIdle(true);
	}

	public boolean isWorking() {
		return isWorking;
	}

	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}

	public boolean isWaiting() {
		return isWaiting;
	}

	public void setWaiting(boolean isWaiting) {
		this.isWaiting = isWaiting;
	}

	public boolean isIdle() {
		return isIdle;
	}

	public void setIdle(boolean isIdle) {
		this.isIdle = isIdle;
	}

}
