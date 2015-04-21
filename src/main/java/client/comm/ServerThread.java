package client.comm;

import java.io.IOException;

import controller.ReadException;

import client.comm.Command;




import annot.Producer;

/**
 * Server Thread that listens incoming messages from the server.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class ServerThread extends Thread
{
	// connection to server
	private Server server;
	
	// indicator for the thread
	private boolean active;
	
	/**
	 * Construct a thread for the given server.
	 * 
	 * @param server	server to be associated with this thread
	 */
	public ServerThread(Server server)
	{
		this.server = server;
		this.active = true;
	}

	/**
	 * Listens to the messages coming from the server until the connection
	 * is lost. 
	 */
	public void run()
    {	
		String input;
		
		while(this.isActive())
		{
			try {
				input = server.receive();
				produceCommand(input);
			} catch (IOException e) {
				setActive(false);
			} catch (ReadException e) {
				setActive(false);
			}
			
		}
    }
	
	/**
	 * Produces command from incoming requests.
	 *  
	 * @param input		request string
	 * @return			Command object containing the request.
	 */
	@Producer
	public Command produceCommand(String input)
	{
		return new Command(input);
	}

	/*
	 * GETTERS and SETTERS 
	 */
	
	public void setActive(boolean active)
	{
		this.active = active;
	}

	public boolean isActive()
	{
		return active;
	}

}
