package edu.ucam.client;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import edu.ucam.frames.Loading;
import edu.ucam.frames.LoginUser;
import edu.ucam.frames.UIFrame;
import sauras.people.User;

public class Thread_client_commands extends Thread{

	//Commands
	final static String login = "LOGIN";
	
	//Frames
	LoginUser loginUser;
	UIFrame ui; //User interface
	
	//Atributes
	Thread_client_commands thisThread = this;
	Boolean paused, suspended;
	static String ip = "localhost";
	final static Integer port = 2000;
	static Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	
	User user;
	
	//Constructors
	public Thread_client_commands() {
		
		this.paused = this.suspended = false;
		getIp();
		
		if(ip != null) {
			loginUser = new LoginUser(output,this);
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {	
						
						loginUser.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	
	//Methods
	public void run() {
				
		String message = null;
		
		//Thread control
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
			
			//Receive message
			try {
				message = readMessage();
			}
			catch(Exception e){
				
			}
			if(message != null)
				checkCommands(message.split(" "));
			else {
				JOptionPane.showMessageDialog(null, "Error al recibir mensaje del servidor.");
				setSuspended(true);
			}
			
		}
		
	}

	public void getIp(){
		
		try {
			do {
				ip = JOptionPane.showInputDialog("Introduce la IP del servidor al que se va a conectar: ");
			}while(!checkIp());
		}
		catch(Exception t) {
			JOptionPane.showMessageDialog(null,"La conexión del cliente ha sido cancelada.","Cancel", JOptionPane.INFORMATION_MESSAGE);
			ip = null;
		}
		
		return;
	}
	
	public void setBridge() {
		
		try {
			this.setOutput(new DataOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"" + "\n" + e.getMessage(), /*Title*/ "", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
			
		try {
			this.setInput(new DataInputStream(socket.getInputStream()));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, ""+ "\n" + e.getMessage(), /*Title*/ "", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} 
				
		return;
	}
	
	public Boolean checkIp(){
		
		//Checking compatibility
		String[] splitedIp = ip.split("\\.");
		
		if(ip.equals("localhost"))
			System.out.println("localhostmode");
		
		else if(splitedIp.length != 4) {
			JOptionPane.showMessageDialog(null,"El texto introducido no corresponde a una IP válida.","Field error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		Integer i = 0;
		
		if(!ip.equals("localhost"))
		for(String s: splitedIp) {
			Integer checkSplitedIp = Integer.parseInt(s);
			if(checkSplitedIp < 1 || checkSplitedIp > 254) {
				JOptionPane.showMessageDialog(null,"El split " + i + " introducido no corresponde a una IP válida.","Field error", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			i++;	
		}
		
		//Checking connection
		Loading loadingFrame = new Loading("Conectando con el servidor");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {	
					loadingFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		try {
			socket = new Socket(ip,port);
			setBridge();
			loadingFrame.dispose();
			System.out.println("Conectado con el servidor");
		} catch (UnknownHostException e) {
			loadingFrame.dispose();
			JOptionPane.showMessageDialog(null,e.getMessage() + "\n Try again.","Connection error", JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (IOException e) {
			loadingFrame.dispose();
			JOptionPane.showMessageDialog(null,e.getMessage() + "\n Try again.","Connection error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}

	void checkCommands(String[] splitedMessage) {
		
		switch(splitedMessage[0]/*Command*/) {
			case "LOGED":
				login();
				break;
				
			case "SIGNED":
				register(splitedMessage);
				break;
				
			case "LOGIN_ERROR":
				loginError();
				break;
			
			case "ALREADY_SIGNED_EMAIL":
				alreadySigned(splitedMessage);
				break;
				
			case "REGISTER_ERROR":
				registrationError();
				break;
				
			case "CANCELED":
				cancelConnection();
				break;
		}
		
		return;
	}
	
		
	private String readMessage() {
			
		String message = null;
			
		try {
			message = this.getInput().readUTF();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Se ha perdido la conexión con el servidor.\n" + e.getMessage(), /*Title*/"", JOptionPane.ERROR_MESSAGE);
			setSuspended(true);
			loginUser.dispose();
			e.printStackTrace();
		}
			
		return message;
	}
	
	private void login() {
		
		JOptionPane.showMessageDialog(null, "You have logged succesfully.", /*Title*/"Log in", JOptionPane.INFORMATION_MESSAGE);
		this.loginUser.dispose();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ui = new UIFrame(output, thisThread);
					ui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		return;
	}
	
	private void register(String[] splitedMessage) {
		
		try {
			this.user = new User(splitedMessage[1], splitedMessage[2], null);
			JOptionPane.showMessageDialog(null, "You have registered succesfully.", /*Title*/"Sign in", JOptionPane.INFORMATION_MESSAGE);
			this.loginUser.setVisible(true);
			this.loginUser.getRegisterUser().dispose();
		}
		catch(Exception t) {
			JOptionPane.showMessageDialog(null, "Error recieving sign in confirmation from server", /*Title*/"Sign in", JOptionPane.INFORMATION_MESSAGE);
		}
		
		return;
	}
	
	private void loginError() {
		
		JOptionPane.showMessageDialog(null, "Error to login, check the data of the fields.", /*Title*/"Log in error", JOptionPane.ERROR_MESSAGE);

		return;
	}
	
	private void alreadySigned(String[] splitedMessage) {
		
		JOptionPane.showMessageDialog(null, "The email " + splitedMessage[1] + " was registered yet. Please, try with other one.", /*Title*/"Sign in error", JOptionPane.ERROR_MESSAGE);
		
		return;
	}
	
	private void registrationError() {
		
		this.loginUser.setVisible(true);
		this.loginUser.getRegisterUser().dispose();
		
		JOptionPane.showMessageDialog(null, "Error to sign up, check the data of the fields or your connection.", /*Title*/"Sign in error", JOptionPane.ERROR_MESSAGE);

		return;
	}
	
	private void cancelConnection() {
		
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
	
	
	
	
}
