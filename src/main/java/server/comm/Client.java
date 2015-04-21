package server.comm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

import controller.ReadException;

import controller.Constants;

/**
 * Client Class.
 * Represents a connected client with its connection socket and the
 * communication streams.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class Client
{
	// client connection socket
	private Socket clientSock;
	
	// output writer
	private Writer outputStreamWriter;
	
	// input reader
	private Reader inputStreamReader;
	
	
	/**
	 * Constructs a client with the given connection socket.
	 * 
	 * @param clientSock	socket of the client
	 */
	public Client(Socket clientSock)
	{
		this.clientSock = clientSock;
	}
	
	/**
	 * Initializes the client by instantiating its reader and writer streams. 
	 * 
	 * @throws IOException	if an IO error occurs.
	 */
	public void init() throws IOException
    {
    	this.outputStreamWriter = new OutputStreamWriter(new BufferedOutputStream(this.getSocket().getOutputStream()));
    	this.inputStreamReader = new InputStreamReader(new BufferedInputStream(this.getSocket().getInputStream()));
    }
	
	/**
	 * Returns the socket of the client
	 */
	public Socket getSocket()
	{
		return clientSock;
	}
	
	/**
	 * Returns the address of the client
	 */
	public String getAddress()
	{
		return clientSock.getInetAddress().getHostAddress();
	}

	/**
	 * Sends the given message to the client
	 * 
	 * @param message		message to be sent
	 * @throws IOException	if an IO error occurs
	 */
	public void send(String message) throws IOException
    {
    	if(this.outputStreamWriter != null)
    	{
    		this.outputStreamWriter.write(message);
    		this.outputStreamWriter.flush();
    	}
    }
	
	/**
	 * Receives a single message with a size BUFFER_SIZE from the client.
	 *  
	 * @return		message received
	 * @throws IOException		if an IO error occurs
	 * @throws ReadException	if a read error occurs
	 */
	public String receive() throws IOException, ReadException
    {
		char[] buffer = new char[Constants.BUFFER_SIZE];
		int byteCount;
    	
    	if(this.inputStreamReader != null)
    	{
    		byteCount = this.inputStreamReader.read(buffer);
    		
    		if(byteCount <= 0) // read failed
    			throw new ReadException();
    		
    		return new String(buffer);
    	}
    	else
    		throw new ReadException();
    }
	
	public String toString()
	{
		String str = new String();
		String ip = clientSock.getInetAddress().getHostAddress();
		int port = clientSock.getPort();
		str += " [" + ip + ":" + port + "]";
		
		return str;
	}
}
