package client.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import action.Action;

import client.model.GameRoom;

public class RoomInfoPanel extends JPanel
{
	public static final String ROOM = "Room#";
	
	private JLabel numberLabel;
	private List<JLabel> usernameLabelList;
	private JButton join;
	private JPanel headerPanel;
	private JPanel listPanel;
	
	public RoomInfoPanel()
	{
		this.usernameLabelList = new ArrayList<JLabel>();
		
		this.numberLabel = new JLabel();
		this.join = new JButton("Join");
		
		this.headerPanel = new JPanel();
		this.listPanel = new JPanel();
		
		this.headerPanel.setLayout(new GridLayout(1,2));
		this.listPanel.setLayout(new GridLayout(0,2));
		
		this.headerPanel.add(this.numberLabel);
		this.headerPanel.add(this.join);
		
		this.setLayout(new BorderLayout());
		this.add(headerPanel, BorderLayout.NORTH);
		this.add(listPanel, BorderLayout.CENTER);
	}
	
	public void update(GameRoom room)
	{
		Iterator<String> iter = room.getPlayers().iterator(); 
		
		this.usernameLabelList.clear();
		this.numberLabel.setText(ROOM + room.getRoomNo());
		this.join.setActionCommand(Action.JOIN_ROOM + "?roomNo=" + 
			room.getRoomNo());
		
		while(iter.hasNext())
		{
			this.usernameLabelList.add(new JLabel(iter.next()));
		}
	}
	
	public void refreshPanel()
	{
		Iterator<JLabel> iter = this.usernameLabelList.iterator(); 
		
		this.setVisible(false);
				
		this.listPanel.removeAll();
		
		while(iter.hasNext())
		{
			this.listPanel.add(iter.next());
		}
		
		this.setVisible(true);
	}

	public JLabel getNumberLabel()
	{
		return numberLabel;
	}
}
