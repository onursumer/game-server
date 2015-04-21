package controller;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import action.ActionHandler;
import action.ActionInfo;

/**
 * Abstract Controller Class.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public abstract class Controller
{
	// action map for the relation between action commands and action methods
	protected HashMap<String,ActionInfo> actionMap;
	
	/**
	 * Parses the given input string and creates and ActionHandler containing
	 * the Action class, the action method, and the parameters of the action 
	 * for the action specified in the input string.
	 * 
	 * @param input		input to be parsed
	 * @return		ActionHandler containing action related info
	 * @throws SecurityException		if a reflection error occurs
	 * @throws ClassNotFoundException	if a reflection error occurs
	 * @throws NoSuchMethodException	if a reflection error occurs
	 */
	protected ActionHandler parse(String input) throws SecurityException, 
			ClassNotFoundException, NoSuchMethodException,
			NoSuchElementException
	{
		ActionHandler handler = new ActionHandler();
		ActionInfo info;
		StringTokenizer tokenizer, paramTokenizer;
		String actionName, paramName, paramContent;
		
		tokenizer = new StringTokenizer(input,
				Constants.END_OF_MSG.toString());
		tokenizer = new StringTokenizer(tokenizer.nextToken(), "?");
		
		actionName = tokenizer.nextToken();
		info = this.actionMap.get(actionName);
		
		if(info == null)
			return null;
		
		handler.init(info);
		
		if(tokenizer.hasMoreTokens()) // if command has parameters
		{
			tokenizer = new StringTokenizer(tokenizer.nextToken(), "&");
		
			while(tokenizer.hasMoreTokens())
			{
				paramTokenizer = new StringTokenizer(tokenizer.nextToken(), "=");
				paramName = paramTokenizer.nextToken();
				paramContent = paramTokenizer.nextToken();
				
				handler.addParam(paramName, paramContent);
			}
		}
		
		return handler;
	}
}
