package edu.ucam.frames;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import java.beans.PropertyChangeListener;
import java.io.DataOutputStream;
import java.util.Map;
import java.beans.PropertyChangeEvent;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.font.TextAttribute;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

import edu.ucam.client.Thread_client_commands;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author saura
 *
 */
public class LoginUser extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8294705280785443141L;
	
	//Atributes
	private JPanel contentPane;
	private JTextField textFieldUserName;
	private JPasswordField passwordField;
	
	private Thread_client_commands threadClientCommands;
	private DataOutputStream output;
	private Integer xMouse, yMouse;
	
	private RegisterUser registerUser;

	//Constructors
	public LoginUser(DataOutputStream output, Thread_client_commands threadClientCommands) {
		this.setThreadClientCommands(threadClientCommands);
		this.setOutput(output);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 314, 397);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		
		/*
		ComponentResizer cr = new ComponentResizer();
		cr.registerComponent(this);
		cr.setMinimumSize(this.getSize());
        cr.setSnapSize(new Dimension(1, 1));
		*/
		
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(this.contentPane);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(35, 44, 237, 22);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblUserName = new JLabel("User name:");
		lblUserName.setBounds(35, 84, 237, 14);
		
		this.textFieldUserName = new JTextField();
		textFieldUserName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					passwordField.requestFocus();
			}
		});
		this.textFieldUserName.setBounds(35, 104, 237, 20);
		this.textFieldUserName.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(35, 142, 237, 14);
		
		this.passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					sendData();
			}
		});
		this.passwordField.setBounds(35, 162, 237, 20);
		

		
		JCheckBox chckbxShowPassword = new JCheckBox("Show password.");
		chckbxShowPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(chckbxShowPassword.isSelected())
					passwordField.setEchoChar((char) 0);
				
				else
					passwordField.setEchoChar((char)8226);
			}
		});
		chckbxShowPassword.setBounds(35, 184, 237, 23);
		chckbxShowPassword.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				

			}
		});
		
		JLabel lblIfYouDont = new JLabel("*If you don't have an account, please,");
		lblIfYouDont.setBounds(35, 233, 269, 14);
		
		JLabel lblNewLabel = new JLabel("create it to access to the content");
		lblNewLabel.setBounds(39, 253, 199, 14);
		
		JLabel lblHere = new JLabel("here.");
		lblHere.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblHere.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblHere.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				lblHere.setForeground(Color.RED);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				lblHere.setForeground(Color.BLUE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				openRegisterFrame();
			}
		});
		lblHere.setBounds(86, 273, 35, 14);
		lblHere.setForeground(Color.BLUE);
		Font font = lblHere.getFont();
		@SuppressWarnings("unchecked")
		Map<TextAttribute, Integer> attributes = (Map<TextAttribute, Integer>) font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblHere.setFont(font.deriveFont(attributes));
		
		JPanel panelHead = new JPanel();
		panelHead.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelHead.setBounds(0, 0, 314, 21);
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
		
		JLabel lblIcon = new JLabel(" Icon");
		GroupLayout gl_panelHead = new GroupLayout(panelHead);
		gl_panelHead.setHorizontalGroup(
			gl_panelHead.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelHead.createSequentialGroup()
					.addComponent(lblIcon)
					.addPreferredGap(ComponentPlacement.RELATED, 192, Short.MAX_VALUE))
		);
		gl_panelHead.setVerticalGroup(
			gl_panelHead.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelHead.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblIcon, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
		);
		panelHead.setLayout(gl_panelHead);
		contentPane.setLayout(null);
		contentPane.add(lblNewLabel);
		contentPane.add(lblHere);
		contentPane.add(lblIfYouDont);
		contentPane.add(chckbxShowPassword);
		contentPane.add(passwordField);
		contentPane.add(lblPassword);
		contentPane.add(lblLogin);
		contentPane.add(textFieldUserName);
		contentPane.add(lblUserName);
		contentPane.add(panelHead);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(10, 363, 78, 23);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelLogin();
				dispose();
			}
		});
		contentPane.add(btnCancel);
		
		JButton btnAccept = new JButton("Accept");
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendData();
			}
		});
		btnAccept.setBounds(226, 363, 78, 23);
		contentPane.add(btnAccept);
		
		JLabel lblNewLabel_1 = new JLabel("clicking");
		lblNewLabel_1.setBounds(39, 273, 48, 14);
		contentPane.add(lblNewLabel_1);
		
	}
	
	//Methods
	void sendData() {
		
		if(checkData()) {
			try {
				this.output.writeUTF("LOGIN " + this.textFieldUserName.getText() + " " + String.valueOf(this.passwordField.getPassword()));
				System.out.println("Solicitud de login enviada con los datos: " + "LOGIN " + this.textFieldUserName.getText() + " " + String.valueOf(this.passwordField.getPassword()));
			}
			catch(Exception t) {
			
			}
		}
		
		return;
	}
	
	void cancelLogin() {
		try {
			this.output.writeUTF("CANCEL");
			threadClientCommands.setSuspended(true);
			System.out.println("Solicitud de cancel enviada...");
		}
		catch(Exception t) {
		
		}
	}
	
	Boolean checkData() {
				
		if(textFieldUserName.getText().compareTo("")==0) {
			JOptionPane.showMessageDialog(null,"You must complete user name field.","Field error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		else if(String.valueOf(passwordField.getPassword()).compareTo("")==0) {
			JOptionPane.showMessageDialog(null,"You must complete password field.","Field error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
			
		String[] spaces = textFieldUserName.getText().split(" ");
		if(spaces.length!=1) {
			JOptionPane.showMessageDialog(null,"Spaces are not allowed in User name field.","Field error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	void openRegisterFrame() {
		
		this.setVisible(false);
		
		registerUser = new RegisterUser(this.output, this.threadClientCommands, this);
		registerUser.setVisible(true);
		
		return;
	}

	
	//Getters & Setters
	public DataOutputStream getOutput() {
		return output;
	}

	public void setOutput(DataOutputStream output) {
		this.output = output;
	}

	public Thread_client_commands getThreadClientCommands() {
		return threadClientCommands;
	}

	public void setThreadClientCommands(Thread_client_commands threadClientCommands) {
		this.threadClientCommands = threadClientCommands;
	}

	public RegisterUser getRegisterUser() {
		return registerUser;
	}

	public void setRegisterUser(RegisterUser registerUser) {
		this.registerUser = registerUser;
	}
	
	
}
