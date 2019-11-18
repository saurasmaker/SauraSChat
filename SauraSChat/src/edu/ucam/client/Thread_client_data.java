package edu.ucam.client;

public class Thread_client_data extends Thread{

	//Atributes
	Boolean paused, stopped;
	
	//Constructors
	public Thread_client_data() {
		this.paused = this.stopped = false;
	}
	
	
	//Methods
	public void run() {
		
		
		return;
	}


	//Getters & Setters
	public Boolean isPaused() {
		return paused;
	}


	public void setPaused(Boolean paused) {
		this.paused = paused;
	}


	public Boolean isStopped() {
		return stopped;
	}


	public void setStopped(Boolean stopped) {
		this.stopped = stopped;
	}
	
	
	
	
}
