package action;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Class that stores necessary objects for invocation of an action.
 * 
 * @author Selcuk Onur Sumer
 */
public class ActionHandler
{
	// Class of the action
	private Class<?> actionClass;

	// Method of the action class
	private Method actionMethod;
	
	// parameter list for the action method
	private HashMap<String, String> actionParam;

	/**
	 * Constructs a new ActionHandler object by initializing the parameter
	 * map.
	 */
	public ActionHandler()
	{
		actionParam = new HashMap<String, String>();
	}
	
	/**
	 * Initializes the handler according to the given ActionInfo. 
	 * 
	 * @param info		information of the action
	 * @throws ClassNotFoundException	if a reflection error occurs
	 * @throws SecurityException		if a reflection error occurs
	 * @throws NoSuchMethodException	if a reflection error occurs
	 */
	public void init(ActionInfo info) throws ClassNotFoundException,
			SecurityException, NoSuchMethodException
	{
		actionClass = Class.forName(info.getActionClass());
		actionMethod = actionClass.getMethod(info.getMethodName(), 
				info.getParamTypes());
	}
	
	/**
	 * Adds an action parameter with its name and content to the
	 * parameter map.
	 * 
	 * @param paramName		name of the parameter
	 * @param paramContent	value of the parameter
	 */
	public void addParam(String paramName, String paramContent)
	{
		actionParam.put(paramName, paramContent);
	}

	
	/*
	 * GETTERS and SETTERS
	 */
	
	public Class<?> getActionClass()
	{
		return actionClass;
	}

	public Method getActionMethod()
	{
		return actionMethod;
	}
	
	public HashMap<String, String> getActionParam()
	{
		return actionParam;
	}
}
