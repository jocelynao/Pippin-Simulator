package project;

import javax.swing.Timer;

public class StepControl {
	
	private static final int TICK = 500;
	boolean autoStepOn = false;
	Timer timer;
	GUIMediator gui;
	
	public StepControl(GUIMediator gui){
		this.gui = gui;
	}
	
	public void setAutoStepOn(boolean autoStepOn){
		this.autoStepOn = autoStepOn;
	}
	
	public boolean isAutoStepOn(){
		return autoStepOn;
	}
	
	void toggleAutoStep(){
		if(autoStepOn == false){
			autoStepOn = true;
		}
		else{
			autoStepOn = false;
		}
	}
	
	void setPeriod(int period){
		timer.setDelay(period);
	}
	
	public void start() {
		timer = new Timer(TICK, e -> {if(autoStepOn) gui.step();});
		timer.start();
	}	
	
}
