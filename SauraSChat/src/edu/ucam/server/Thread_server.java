package edu.ucam.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Thread_server extends Thread{
	
	//
	private Integer port = 2000;

	//Atributes
	private Boolean paused, stopped;
    private ServerSocket serverSocket;
    private ArrayList<Thread_server_commands> threadsServerCommands;
    private ArrayList<Thread_server_data> threadsServerData;
	
	//Constructors
	public Thread_server() {
		this.paused = this.stopped = false;
		threadsServerCommands = new ArrayList<Thread_server_commands>();
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
		
	//Methods
	public void run() {
			
		while(true) {
			try {
				threadsServerCommands.add(new Thread_server_commands(serverSocket.accept()));
				System.out.println("Nueva conexión establecida.");
				threadsServerCommands.get(threadsServerCommands.size()-1).run();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
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

	public ServerSocket getServerSocket() {
		return serverSocket;
	}


	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}


	public ArrayList<Thread_server_commands> getThreadsServerCommands() {
		return threadsServerCommands;
	}


	public void setThreadsServerCommands(ArrayList<Thread_server_commands> threadsServerCommands) {
		this.threadsServerCommands = threadsServerCommands;
	}


	public ArrayList<Thread_server_data> getThreadsServerData() {
		return threadsServerData;
	}


	public void setThreadsServerData(ArrayList<Thread_server_data> threadsServerData) {
		this.threadsServerData = threadsServerData;
	}


	public Boolean getPaused() {
		return paused;
	}


	public Boolean getStopped() {
		return stopped;
	}
	
	
	
}
