package client.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import view.UserInterface;

public class ClientGUI implements UserInterface
{
	// singleton
	private static ClientGUI instance = null;
	
	// main frame dimensions
	private static final int frame_width = 1024;
	private static final int frame_height = 3 * frame_width / 4;
	
	// main window components	
	private JFrame mainFrame;
	private JPanel mainPanel;
	private ImageIcon windowIcon;
	
	private ClientMenuBar menuBar;
	
	private LoginPanel loginPanel;
	private GameSelectPanel gameSelectPanel;
	private GameHallPanel gameHallPanel;
	private GameRoomPanel gameRoomPanel;
	
	private ClientGUI()
	{
		super();
	}
	
	public static ClientGUI getInstance()
	{
		if(instance == null)
		{
			instance = new ClientGUI();
			instance.init();
		}
			
		return instance;
	}
	
	private void init()
	{
		int width, height;
		
		// init main panel
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		
		// init menu bar
		menuBar = new ClientMenuBar();
		
		// create icon
		windowIcon = new ImageIcon("resource/icon.gif");
		
		// init main frame
		mainFrame = new JFrame("Client Application");
		mainFrame.setSize(frame_width, frame_height);
		mainFrame.setJMenuBar(menuBar);
		mainFrame.setContentPane(mainPanel);
		mainFrame.setIconImage(windowIcon.getImage());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		
		width = mainPanel.getBounds().width;
		height = mainPanel.getBounds().height;
		
		// construct login panel
		loginPanel = new LoginPanel(width, height);
		loginPanel.setLocation(0, 0);
		loginPanel.setVisible(true);
		
		// construct game selection panel
		gameSelectPanel = new GameSelectPanel(width, height);
		gameSelectPanel.setLocation(0, 0);
		gameSelectPanel.setVisible(false);
		
		//construct game hall panel
		gameHallPanel = new GameHallPanel(width, height);
		gameHallPanel.setLocation(0, 0);
		gameHallPanel.setVisible(false);
		
		//construct game room panel
		gameRoomPanel = new GameRoomPanel(width, height);
		gameRoomPanel.setLocation(0, 0);
		gameRoomPanel.setVisible(false);

		// add panels to main panel
		mainPanel.setVisible(false);
		mainPanel.add(loginPanel);
		mainPanel.add(gameSelectPanel);
		mainPanel.add(gameHallPanel);
		mainPanel.add(gameRoomPanel);
		mainPanel.setVisible(true);
	}
	
	public void switchTo(ContentPanel panel)
	{
		if(panel == null)
			return;
		
		loginPanel.setVisible(false);
		gameHallPanel.setVisible(false);
		gameSelectPanel.setVisible(false);
		gameRoomPanel.setVisible(false);
		
		panel.setVisible(true);
	}
	
	public LoginPanel getLoginPanel()
	{
		return loginPanel;
	}

	public GameSelectPanel getGameSelectPanel()
	{
		return gameSelectPanel;
	}
	
	public GameHallPanel getGameHallPanel()
	{
		return gameHallPanel;
	}

	public GameRoomPanel getGameRoomPanel()
	{
		return gameRoomPanel;
	}
	
	public void showInfo(String infoTitle, String infoMsg)
	{
	    JOptionPane.showMessageDialog(mainFrame, infoMsg, infoTitle, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showError(String errorTitle, String errorMsg)
	{
		JOptionPane.showMessageDialog(mainFrame, errorMsg, errorTitle, JOptionPane.ERROR_MESSAGE);
	}
}
