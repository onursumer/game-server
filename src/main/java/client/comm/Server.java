package client.comm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import controller.ReadException;

import controller.Constants;

/**
 * Class that represents the server connection.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class Server
{
	// IP address or domain name of the server
	private String host;
	
	// port number of the server
	private int portNo;
	
	// connection socket
	private Socket connection;
	
	// output stream writer for the server
	private Writer outputStreamWriter;
	
	// input stream reader for the server
    private Reader inputStreamReader;

    /**
     * Constructs a new Server object with given host name and the port no.
     * 
     * @param host		host name or the IP address of the server
     * @param portNo	server's connection port no
     */
    public Server(String host, int portNo)
    {
    	this.host = new String(host);
    	this.portNo = portNo;
    }
    
    /**
     * Initializes the server by setting up a connection.
     * 
     * @throws UnknownHostException		if host not found
     * @throws IOException				if an IO error occurs
     */
    public void init() throws UnknownHostException, IOException
    {
    	this.connection = new Socket(InetAddress.getByName(host), portNo);
    	this.outputStreamWriter = new OutputStreamWriter(new BufferedOutputStream(connection.getOutputStream()));
    	this.inputStreamReader = new InputStreamReader(new BufferedInputStream(connection.getInputStream()));
    }
    
    /**
     * Writes the given message to the server's output stream writer.
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
     * Receives message from the server.
     * 
     * @return		message received
     * @throws IOException		if an IO error occurs
     * @throws ReadException	if the message cannot be read
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
    
    /**
     * Checks the connection status of the server.
     * 
     * @return TRUE if connected, FALSE otherwise
     */
    public boolean isConnected()
    {
    	if(this.connection == null)
    		return false;
    	else if(this.connection.isClosed())
    		return false;    	
    	else if(this.connection.isConnected())
    		return true;
    	else
    		return false;
    }
    
    /**
     * Disconnects from the server.
     * 
     * @throws IOException		if an IO error occurs.
     */
    public void disconnect() throws IOException
    {
    	if(this.connection != null)
    		this.connection.close();
    }
    
}
