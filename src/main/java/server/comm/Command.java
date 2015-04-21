package server.comm;

/**
 * Represents the command coming from a client or console.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class Command
{
	// source Client of the command
	private Client client;
	
	// command string 
	private String input;
	
	/**
	 * Default constructor
	 */
	public Command()
	{
		super();
	}
	
	/**
	 * Constructs a Command object with the given source Client and 
	 * the input string.
	 * 
	 * @param client	source Client of the command
	 * @param input		the input string
	 */
	public Command(Client client, String input)
	{
		this.input = input;
		this.client = client;
	}

	
	/*
	 * GETTERS and SETTERS 
	 */
	
	public String getInput()
	{
		return input;
	}
	
	public void setInput(String input)
	{
		this.input = input;
	}
	
	public Client getClient()
	{
		return client;
	}
	
	public void setClient(Client client)
	{
		this.client = client;
	}
	
	
}
