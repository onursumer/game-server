package client.controller;

import java.io.IOException;
import java.net.UnknownHostException;

import client.comm.Server;
import client.comm.ServerThread;

/**
 * Communicator Class.
 * This class contains methods for the communication between the server and
 * the client.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class Communicator
{
	// server connection
	private Server server;
	
	// host name or IP address of the server
	private String host;
	
	// port number of the server
	private int portNo;
	
	/**
	 * Constructs a new Communicator object with given host name
	 * and port number.
	 */
	public Communicator(String host, int portNo)
	{
		this.host = host;
		this.portNo = portNo;
	}
	
	/**
	 * Initializes a new connection with the server.
	 * 
	 * @return	TRUE if connection is established, FALSE otherwise
	 */
	public boolean initConnection()
	{
		server = new Server(host, portNo);
			
		// try to connect to the server
		try {
			server.init();
			new ServerThread(server).start();
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Sends the given request to the server.
	 * 
	 * @param request		request to be sent
	 * @throws IOException	if an IO error occurs
	 */
	public void request(String request) throws IOException
	{	
		if(this.server != null)
			this.server.send(request);
	}
	
	/*
	 * GETTERS and SETTERS
	 */
	
	public void setHost(String host)
	{
		this.host = host;
	}
	
	public String getHost()
	{
		return this.host;
	}

	public int getPortNo()
	{
		return portNo;
	}

	public void setPortNo(int portNo)
	{
		this.portNo = portNo;
	}
}
