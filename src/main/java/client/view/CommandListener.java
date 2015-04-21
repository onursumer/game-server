package client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.comm.Command;

import annot.Producer;

public class CommandListener implements ActionListener
{
	// singleton
	private static CommandListener instance = new CommandListener();
	
	private CommandListener()
	{
		super();
	}
	
	public static CommandListener getInstance()
	{
		return instance;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		produceCommand(event.getActionCommand());
	}
	
	@Producer
	public Command produceCommand(String input)
	{
		return new Command(input);
	}
}
