package edu.ucam.frames;

import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class Loading extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7674268369389941438L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public Loading(String message) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 257, 103);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMessage = new JLabel("");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setText(message);
		lblMessage.setBounds(10, 25, 237, 14);
		contentPane.add(lblMessage);
		
		JLabel lblConnecting = new JLabel("");
		ImageIcon fot = new ImageIcon(System.getProperty("user.dir") + "\\gifs\\sonic_running_2.gif");
		Icon icono = new ImageIcon(fot.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		lblConnecting.setIcon(icono);
		
		
		lblConnecting.setBounds(108, 52, 40, 40);
		contentPane.add(lblConnecting);
	}

}
