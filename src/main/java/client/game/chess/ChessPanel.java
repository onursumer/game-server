package client.game.chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import view.ImageProcess;

/**
 * View of client side. Clients plays chess via chess panel
 * 
 * @author Alper Karacelik
 *
 */
public class ChessPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JLabel labelCells[] = new JLabel[64];
	private JPanel panelCells[] = new JPanel[64];
	private String stringPieces[][] = new String[8][8];
	private int heldX, heldY, heldI = -1;
	private JToolBar tlbMain = null;

	private JPanel northPanel;
	private JButton selectWhite;
	private JButton selectBlack;
	private JLabel northTitle;
	
	private JLabel lblCurrPlayer = null;
	private JLabel lblPlayerColor = null;
	private JLabel lblStatus = null;
	private String currPlayer;
	
	public ChessPanel() 
	{
		super();
		initialize();
		addListeners();
	}
	
	private void initialize() 
	{	
		this.initNorthPanel();
		
		this.setLayout(new BorderLayout());
		this.add(northPanel, BorderLayout.NORTH);
		this.add(getJContentPane(), BorderLayout.CENTER);
	}
	
	private void initNorthPanel()
	{
		this.selectWhite = new JButton("White");
		this.selectBlack = new JButton("Black");
		this.northTitle = new JLabel("<--- Select Your Side --->");
		this.northTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.selectWhite.setActionCommand("SELECT_PLAYER?player=white");
		this.selectBlack.setActionCommand("SELECT_PLAYER?player=black");
		
		this.northPanel = new JPanel();
		this.northPanel.setLayout(new GridLayout(1,3));
		
		this.northPanel.add(selectWhite);
		this.northPanel.add(northTitle);
		this.northPanel.add(selectBlack);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
			jContentPane.add(getTlbMain(), BorderLayout.SOUTH);
		}
		return jContentPane;
		
	}
	
	private JPanel getJPanel()
	{
		if (jPanel == null) {
			GridLayout gridLayout = new GridLayout(8,8);
			gridLayout.setHgap(5);
			gridLayout.setVgap(5);
			jPanel = new JPanel();
			jPanel.setLayout(gridLayout);
			int k=0;
			for (int i=0; i<8; i++){
				for(int j=0; j<8; j++){
					
					labelCells[k] = new JLabel();
					labelCells[k].setOpaque(false);
					panelCells[k] = new JPanel();

					if((((i % 2) + (j % 2)) % 2) == 0){
						panelCells[k].setBackground(Color.WHITE);
						panelCells[k].add(labelCells[k]);
					}
					else{
						panelCells[k].setBackground(Color.GRAY);
						panelCells[k].add(labelCells[k]);
					}
					
					jPanel.add(panelCells[k]);
					k++;
				}
			}
		}
		return jPanel;		
	}
	
	private JToolBar getTlbMain() 
	{
		if (tlbMain == null) 
		{	
			lblCurrPlayer = new JLabel();
			lblCurrPlayer.setText("");
			lblPlayerColor = new JLabel();
			lblPlayerColor.setText("");
			lblStatus = new JLabel();
			lblStatus.setText("");
			lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
			
			tlbMain = new JToolBar();
			tlbMain.setLayout(new BorderLayout());
			tlbMain.add(lblPlayerColor, BorderLayout.WEST);
			tlbMain.add(lblStatus, BorderLayout.CENTER);
			tlbMain.add(lblCurrPlayer, BorderLayout.EAST);
			
		}
		return tlbMain;
	}

	private void resetMainPanel(){
		int k=0;
		for (int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				
				if((((i % 2) + (j % 2)) % 2) == 0){
					panelCells[k].setBackground(Color.WHITE);
				}
				else{
					panelCells[k].setBackground(Color.GRAY);
				}
				
				k++;
			}
		}
	}
	
	public void setChessBoard(String[][] currentBoard)
	{
		for (int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				stringPieces[i][j] = currentBoard[i][j];
			}
		}
		lblStatus.setText("Game Initialized!");
		currPlayer="White";
		lblCurrPlayer.setText(currPlayer + " Player's Turn");
		placePieces(stringPieces);
		resetMainPanel();
	}
	
	public void makeMovement(int fromX, int fromY, int toX, int toY)
	{
		lblStatus.setText(stringPieces[fromX][fromY] + " Moves from " + showBoardRelative(fromX, fromY) + " to " + showBoardRelative(toX, toY));
		
		stringPieces[toX][toY] = stringPieces[fromX][fromY];
		stringPieces[fromX][fromY] = "";
		
		placePieces(stringPieces);
		resetMainPanel();
		changeTurn();
		lblCurrPlayer.setText(currPlayer + " Player's Turn");
	}
	
	public void endOfGame(String winner)
	{
		JOptionPane.showMessageDialog(this, winner + " wins!", "Cell Information", JOptionPane.INFORMATION_MESSAGE );
	}
	
	private void placePieces(String pieces[][]){
		
		int k = 0;
		for (int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				labelCells[k].setIcon(null);
				if(pieces[i][j] != null && !pieces[i][j].equals(""))
				{
					ImageIcon icon = ImageProcess.scaleIcon("resource/chess/images/" + pieces[i][j] + ".png", 65, 65);
					//ImageIcon icon = new ImageIcon("resource/chess/images/" + pieces[i][j] + ".png");
					labelCells[k].setIcon(icon);
				}
				k++;
			}
		}
	}
	
	private void addListeners(){
		
		int k = 0;
		for (int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				
				final int finalI = i;
				final int finalJ = j;
				final int finalK = k; 
				final Color color = panelCells[k].getBackground();
				
				panelCells[k].addMouseListener(new java.awt.event.MouseAdapter()
				{
					public void mouseEntered(MouseEvent e)
					{
						if(finalK != heldI)
						{
							panelCells[finalK].setBackground(Color.DARK_GRAY);
						}
					}
				});
				
				panelCells[k].addMouseListener(new java.awt.event.MouseAdapter()
				{
					public void mouseExited(MouseEvent e)
					{
						if(finalK != heldI)
						{
						panelCells[finalK].setBackground(color);
						}
					}
				});
				
				panelCells[k].addMouseListener(new java.awt.event.MouseAdapter() 
				{
					public void mouseReleased(MouseEvent e)
					{
						if(e.getModifiers() == InputEvent.BUTTON3_MASK)
						{
							showCellInfo(finalI, finalJ, finalK);
						}
						else if(e.getModifiers() == InputEvent.BUTTON1_MASK)
						{
							panelCells[finalK].setBackground(Color.DARK_GRAY);
							clickCell(e, finalI, finalJ, finalK);
						}
					}
				});
				
				k++;
			}
		}
	}

	private void showCellInfo(int x, int y, int i)
	{
		if(stringPieces[x][y] != null && !stringPieces[x][y].equals(""))
		{
			JOptionPane.showMessageDialog( this, stringPieces[x][y] + " located at " + showBoardRelative(x, y), "Cell Information", JOptionPane.INFORMATION_MESSAGE );
		}
		else
		{
			JOptionPane.showMessageDialog( this, "No piece located at " + showBoardRelative(x, y), "Cell Information", JOptionPane.INFORMATION_MESSAGE );
		}
	}
	
	/**
	 * Translates grid relative coordinates to chess board relative
	 * Example: 0x0 to A8
	 * 
	 * @param x		X coordinate(0..7)
	 * @param x		Y coordinate(0..7)
	 * @return		chess coordinate (A1..H8) as string
	 * 
	 */
	
	private String showBoardRelative(int x, int y)
	{
		String chessCoord = "";
		chessCoord = (char)(y + 65) + "" + (x - 8) * -1;
		return chessCoord;	
	}
	
	private boolean clickCell(MouseEvent e, int x, int y, int i)
	{
		JPanel lblClicked = (JPanel)e.getSource();
		if(heldI != -1) // We're dropping a piece
		{
			jContentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			//TODO movePiece(heldX, heldY, heldI, x, y, i);
			resetMainPanel();
			heldI = -1;
			return true;
		}
		else // We're picking up a piece
		{
			if(stringPieces[x][y] == null || stringPieces[x][y].equals(""))
			{
				//lblStatus.setText("No piece to pick up.");
			}
			else
			{
				jContentPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				lblClicked.setBackground(new Color(255, 176, 70));
				//lblStatus.setText("Picked up " + pieceName.get(jPieces[x][y]) + " from " + showBoardRelative(x, y));
				setHeldX(x);
				setHeldY(y);
				heldI = i;
			}
			return false;
		}
	}

	public void setHeldX(int heldX) {
		this.heldX = heldX;
	}

	public int getHeldX() {
		return heldX;
	}

	public int setHeldY(int heldY) {
		this.heldY = heldY;
		return heldY;
	}

	public int getHeldY() {
		return heldY;
	}

	private void changeTurn()
	{
		if( currPlayer.compareToIgnoreCase("White") == 0)
		{
			currPlayer = "Black";
		}
		else
		{
			currPlayer = "White";
		}
	}

	public JLabel getLblPlayerColor() {
		return lblPlayerColor;
	}

}
