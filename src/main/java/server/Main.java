package server;

import java.util.Locale;

import server.controller.ServerController;
import server.view.ServerConsoleUI;

public class Main
{
	public static void main(String[] args)
	{
		Locale.setDefault(Locale.ENGLISH);
		ServerConsoleUI ui = ServerConsoleUI.getInstance();
		//ui.init();
		
		ServerController.getInstance().getCommunicator().startServer(); // TODO
	}
}
