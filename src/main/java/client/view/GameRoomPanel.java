package client.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import action.Action;

import client.model.GameRoom;

public class GameRoomPanel extends ContentPanel
{
	private JLabel title;
	private JPanel gamePanel;
	private ChatPanel chatPanel;
	private JButton start;
	private JButton leave;
	private JPanel buttonPanel;
	private JPanel infoPanel;
	private JTextArea userInfo;
	
	public GameRoomPanel(Integer width, Integer height)
	{
		super(width, height);
		this.chatPanel = new ChatPanel();
		this.gamePanel = new JPanel();
		
		// set bounds of panels
		this.leftPanel.setSize(width / 4, height);
		this.leftPanel.setLocation(0, 0);		
		this.rightPanel.setSize(3 * width / 4, height);
		this.rightPanel.setLocation((width / 4), 0);
		
		// construct button panel
		this.start = new JButton("Start Game");
		this.leave = new JButton("Leave Room");
		
		this.buttonPanel = new JPanel();
		this.buttonPanel.setLayout(new GridLayout(0,1));
		this.buttonPanel.add(start);
		this.buttonPanel.add(leave);
		
		// construct info panel
		this.title = new JLabel();
		
		this.userInfo = new JTextArea();
		this.userInfo.setEditable(false);
		
		this.infoPanel = new JPanel();
		this.infoPanel.setLayout(new BorderLayout());
		this.infoPanel.add(title, BorderLayout.NORTH);
		this.infoPanel.add(userInfo, BorderLayout.CENTER);
		
		// construct left panel
		this.leftPanel.setLayout(new BorderLayout());
		this.leftPanel.add(buttonPanel, BorderLayout.NORTH);
		this.leftPanel.add(infoPanel, BorderLayout.CENTER);
		
		// construct right panel
		this.rightPanel.setLayout(new BorderLayout());
		this.rightPanel.add(chatPanel, BorderLayout.SOUTH);
		this.rightPanel.add(gamePanel, BorderLayout.CENTER);
		
		this.add(leftPanel);
		this.add(rightPanel);
		
		// set action commands
		this.start.setActionCommand(Action.START_GAME + "?");
		this.leave.setActionCommand(Action.EXIT_ROOM + "?");
	}
	
	@Override
	public void refreshPanel()
	{
		this.setVisible(false);
		
		this.removeAll();
		
		this.add(leftPanel);
		this.add(rightPanel);
		
		this.setVisible(true);
	}

	public void update(GameRoom room)
	{
		String list = new String();
		Iterator<String> iter = room.getPlayers().iterator();
		
		while(iter.hasNext())
		{
			list += iter.next() + "\n";
		}
		
		this.userInfo.setText(list);
	}

	public JPanel getGamePanel()
	{
		return gamePanel;
	}

	public void setGamePanel(JPanel gamePanel)
	{
		this.gamePanel = gamePanel;
	}
	
	public void updateGamePanel(JPanel gamePanel)
	{
		this.rightPanel.remove(this.gamePanel);
		this.setGamePanel(gamePanel);
		this.rightPanel.add(this.gamePanel, BorderLayout.CENTER);
	}
	
	public void resetPanel()
	{
		// reset game room panel
		this.userInfo.setText(null); // reset user list
		this.chatPanel.getChatArea().setText(null); // reset chat content
		this.updateGamePanel(new JPanel()); // reset game panel
	}

	public ChatPanel getChatPanel()
	{
		return chatPanel;
	}

	public void addChatContent(String username, String chatContent)
	{
		// add chat content to the chat panel
		String chatStr = username + ": " + chatContent + "\n";
		this.chatPanel.getChatArea().append(chatStr);
	}
	
	public void setTitleText(String title)
	{
		this.title.setText(title);
	}
}
