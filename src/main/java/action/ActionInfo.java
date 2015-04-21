package action;

/**
 * Class that stores class and method information of an action.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public class ActionInfo
{
	// Class name of the action
	private String actionClass;
	
	// Method name of the action
	private String methodName;
	
	// parameter Types
	private Class<?>[] paramTypes;

	/**
	 * Creates an ActionInfo object with the given class name, method name,
	 * and the parameter type array.
	 * 
	 * @param actionClass	class name of the action
	 * @param methodName	method name of the action
	 * @param paramTypes	parameter types of an action
	 */
	public ActionInfo(String actionClass,
			String methodName,
			Class<?>[] paramTypes)
	{
		super();
		this.actionClass = actionClass;
		this.methodName = methodName;
		this.paramTypes = paramTypes;
	}

	
	/*
	 * GETTERS and SETTERS
	 */
	public String getActionClass()
	{
		return actionClass;
	}
	
	public String getMethodName()
	{
		return methodName;
	}
	
	public Class<?>[] getParamTypes()
	{
		return paramTypes;
	}
}
