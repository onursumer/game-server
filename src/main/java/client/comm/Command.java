package client.comm;

/**
 * Represents the command coming from the server or GUI.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class Command
{
	// command string
	private String input;
	
	/**
	 * Default constructor.
	 */
	public Command()
	{
		super();
	}
	
	/**
	 * Constructs a Command object with the given input string.
	 * 
	 * @param input		the input string
	 */
	public Command(String input)
	{
		this.input = input;
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
}
