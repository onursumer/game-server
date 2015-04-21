package client.view;

import javax.swing.JPanel;

public abstract class ContentPanel extends JPanel
{
	protected JPanel leftPanel;
	protected JPanel rightPanel;
	
	public ContentPanel(Integer width, Integer height)
	{
		super();
		this.setSize(width, height);
		this.leftPanel = new JPanel();
		this.rightPanel = new JPanel();
		this.setLayout(null);
	}
	
	public abstract void refreshPanel();

	public JPanel getLeftPanel()
	{
		return leftPanel;
	}
	
	public JPanel getRightPanel()
	{
		return rightPanel;
	}
}
