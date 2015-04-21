package controller;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

/**
 * Settings Class for host name, port number, and username for connection
 * settings.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class Settings
{
	// default values
	public final static Integer DEFAULT_PORT = 48961;
	public final static String DEFAULT_HOST = "127.0.0.1";
	public final static String DEFAULT_USER = "admin";
	public final static String SETTINGS_FILE = "settings.ini";
	
	private String username;
	private String host;
	private Integer portNo;
	
	/**
	 * Default constructor.
	 */
	public Settings()
	{
		this.username = DEFAULT_USER;
		this.host = DEFAULT_HOST;
		this.portNo = DEFAULT_PORT;
	}

	/**
	 * Loads settings from the local settings file.
	 * 
	 * @return	TRUE if successful, FALSE otherwise
	 */
	public boolean loadSettings()
	{
		RandomAccessFile file;
		String line = new String();
		StringTokenizer tokenizer;
		String variable, value;
		
		try {
			file = new RandomAccessFile(SETTINGS_FILE, "r");
		} catch (FileNotFoundException e) {
			return false;
		}
		
		// parse the file until the end
		while(true)
		{
			try {
				line = file.readLine();
			} catch (IOException e) {
				break;
			}
			
			if(line == null) // end of file
				break; // stop parsing
			
			tokenizer = new StringTokenizer(line, " =");
		
			if(tokenizer.countTokens() == 0)
				continue; // skip empty lines			
			else if(tokenizer.countTokens() != 2)
				continue; // skip illegal lines			
			
			variable = tokenizer.nextToken();
			
			if(variable.equals("#"))
				continue; // skip comments
			
			value = tokenizer.nextToken();
			
			if(variable.equals("host"))
				this.configHostIP(value);
			else if(variable.equals("port"))
				this.configPort(value);
			else if(variable.equals("user"))
				this.username = value;
		}
		
		try {
			file.close();
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Saves the current settings to the local output file. Returns TRUE if
	 * the operation is successful, returns FALSE otherwise.
	 * 
	 * @return	TRUE if successful, FALSE otherwise
	 */
	public boolean saveSettings()
	{
		BufferedWriter output;
		
		try {
			output = new BufferedWriter(new FileWriter(SETTINGS_FILE));
			
			output.write("# Hostname");
			output.newLine();
			output.write("host = ");
			output.write(this.getHost());
			output.newLine();
			output.newLine();
			
			output.write("# Connection Port");
			output.newLine();
			output.write("port = ");
			output.write(this.getPortNo().toString());
			output.newLine();
			output.newLine();
			
			output.write("# Username");
			output.newLine();
			output.write("user = ");
			output.write(this.getUsername());
			output.newLine();
			output.flush();
			output.close();
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Configures host name or IP.
	 * 
	 * @param host	IP or name of the host
	 */
	public void configHostIP(String host)
	{
		if(host != null)
			this.host = host;
	}
	
	/**
	 * Configures port no of the server.
	 * 
	 * @param portNo	port number
	 * @return	TRUE if configuration is successful, FALSE otherwise
	 */
	public boolean configPort(String portNo)
	{
		int port;
		
		try {
			port = Integer.parseInt(portNo);
		} catch (NumberFormatException e) {
			return false;
		}
		
		if(port <= 1024 || port > 65536)
			return false;
		
		this.portNo = port;
		
		return true;
	}
	
	/**
	 * Configures the username of the client.
	 * 
	 * @param username
	 */
	public void configUser(String username)
	{
		if(username != null)
			this.username = username;
	}
	
	/*
	 * GETTERS and SETTERS 
	 */
	
	public String getUsername()
	{
		return username;
	}
	
	public Integer getPortNo()
	{
		return portNo;
	}
	
	public String getHost()
	{
		return host;
	}
}
