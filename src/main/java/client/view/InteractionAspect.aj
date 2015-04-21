package client.view;

import javax.swing.AbstractButton;
import javax.swing.JMenuItem;
import javax.swing.JButton;

/**
 * Adds an ActionListener (CommandListener) to every new 
 * JButton and JMenuItem.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public aspect InteractionAspect
{	
	// pointcut for construction of a new JMenuItem or JButton
	pointcut itemCreation() :
		(call(public JMenuItem.new(..)) || call(public JButton.new(..)));
	
	/**
	 * Advises after the construction of the item
	 * 
	 * @param item		new created item
	 */
	after () returning (AbstractButton item): itemCreation()
	{
		// adds the CommandListener to the item
		item.addActionListener(CommandListener.getInstance());
	}
}
