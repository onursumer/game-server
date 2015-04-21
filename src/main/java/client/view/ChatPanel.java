package client.view;

import java.awt.BorderLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import action.Action;

public class ChatPanel extends JPanel
{
	private JScrollPane chatPane;
	private JPanel fieldPanel;
	private JTextArea chatArea;
	private JTextField chatField;
	private JButton send;
	
	public ChatPanel()
	{
		this.chatArea = new JTextArea();
		this.chatArea.setRows(3);
		this.chatArea.setEditable(false);
		this.chatPane = new JScrollPane(chatArea);
		
		this.chatField = new JTextField();
		this.chatField.addKeyListener(EnterListener.getInstance());
		this.chatField.setActionCommand(Action.CHAT + "?");
		this.send = new JButton("Send");
		this.send.setActionCommand(Action.CHAT + "?");
		
		this.fieldPanel = new JPanel();
		this.fieldPanel.setLayout(new BorderLayout());
		this.fieldPanel.add(chatField, BorderLayout.CENTER);
		this.fieldPanel.add(send, BorderLayout.EAST);
		
		this.setLayout(new BorderLayout());
		this.add(chatPane, BorderLayout.CENTER);
		this.add(fieldPanel, BorderLayout.SOUTH);
	}

	public void refreshPanel()
	{
		chatArea.scrollRectToVisible(
				new Rectangle(0, chatArea.getHeight(), 0, 0));
	}
	
	public JTextField getChatField()
	{
		return chatField;
	}
	
	public JTextArea getChatArea()
	{
		return chatArea;
	}
}
