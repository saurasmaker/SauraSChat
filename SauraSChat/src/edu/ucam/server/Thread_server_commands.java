package edu.ucam.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

import org.apache.commons.codec.digest.DigestUtils;

import sauras.people.Admin;

public class Thread_server_commands extends Thread{

	//Atributes
	private String user;
	private Boolean paused, suspended;
	private Socket socket;
	private String[] splitedMessage;
	private DataInputStream input;
	private DataOutputStream output;

	
	//Constructors
	public Thread_server_commands() {
		this.paused = this.suspended = false;
		this.setBridges();
	}
	
	public Thread_server_commands(Socket socket) {
		this.paused = this.suspended = false;
		this.setSocket(socket);
		this.setBridges();
	}
	
	
	//Methods
	public void run() {
		
		while(true){
			try {
				synchronized(this){
					while(this.isPaused()) 
						wait();
					
					if(this.isSuspended())
						break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			splitedMessage = readMessage();
			checkCommands(splitedMessage[0]);
		}
	}


	//Methods
	void setBridges() {
		
		try {
			this.setOutput(new DataOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "" + "\n" + e.getMessage(), /*Title*/ "", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		try {
			this.setInput(new DataInputStream(socket.getInputStream()));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "" + "\n" + e.getMessage(), /*Title*/ "", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} 
			
		return;
	}
	
	private String[] readMessage() {
		
		String message = null;

		try {
			message = this.getInput().readUTF();
			System.out.println("Mensaje recibido: " + message);
			return message.split(" "); 
		} catch (IOException e) {
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			
			return null;
		}
	}
		
		
	void checkCommands(String command) {
				
		switch(command) {
			case "LOGIN":
				checkLogin();
				break;
				
			case "SIGNIN":
				signinUser();
				break;
			
			case "ADD_CONTACT":
				addContact();
				break;
				
			case "CANCEL":
				cancelConnection();
				break;
		}
		
		return;
	}
	
	void checkLogin() {
		
		BufferedReader objReader = null;
		String user = null;
				
		try {
			objReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\users\\users"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}  
		
		try {
			while((user = objReader.readLine()) != null) {
				if(splitedMessage[1].equals(user)) {
					if(checkPass(splitedMessage[1]))
						try {
							getOutput().writeUTF("LOGED");
							return;
						} catch (IOException e) {
							e.printStackTrace();
							break;
						}
				}
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
			
		}

		try {
			getOutput().writeUTF("LOGIN_ERROR");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return;
	}
	
	Boolean checkPass(String user) {
		
		BufferedReader objReader = null;
				
		try {
			objReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\users\\" + user));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}  
		
		try {
			if((user = objReader.readLine()).split(" ")[2].equals(DigestUtils.md5Hex(splitedMessage[2])) )
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	void signinUser() {
		
		FileOutputStream fos = null;
		DataOutputStream salida = null;
		
		String buffer = null;
				
		//Registrar
		if(checkAlreadySigned())
		try {
			
			//Realizamos registro
			fos = new FileOutputStream(System.getProperty("user.dir") + "\\users\\" + splitedMessage[1]);
			salida = new DataOutputStream(fos);
			
			buffer = splitedMessage[1]/*email*/ + " " + splitedMessage[2]/*userName*/ + " " + DigestUtils.md5Hex(splitedMessage[3])/*password*/ + "\n";
			
			salida.write(buffer.getBytes());
			
			//Declaramos registro
			fos = new FileOutputStream(System.getProperty("user.dir") + "\\users\\users", true);
			salida = new DataOutputStream(fos);
			
			salida.write((splitedMessage[1]+"\n").getBytes());
			
			//Confirmamos registro
			System.out.println("REGISTRO REALIZADO CON EXITO");
			getOutput().writeUTF("SIGNED " + splitedMessage[1]/*email*/ + " " + splitedMessage[2]/*userName*/);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return;
	}
	
	void addContact() {
		
		
		
		return;
	}
	
	Boolean checkAlreadySigned() {
		
		File file = new File(System.getProperty("user.dir") + "\\users\\" + splitedMessage[1]);
		
		if(!file.exists())
			return true;
		else {
			try {
				getOutput().writeUTF("ALREADY_SIGNED_EMAIL " + splitedMessage[1]/*email*/ + " " + splitedMessage[2]/*userName*/);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		
	}
	
	String adjustID(Integer id) {
		
		String finalID = "U";
		
		for(int i = Integer.toString(id).length(); i < 4; ++i) {
			finalID += "0";
		}
		
		System.out.println("id a generar 1: " + finalID+id);
		return finalID+=id;
	}
	
	void cancelConnection() {
		
		this.setSuspended(true);
		
		try {
			getOutput().writeUTF("CANCELED");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return;
	}
	
	
	
	//Getters & Setters
	public Boolean isPaused() {
		return paused;
	}


	public void setPaused(Boolean paused) {
		this.paused = paused;
	}


	public Boolean isSuspended() {
		return suspended;
	}


	public void setSuspended(Boolean suspended) {
		this.suspended = suspended;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public DataInputStream getInput() {
		return input;
	}

	public void setInput(DataInputStream input) {
		this.input = input;
	}

	public DataOutputStream getOutput() {
		return output;
	}

	public void setOutput(DataOutputStream output) {
		this.output = output;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}	
	
}
