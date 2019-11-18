package edu.ucam.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main_server {

	/*
	 * Este es el main de la aplicación
	 * servidor que estará a la escucha
	 * de los comandos y datos a trasferir. 
	 */
	
	//Atributes
	
	public static void main(String[] args) {
		
		checkUsers();
		
		Thread_server threadServer = new Thread_server();
		threadServer.run();
			
	}

	//Methods
	static void checkUsers() {
		
		File file = new File(System.getProperty("user.dir") + "\\users");
		
		if(!file.exists())
			file.mkdir(); 
		
		file = new File(System.getProperty("user.dir") + "\\users\\users");
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		
		return;
	}
}
