package client.view;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import action.Action;

public class SettingsPanel extends JPanel
{
	// login panel components
	private JTextField hostNameField;
	private JTextField portField;
	private JLabel hostName, port, info;
	private JButton save;
	private JPanel inputPanel;
	
	public SettingsPanel()
	{
		super();
		createLoginPanel();
	}
	
	private void createLoginPanel()
	{
		// create components
		hostNameField = new JTextField(8);
		portField = new JTextField(8);
		hostName = new JLabel("Host Name: ");
		port = new JLabel("Port No: ");
		info = new JLabel("===SETTINGS===");
		save = new JButton("Save Settings");
		
		// setting action commands
		save.setActionCommand(Action.SAVE_SETTINGS + "?");
		
		// construct panels and add components
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2,2));
		inputPanel.add(hostName);
		inputPanel.add(hostNameField);
		inputPanel.add(port);
		inputPanel.add(portField);
		
		this.setLayout(new FlowLayout());
		
		this.add(info);
		this.add(inputPanel);
		this.add(save);
	}

	public String getHostName()
	{
		return hostNameField.getText();
	}

	public String getPortNo()
	{
		return portField.getText();
	}
}
