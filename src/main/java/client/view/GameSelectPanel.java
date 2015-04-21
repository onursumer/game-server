package client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;

import action.Action;

import view.ImageProcess;

public class GameSelectPanel extends SelectionPanel
{
	private List<JButton> buttonList;
	private JLabel infoLabel;
	
	public GameSelectPanel(Integer width, Integer height)
	{
		super(width, height);
		this.buttonList = new ArrayList<JButton>();
		
		
		this.infoLabel = new JLabel(ImageProcess.scaleIcon("resource/games.gif",
				 this.leftPanel.getWidth(),
				 this.leftPanel.getHeight()));
		
		this.leftPanel.setLayout(new BorderLayout());
		this.leftPanel.add(infoLabel, BorderLayout.CENTER);
	}
	
	public void addGameButton(String gameName)
	{
		JButton button = new JButton(); 
		button.setIcon(ImageProcess.scaleIcon(
				"resource/" + gameName.toLowerCase() + "_button.jpg", 
				this.componentSize.width,
				this.componentSize.height));
		button.setActionCommand(Action.SELECT_GAME + "?gameName=" + gameName);
		this.buttonList.add(button);
	}
	
	@Override
	public void refreshPanel()
	{
		JButton button;
		Point location = null;
		Iterator<JButton> iter = this.buttonList.iterator();
		double ratio = 0.9;
		
		this.innerPanel.setVisible(false);
		
		this.innerPanel.removeAll();
		
		while(iter.hasNext())
		{
			button = iter.next();
			location = this.nextLocation(location);
			button.setLocation(location);
			button.setSize(this.componentSize);
			this.innerPanel.add(button);
		}
		
		if(location != null)
		{
			int width = (int)(ratio * this.rightPanel.getSize().width);
			int height = location.y + this.componentSize.height;
						
			this.innerPanel.setPreferredSize(new Dimension(width, height));
		}
		
		this.innerPanel.setVisible(true);
	}
}
