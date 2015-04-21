package client.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import action.Action;

public class ClientMenuBar extends JMenuBar
{
	private JMenu fileMenu, helpMenu;
	private JMenuItem exit;
	private JMenuItem help, about;
	
	public ClientMenuBar()
	{
		super();
		createMenu();
	}
	
	private void createMenu()
	{
		// constructing menus and menu items
		fileMenu = new JMenu("File");
		helpMenu = new JMenu("Help");
		
		exit = new JMenuItem("Exit");
				
		help = new JMenuItem("Help");
		about = new JMenuItem("About");
		
		fileMenu.add(exit);
		
		helpMenu.add(help);
		helpMenu.add(about);
		
		this.add(fileMenu);
		this.add(helpMenu);
		
		// setting action commands
		exit.setActionCommand(Action.EXIT + "?");
		help.setActionCommand(Action.HELP + "?");
		about.setActionCommand(Action.ABOUT + "?");
	}
}
