package client.comm;

import client.controller.ClientController;

/**
 * This aspect performs operations when a new connection is established to 
 * the server or the current connection is lost.
 * 
 * @author Selcuk Onur Sumer
 *
 */
public aspect ConnectionAspect
{
	/**
	 * Pointcut for the execution of the server thread.
	 * @param thread	server thread
	 */
	pointcut connection(ServerThread thread) :
		execution(public * ServerThread.run())
		&& this(thread);
	
	/**
	 * Advises after the execution of the server thread.
	 */
	after (ServerThread thread) : connection(thread)
	{
		ClientController.getInstance().getGui().showInfo("Connection Info", 
				"Server connection is lost");
	}
}
