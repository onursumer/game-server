package client;

import java.util.Locale;

import client.view.ClientGUI;

public class Main
{
	public static void main(String[] args)
	{
		Locale.setDefault(Locale.ENGLISH);
		ClientGUI gui = ClientGUI.getInstance();
	}
}
