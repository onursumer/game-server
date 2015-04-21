package server.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import server.comm.Client;
import server.comm.ListenerThread;
import server.comm.Server;

/**
 * Communicator Class.
 * This class contains method for controlling the communication between the
 * server and the clients.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class Communicator
{
	// Server connection
	private Server server;
	
	// Port number to listen connections 
	private int portNo;
	
	// Listener thread of the server
	private Thread listener;
	
	// indicats the activeness of the server
	private boolean serverActive;
	
	// list of all clients connected to the server
	private List<Client> clientList;
	
	/**
	 * Constructs a Communicator object by initializing the client list,
	 * the port no, and the indicator.
	 */
	public Communicator(int portNo)
	{
		clientList = new ArrayList<Client>();
		this.portNo = portNo;
		serverActive = false;
	}
	
	/**
	 * Starts the server by creating a ListenerThread object which
	 * listens the incoming connections.
	 * 
	 * @return	TRUE if server is started successfully, FALSE otherwise
	 */
	public boolean startServer()
	{
		server = new Server(portNo);
		
		try {
			server.init();
			listener = new ListenerThread(server);
			listener.start();
			serverActive = true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Stops the server and disconnect all clients from the server.
	 * 
	 * @return	TRUE if server is stopped successfully, FALSE otherwise
	 */
	public boolean stopServer()
	{
		if(!isServerActive())
			return false;
		
		try {
			server.stop(); // stop accepting clients
			serverActive = false; // set server inactive
			disconnectAll(); // disconnect all clients
			clientList.clear(); // clear client list
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Adds the specified client to the client list.
	 *
	 * @param client	client to be added
	 */
	public void addClient(Client client)
	{
		clientList.add(client);
	}
	
	/**
	 * Removes the specified client from the client list.
	 * 
	 * @param client	client to be removed
	 */
	public void removeClient(Client client)
	{
		clientList.remove(client);
	}
	
	/**
	 * Disconnect each client by closing the socket associated with it.
	 * 
	 * @throws IOException	if an IO error occurs
	 */
	private void disconnectAll() throws IOException
	{
		for(int i=0; i<clientList.size(); i++)		
			clientList.get(i).getSocket().close();
	}
	
	
	/*
	 * GETTERS and SETTERS
	 */
	
	public int getPortNo()
	{
		return portNo;
	}
	
	public void setPortNo(int portNo)
	{
		this.portNo = portNo;
	}
	
	public List<Client> getClientList()
	{
		return clientList;
	}
	
	public boolean isServerActive()
	{
		return serverActive;
	}
}
