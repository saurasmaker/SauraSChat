package edu.ucam.frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.DataOutputStream;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import edu.ucam.client.Thread_client_commands;
import edu.ucam.extra_classes.ComponentResizer;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.border.EtchedBorder;

public class UIFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1209560589518141067L;

	//Atributes
	private JPanel contentPane;
	
	private Thread_client_commands threadClientCommands;
	private DataOutputStream output;
	private Integer xMouse, yMouse;
	private JTextField textFieldMessage;
	
	//Constructors
	public UIFrame(DataOutputStream output, Thread_client_commands threadClientCommands) {
		this.setThreadClientCommands(threadClientCommands);
		this.setOutput(output);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 720, 480);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		
		ComponentResizer cr = new ComponentResizer();
		cr.registerComponent(this);
		cr.setMinimumSize(this.getSize());
        cr.setSnapSize(new Dimension(10, 10));
		
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		
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
		
		JPanel panelMenu = new JPanel();
		panelMenu.setBackground(new Color(255, 255, 255));
		
		JButton buttonProfile = new JButton("  Profile  ");
		buttonProfile.setForeground(new Color(0, 0, 0));
		buttonProfile.setBorder(null);
		buttonProfile.setBackground(new Color(255, 255, 255));
		
		JButton buttonTheme = new JButton("  Theme  ");
		buttonTheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonTheme.setForeground(new Color(0, 0, 0));
		buttonTheme.setBorder(null);
		buttonTheme.setBackground(new Color(255, 255, 255));
		
		JButton buttonHelp = new JButton("  Help  ");
		buttonHelp.setForeground(new Color(0, 0, 0));
		buttonHelp.setBorder(null);
		buttonHelp.setBackground(new Color(255, 255, 255));
		GroupLayout gl_panelMenu = new GroupLayout(panelMenu);
		gl_panelMenu.setHorizontalGroup(
			gl_panelMenu.createParallelGroup(Alignment.LEADING)
				.addGap(0, 534, Short.MAX_VALUE)
				.addGroup(gl_panelMenu.createSequentialGroup()
					.addComponent(buttonProfile)
					.addGap(2)
					.addComponent(buttonTheme)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(buttonHelp)
					.addGap(646))
		);
		gl_panelMenu.setVerticalGroup(
			gl_panelMenu.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 20, Short.MAX_VALUE)
				.addGroup(gl_panelMenu.createSequentialGroup()
					.addGroup(gl_panelMenu.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonProfile, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
						.addComponent(buttonTheme, GroupLayout.PREFERRED_SIZE, 20, Short.MAX_VALUE)
						.addComponent(buttonHelp, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(0))
		);
		panelMenu.setLayout(gl_panelMenu);
		
		JPanel panelFoot = new JPanel();
		
		JLabel labelIcon = new JLabel("  Icon");
		
		JLabel labelMin = new JLabel("_");
		labelMin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)
					setExtendedState(JFrame.ICONIFIED);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				labelMin.setBorder(BorderFactory.createRaisedBevelBorder());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				labelMin.setBorder(null);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				labelMin.setBorder(BorderFactory.createLoweredBevelBorder());
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				labelMin.setBorder(null);
			}
		});
		labelMin.setHorizontalAlignment(SwingConstants.CENTER);
		labelMin.setForeground(Color.BLACK);
		
		JLabel labelMax = new JLabel("\u25A2");
		labelMax.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1){
					if(getExtendedState() == JFrame.MAXIMIZED_BOTH)
						setExtendedState(JFrame.NORMAL);
					else
						setExtendedState(JFrame.MAXIMIZED_BOTH);
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				labelMax.setBorder(BorderFactory.createRaisedBevelBorder());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				labelMax.setBorder(null);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				labelMax.setBorder(BorderFactory.createLoweredBevelBorder());
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				labelMax.setBorder(null);
			}
		});
		labelMax.setHorizontalAlignment(SwingConstants.CENTER);
		labelMax.setForeground(Color.BLACK);
		labelMax.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel labelClose = new JLabel("X");
		labelClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				close();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				labelClose.setBorder(BorderFactory.createRaisedBevelBorder());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				labelClose.setBorder(null);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				labelClose.setBorder(BorderFactory.createLoweredBevelBorder());
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				labelClose.setBorder(null);
			}
		});
		labelClose.setHorizontalAlignment(SwingConstants.CENTER);
		labelClose.setForeground(Color.BLACK);
		labelClose.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout gl_panelHead = new GroupLayout(panelHead);
		gl_panelHead.setHorizontalGroup(
			gl_panelHead.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelHead.createSequentialGroup()
					.addComponent(labelIcon, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 320, Short.MAX_VALUE)
					.addComponent(labelMin, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(labelMax, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(labelClose, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
		);
		gl_panelHead.setVerticalGroup(
			gl_panelHead.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelHead.createSequentialGroup()
					.addGroup(gl_panelHead.createParallelGroup(Alignment.LEADING, false)
						.addComponent(labelIcon, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
						.addComponent(labelClose, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
						.addComponent(labelMax, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
						.addComponent(labelMin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		panelHead.setLayout(gl_panelHead);
		
		JSplitPane splitPaneGeneral = new JSplitPane();
		splitPaneGeneral.setDividerSize(3);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(panelHead, GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
				.addComponent(panelMenu, GroupLayout.PREFERRED_SIZE, 718, Short.MAX_VALUE)
				.addComponent(panelFoot, GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
				.addComponent(splitPaneGeneral, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panelHead, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(panelMenu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(splitPaneGeneral, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelFoot, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
		);
		
		JSplitPane splitPaneLeft = new JSplitPane();
		splitPaneLeft.setDividerSize(3);
		splitPaneGeneral.setLeftComponent(splitPaneLeft);
		
		JPanel panelLeft = new JPanel();
		panelLeft.setBackground(Color.WHITE);
		panelLeft.setBorder(null);
		splitPaneLeft.setLeftComponent(panelLeft);
		
		JTree tree = new JTree();
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Contacts") {
				{
					add(new DefaultMutableTreeNode("Friends"));
					add(new DefaultMutableTreeNode("Groups"));
				}
			}
		));
		tree.setBorder(null);
		GroupLayout gl_panelLeft = new GroupLayout(panelLeft);
		gl_panelLeft.setHorizontalGroup(
			gl_panelLeft.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelLeft.createSequentialGroup()
					.addContainerGap()
					.addComponent(tree, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
		);
		gl_panelLeft.setVerticalGroup(
			gl_panelLeft.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelLeft.createSequentialGroup()
					.addContainerGap()
					.addComponent(tree, GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE))
		);
		panelLeft.setLayout(gl_panelLeft);
		
		JSplitPane splitPaneCenter = new JSplitPane();
		splitPaneCenter.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPaneCenter.setDividerSize(3);
		splitPaneLeft.setRightComponent(splitPaneCenter);
		
		JPanel panelCenter = new JPanel();
		panelCenter.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Chat", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		splitPaneCenter.setLeftComponent(panelCenter);
		
		textFieldMessage = new JTextField();
		textFieldMessage.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		
		JButton btnEmojis = new JButton("Emojis");
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panelCenter = new GroupLayout(panelCenter);
		gl_panelCenter.setHorizontalGroup(
			gl_panelCenter.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelCenter.createSequentialGroup()
					.addComponent(textFieldMessage, GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnEmojis)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSend))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
		);
		gl_panelCenter.setVerticalGroup(
			gl_panelCenter.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCenter.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelCenter.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldMessage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSend)
						.addComponent(btnEmojis))
					.addGap(3))
		);
		
		JEditorPane editorPaneChat = new JEditorPane();
		scrollPane.setViewportView(editorPaneChat);
		panelCenter.setLayout(gl_panelCenter);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPaneCenter.setRightComponent(tabbedPane);
		splitPaneCenter.setDividerLocation(250);
		splitPaneLeft.setDividerLocation(100);
		
		JPanel panelRight = new JPanel();
		panelRight.setBackground(Color.WHITE);
		splitPaneGeneral.setRightComponent(panelRight);
		splitPaneGeneral.setDividerLocation(620);
		contentPane.setLayout(gl_contentPane);
	}
	
	
	//Methods
	void close() {
		try {
			this.output.writeUTF("CANCEL");
			threadClientCommands.setSuspended(true);
			System.out.println("Solicitud de cancel enviada...");
			dispose();
		}
		catch(Exception t) {
		
		}
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
}
