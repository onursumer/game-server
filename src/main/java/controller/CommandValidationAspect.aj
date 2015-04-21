package controller;

import java.io.Writer;
import java.io.OutputStreamWriter;

public aspect CommandValidationAspect
{	
	// pointcut for the network message sending
	pointcut messagePassing(String message) :
		call(public * Writer+.write(String))
		&& args(message)
		&& target(OutputStreamWriter);
	
	/**
	 * Boosts the message to BUFFER_SIZE before sending it,
	 * and then proceeds the advised method.
	 * 
	 * @param message	Message to be passed via network
	 */
	void around (String message) : messagePassing(message)
	{
		String fullMessage = new String(message);
		
		if(message.length() < Constants.BUFFER_SIZE)
		{
			for(int i = message.length(); i < Constants.BUFFER_SIZE; i++)
				fullMessage += Constants.END_OF_MSG;
		}
		else
		{
			fullMessage = message.substring(0, Constants.BUFFER_SIZE - 2);
			fullMessage += Constants.END_OF_MSG;
		}
		
		proceed(fullMessage);
	}
}
