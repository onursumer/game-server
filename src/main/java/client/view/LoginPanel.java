package client.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import action.Action;

public class LoginPanel extends ContentPanel
{
	// login panel components
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel username, password, info;
	private JButton login;
	private JPanel inputPanel;
	private SettingsPanel settingsPanel;

	public LoginPanel(Integer width, Integer height)
	{
		super(width, height);
		createLoginPanel();
	}
	
	private void createLoginPanel()
	{
		// create components
		usernameField = new JTextField(8);
		passwordField = new JPasswordField(8);
		username = new JLabel("Username: ");
		password = new JLabel("Password: ");
		info = new JLabel("Login to continue...");
		login = new JButton("Login");
		
		// setting action commands
		login.setActionCommand(Action.LOGIN + "?");
		
		// construct panels and add components
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2,2));
		inputPanel.add(username);
		inputPanel.add(usernameField);
		inputPanel.add(password);
		inputPanel.add(passwordField);
		
		settingsPanel = new SettingsPanel();
		
		this.leftPanel.setLayout(new BorderLayout());
		this.leftPanel.add(settingsPanel, BorderLayout.CENTER);
		
		this.rightPanel.setLayout(new FlowLayout());
		this.rightPanel.add(info);
		this.rightPanel.add(inputPanel);
		this.rightPanel.add(login);
		
		// set bounds of the panels
		int width = this.getBounds().width;
		int height = this.getBounds().height;
		
		this.rightPanel.setSize(width / 4, height / 5);
		this.rightPanel.setLocation((width / 2) , (height / 3));
		
		this.leftPanel.setSize(width / 4, height);
		this.leftPanel.setLocation(0, 0);
		
		// add panels to login panel
		this.add(rightPanel);
		this.add(leftPanel);
	}

	@Override
	public void refreshPanel()
	{
		this.setVisible(false);
		
		this.removeAll();
		
		this.add(leftPanel);
		this.add(rightPanel);
		
		this.setVisible(true);
	}
	
	public String getUsername()
	{
		return usernameField.getText();
	}

	public String getPassword()
	{
		return new String(passwordField.getPassword());
	}
	
	public SettingsPanel getSettingsPanel()
	{
		return settingsPanel;
	}
}
