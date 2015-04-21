package server.comm;

import java.io.IOException;
import java.net.Socket;

/**
 * Thread that listens incomming connection requests from the clients.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class ListenerThread extends Thread
{
	// server connection to listen incoming connections
	private Server server;
	
	// indicator for the thread
	private boolean active;

	/**
	 * Constructs a ListenerThread for the given server.
	 * @param server	server connection to listen
	 */
	public ListenerThread(Server server)
	{
		this.server = server;
		this.active = true;
	}
	
	/**
	 * Listens to incoming connection until the thread is deactivated.
	 */
	public void run()
	{
		Thread clientThread;
		Client client;
		Socket clientSock;
		
		while(this.isActive())
		{
			try {
				// listen to incoming connections
				clientSock = server.listen();
				// create new client for the connection
				client = new Client(clientSock);
				// init client
				client.init();				
				// create client thread which interacts with the client
				clientThread = new ClientThread(client);
				// start client thread
				clientThread.start(); 
			} catch (IOException e) {
				setActive(false);
			}
		}
	}
	
	
	/*
	 * GETTERS and SETTERS
	 */
	
	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}
}
