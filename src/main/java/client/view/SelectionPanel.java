package client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public abstract class SelectionPanel extends ContentPanel
{
	protected Dimension componentSize;
	protected JScrollPane scrollPane;
	protected JPanel innerPanel;
	
	public SelectionPanel(Integer width, Integer height)
	{
		super(width, height);
		
		this.innerPanel = new JPanel();
		this.innerPanel.setLayout(null);
		
		// set bounds of panels
		this.leftPanel.setSize(width / 4, height);
		this.leftPanel.setLocation(0, 0);		
		this.rightPanel.setSize(3 * width / 4, height);
		this.rightPanel.setLocation((width / 4), 0);
		
		this.scrollPane = new JScrollPane(innerPanel);
		this.scrollPane.setSize(this.rightPanel.getSize());
		
		this.rightPanel.setLayout(new BorderLayout());
		this.rightPanel.add(scrollPane, BorderLayout.CENTER);
		
		this.calculateComponentSize();
		
		this.add(leftPanel);
		this.add(rightPanel);
	}
	
	protected void calculateComponentSize()
	{
		int perRow = 4;
		int perCol = 4;
		double ratio = 0.9;
		double areaW = this.rightPanel.getSize().getWidth() / perCol;
		double areaH = this.rightPanel.getSize().getHeight() / perRow;
		int buttonW = (int)(ratio * areaW);
		int buttonH = (int)(ratio * areaH);
		
		this.componentSize = new Dimension(buttonW, buttonH);
	}
	
	protected Point nextLocation(Point current)
	{
		Point next;
		int perRow = 4;
		int perCol = 4;
		int areaW = (int)this.rightPanel.getSize().getWidth() / perCol;
		int areaH = (int)this.rightPanel.getSize().getHeight() / perRow;
		int leftMargin = (areaW - componentSize.width) / 2;
		int topMargin = (areaH - componentSize.height) / 2;
		int x, y;
		
		if(current == null)
			next = new Point(leftMargin, topMargin);
		else
		{
			x = (current.x + areaW);
			
			if(x > (perCol - 1) * areaW + leftMargin)
			{
				x = leftMargin;
				y = current.y + areaH;
			}
			else
				y = current.y;
			
			next = new Point(x, y);
		}
		
		return next; 
	}
}
