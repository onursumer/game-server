package server.comm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server Class that represents the server connection with its server socket
 * and port number.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class Server
{
	// socket of the server
	private ServerSocket servSock;
	
	// port number for incoming connections
	private int portNo;
	
	/**
	 * Construct a new server with given port number.
	 * 
	 * @param portNo	port number for incoming connections
	 */
	public Server(int portNo)
	{
		this.portNo = portNo;
	}
	
	/**
	 * Initializes the server by creating a new ServerSocket.
	 */
	public void init() throws IOException
	{
		this.servSock = new ServerSocket(this.portNo);
	}
	
	/**
	 * Starts to listen to the server socket for incoming connections.
	 */
	public Socket listen() throws IOException
	{
		return this.servSock.accept();
	}
	
	/**
	 * Stops the server by closing the server socket.
	 * 
	 * @throws IOException	if an IO occurs
	 */
	public void stop() throws IOException
	{
		this.servSock.close();
	}
}
