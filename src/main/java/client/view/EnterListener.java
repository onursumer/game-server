package client.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import annot.Producer;
import client.comm.Command;

public class EnterListener implements KeyListener
{
	// singleton
	private static EnterListener instance = new EnterListener();
	
	private EnterListener()
	{
		super();
	}
	
	public static EnterListener getInstance()
	{
		return instance;
	}
	
	public void keyPressed(KeyEvent event)
	{
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent event)
	{
		if(event.getKeyCode() == KeyEvent.VK_ENTER)
			produceCommand("CHAT?");
	}

	public void keyTyped(KeyEvent event)
	{
		// TODO Auto-generated method stub
		
	}

	@Producer
	public Command produceCommand(String input)
	{
		return new Command(input);
	}
}
