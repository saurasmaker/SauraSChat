package edu.ucam.frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import edu.ucam.client.Thread_client_commands;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.DataOutputStream;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionEvent;

public class RegisterUser extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7936085335488851390L;
	
	//Atributes
	private JPanel contentPane;
	private JTextField textFieldUserName;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldRepeat;
	private JTextField textFieldEmail;
	
	private Thread_client_commands threadClientCommands;
	private DataOutputStream output;
	private LoginUser loginUser;
	private Integer xMouse, yMouse;


	//Contructor
	public RegisterUser(DataOutputStream output, Thread_client_commands threadClientCommands, LoginUser loginUser) {
		this.setLoginUser(loginUser);
		this.setThreadClientCommands(threadClientCommands);
		this.setOutput(output);
		this.setBounds(100, 100, 316, 418);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelHead = new JPanel();
		panelHead.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
					Point point = MouseInfo.getPointerInfo().getLocation();
					setLocation(point.x - xMouse, point.y - yMouse);
			}
		});
		panelHead.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xMouse = e.getX();
	            yMouse = e.getY();  
			}
		});
		panelHead.setBounds(0, 0, 316, 21);
		contentPane.add(panelHead);
		
		JLabel labelIcon = new JLabel(" Icon");
		GroupLayout gl_panelHead = new GroupLayout(panelHead);
		gl_panelHead.setHorizontalGroup(
			gl_panelHead.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panelHead.createSequentialGroup()
					.addComponent(labelIcon)
					.addContainerGap(319, Short.MAX_VALUE))
		);
		gl_panelHead.setVerticalGroup(
			gl_panelHead.createParallelGroup(Alignment.LEADING)
				.addComponent(labelIcon, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
		);
		panelHead.setLayout(gl_panelHead);
		
		JLabel labelRegister = new JLabel("Register");
		labelRegister.setHorizontalAlignment(SwingConstants.CENTER);
		labelRegister.setFont(new Font("Tahoma", Font.BOLD, 18));
		labelRegister.setBounds(40, 50, 237, 22);
		contentPane.add(labelRegister);
		
		JLabel labelUserName = new JLabel("User name:");
		labelUserName.setBounds(40, 134, 237, 14);
		contentPane.add(labelUserName);
		
		textFieldUserName = new JTextField();
		textFieldUserName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					passwordField.requestFocus();
			}
		});
		textFieldUserName.setColumns(10);
		textFieldUserName.setBounds(40, 154, 237, 20);
		contentPane.add(textFieldUserName);
		
		JLabel labelPassword = new JLabel("Password:");
		labelPassword.setBounds(40, 185, 237, 14);
		contentPane.add(labelPassword);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					passwordFieldRepeat.requestFocus();
			}
		});
		passwordField.setBounds(40, 205, 237, 20);
		contentPane.add(passwordField);
		
		JCheckBox chckbxShowPassword = new JCheckBox("Show password.");
		chckbxShowPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(chckbxShowPassword.isSelected()) {
					passwordField.setEchoChar((char) 0);
					passwordFieldRepeat.setEchoChar((char) 0);
				}
				
				else {
					passwordField.setEchoChar((char)8226);
					passwordFieldRepeat.setEchoChar((char) 8226);
				}
			}
		});
		chckbxShowPassword.setBounds(40, 232, 237, 23);
		contentPane.add(chckbxShowPassword);
		
		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getLoginUser().setVisible(true);
				dispose();
			}
		});
		buttonCancel.setBounds(10, 384, 78, 23);
		contentPane.add(buttonCancel);
		
		JButton buttonAccept = new JButton("Accept");
		buttonAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendData();
			}
		});
		buttonAccept.setBounds(228, 384, 78, 23);
		contentPane.add(buttonAccept);
		
		JLabel lblRepeatPassword = new JLabel("Repeat password:");
		lblRepeatPassword.setBounds(40, 262, 237, 14);
		contentPane.add(lblRepeatPassword);
		
		passwordFieldRepeat = new JPasswordField();
		passwordFieldRepeat.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					sendData();
			}
		});
		passwordFieldRepeat.setBounds(40, 282, 237, 20);
		contentPane.add(passwordFieldRepeat);
		
		textFieldEmail = new JTextField();
		textFieldEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					textFieldUserName.requestFocus();
			}
		});
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(40, 103, 237, 20);
		contentPane.add(textFieldEmail);
		
		JLabel lblEmailAddress = new JLabel("Email address:");
		lblEmailAddress.setBounds(40, 83, 237, 14);
		contentPane.add(lblEmailAddress);
	}
	
	
	//Methods
	void sendData() {

		if(checkData())
			try {
				this.output.writeUTF("SIGNIN " + this.textFieldEmail.getText() + " " + this.textFieldUserName.getText() + " " + String.valueOf(this.passwordField.getPassword()));
			}
			catch(Exception t) {
			}	
		
		return;
	}

	
	Boolean checkData() {
			
		if(textFieldEmail.getText().compareTo("")==0) {
			JOptionPane.showMessageDialog(null,"You must complete email field.","Field error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else if(textFieldUserName.getText().compareTo("")==0) {
			JOptionPane.showMessageDialog(null,"You must complete user name field.","Field error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else if(String.valueOf(passwordField.getPassword()).compareTo("")==0) {
			JOptionPane.showMessageDialog(null,"You must complete password field.","Field error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else if(String.valueOf(passwordFieldRepeat.getPassword()).compareTo("")==0) {
			JOptionPane.showMessageDialog(null,"You must complete password field.","Field error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
			
		String[] spaces = textFieldUserName.getText().split(" ");
		if(spaces.length!=1) {
			JOptionPane.showMessageDialog(null,"Spaces are not allowed in User name field.","Field error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		if(String.valueOf(passwordField.getPassword()).compareTo(String.copyValueOf(passwordFieldRepeat.getPassword()))!=0){
			JOptionPane.showMessageDialog(null,"The password fields must coincide","Password error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		if(!isValidEmailAddress(textFieldEmail.getText())) {
			JOptionPane.showMessageDialog(null,"The email must be valid","Email error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	public static boolean isValidEmailAddress(String email) {
		try {
			InternetAddress emailAddr = new InternetAddress(email);
		    emailAddr.validate();
		} catch (AddressException ex) {
			return false;
		}
		return true;
	}

	
	//Getters & Setters
	public Thread_client_commands getThreadClientCommands() {
		return threadClientCommands;
	}
	public void setThreadClientCommands(Thread_client_commands threadClientCommands) {
		this.threadClientCommands = threadClientCommands;
	}
	public DataOutputStream getOutput() {
		return output;
	}
	public void setOutput(DataOutputStream output) {
		this.output = output;
	}
	public Integer getyMouse() {
		return yMouse;
	}
	public void setyMouse(Integer yMouse) {
		this.yMouse = yMouse;
	}
	public Integer getxMouse() {
		return xMouse;
	}
	public void setxMouse(Integer xMouse) {
		this.xMouse = xMouse;
	}


	public LoginUser getLoginUser() {
		return loginUser;
	}


	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}

}
