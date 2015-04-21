package client.comm;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import client.controller.ClientController;

import annot.Producer;

/**
 * This aspect maintains (on the client side) synchronization of incoming
 * requests from the server and the ClientGUI. The produced commands are
 * consumed by the inner consumer class. 
 * 
 * @author Selcuk Onur Sumer
 *
 */
public aspect ProducerAspect
{
	// synchronized list of commands
	protected LinkedList<Command> commandList;
	
	// reference to the client controller
	protected ClientController controller;
	
	/**
	 * Default constructor
	 */
	public ProducerAspect()
	{
		commandList = new LinkedList<Command>();
		controller = ClientController.getInstance();
		new ConsumerThread().start();
	}
	
	// pointcut for the call of the methods tagged with Producer annotation
	pointcut production() : call(@Producer * *(..));
	
	/**
	 * Advises after a producer method produces a command. 
	 * 
	 * @param command	produced command
	 */
	after () returning (Command command): production()
	{
		// add the command to the synchronized command list
		synchronized (commandList)
		{
			commandList.add(command);
			commandList.notifyAll();
		}
	}
	
	/**
	 * Inner ComsumerThread which forwards commands added to the command
	 * list.
	 * 
	 * @author Selcuk Onur Sumer
	 *
	 */
	private class ConsumerThread extends Thread
	{	
		public void run()
		{
			Command command;
			
			while(true)
			{
				synchronized (commandList)
				{
					
					while (commandList.size() == 0)
					{
						try {
				              commandList.wait();
						} catch (InterruptedException ex) {
				              
						}
					}

					command = commandList.remove();
					commandList.notifyAll();
				}
				
				try {
					System.out.println(command.getInput()); //TODO debug consumer
					controller.processCommand(command);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchElementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
			}
		}
	}
}