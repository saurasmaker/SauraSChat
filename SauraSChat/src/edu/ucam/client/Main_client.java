package edu.ucam.client;

public class Main_client {

	//Atributes	
	
	
	/*
	 * Este es el main de la aplicación
	 * cliente que se conectará con el 
	 * servidor. 
	 */
	
	
	public static void main(String[] args) {
		
		Thread_client_commands thread_client_commands = new Thread_client_commands();
		thread_client_commands.start();
		
		
	}
	
	
}
