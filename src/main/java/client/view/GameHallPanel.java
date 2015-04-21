package client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import action.Action;

import client.model.GameRoom;

public class GameHallPanel extends SelectionPanel
{
	private List<RoomInfoPanel> gameRoomList;
	
	private JLabel title;
	private JButton leave;
	private JButton create;
	private JPanel buttonPanel;
	private JPanel infoPanel;
	private JTextArea userInfo;
	
	public GameHallPanel(Integer width, Integer height)
	{
		super(width, height);
		
		title = new JLabel();
		
		// construct button panel
		this.leave = new JButton("Leave Hall");
		this.create = new JButton("Create Room");
		
		this.buttonPanel = new JPanel();
		this.buttonPanel.setLayout(new GridLayout(0,1));
		this.buttonPanel.add(create);
		this.buttonPanel.add(leave);
		
		// construct info panel
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
		
		this.gameRoomList = new ArrayList<RoomInfoPanel>();
		
		// add action commands
		this.leave.setActionCommand(Action.EXIT_HALL + "?");
		this.create.setActionCommand(Action.CREATE_ROOM + "?");
	}
	
	@Override
	public void refreshPanel()
	{
		RoomInfoPanel panel;
		Point location = null;
		Iterator<RoomInfoPanel> iter = this.gameRoomList.iterator();
		double ratio = 0.9;
		
		this.innerPanel.setVisible(false);
		
		this.innerPanel.removeAll();
		
		while(iter.hasNext())
		{
			panel = iter.next();
			location = this.nextLocation(location);
			panel.setLocation(location);
			panel.setSize(this.componentSize);
			panel.refreshPanel();
			this.innerPanel.add(panel);
		}
		
		if(location != null)
		{
			int width = (int)(ratio * this.rightPanel.getSize().width);
			int height = location.y + this.componentSize.height;
						
			this.innerPanel.setPreferredSize(new Dimension(width, height));
		}
		
		this.innerPanel.setVisible(true);
	}

	public void addRoomPanel(GameRoom room)
	{
		RoomInfoPanel panel = new RoomInfoPanel();
		panel.update(room);
		this.gameRoomList.add(panel);
	}
	
	public void updateList(List<String> userList)
	{
		String list = new String();
		Iterator<String> iter = userList.iterator();
		
		while(iter.hasNext())
		{
			list += iter.next() + "\n";
		}
		
		this.userInfo.setText(list);
	}
	
	public void removeRoomPanel(Integer roomNo)
	{
		RoomInfoPanel panel = searchInfoPanel(roomNo);
		
		if(panel != null)
			this.gameRoomList.remove(panel);
	}
	
	public RoomInfoPanel searchInfoPanel(Integer roomNo)
	{
		Iterator<RoomInfoPanel> iter = this.gameRoomList.iterator();
		RoomInfoPanel panel = null;
		
		while(iter.hasNext())
		{
			panel = iter.next();
			
			if(panel.getNumberLabel().getText().equals(RoomInfoPanel.ROOM + roomNo))
				return panel;
		}
		
		return panel;
	}
	
	public void resetPanel()
	{
		this.gameRoomList.clear();
		this.userInfo.setText(null);
	}
	
	public void setTitleText(String title)
	{
		this.title.setText(title);
	}
}
