package server.comm;

import java.io.IOException;

import controller.ReadException;

import annot.Producer;

/**
 * Client Thread that listens incoming messages from the specific client.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class ClientThread extends Thread
{
	// client connection for this thread
	private Client client;
	
	// indicator for the thread
	private boolean active;
	
	/**
	 * Construct a thread for the given client.
	 * 
	 * @param client	client to be associated with this thread
	 */
	public ClientThread(Client client)
	{
		this.active = true;
		this.client = client;
	}

	/**
	 * Listens to the messages coming from the client until the connection
	 * is lost. 
	 */
	public void run()
	{	
		String input;
		
		// wait for incoming messages from the client until the connection is
		// lost or the server is down.
		while(this.isActive())
		{
			try {
				input = client.receive(); // read incoming message
				produceCommand(input);
			} catch (IOException e) {
				setActive(false);
			} catch (ReadException e) {
				setActive(false); // set status false
			}
		}
		
		// try to close client connection if it is still open 
		try {
			client.getSocket().close(); // close connection
		} catch (IOException e) {
			
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
		return new Command(this.client, input);
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

	public Client getClient()
	{
		return client;
	}
}
