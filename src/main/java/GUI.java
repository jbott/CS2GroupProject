/**
 * @(#)GUI.java
 * Game logic
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GUI extends JFrame implements ActionListener
{
	private Game game;
	private JButton up, down, skip, across, select, roll;
	private JLabel gameStatus, turnStatus, turnPlayer;
	private GridLayout grid;
	private BoardGUI board;
	private JPanel menu;

	public GUI( Game nGame)
	{
		game = nGame;
		setTitle("Weasel Game");
		setSize(910, 750); //board is (900, 600), the menu on the bottm is (900, 150)
		addWindowListener(new WinQuit());
			
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
			
	  	board = new BoardGUI(game);
	  	menu = new JPanel();
	  	menu.setPreferredSize(new Dimension(901, 110));
		menu.setMinimumSize(new Dimension(901, 110));
		menu.setMaximumSize(new Dimension(901, 110));

	  		
	  	grid = new GridLayout(0,3);
	  	menu.setLayout(grid);

	  	gameStatus = new JLabel();
	  	gameStatus.setHorizontalAlignment( SwingConstants.CENTER );
	  	turnStatus = new JLabel();
	  	turnStatus.setHorizontalAlignment( SwingConstants.CENTER );
	  	turnPlayer = new JLabel();
	  	turnPlayer.setHorizontalAlignment( SwingConstants.CENTER );
	  	
	  	select = new JButton("Select Slot");
	  	select.addActionListener(this);

	  	roll = new JButton("Roll Die");
	  	roll.addActionListener(this);
	  		
	  	up = new JButton("Move Up");
	  	up.addActionListener(this);
	  		
	  	down = new JButton("Move Down");
	  	down.addActionListener(this);
	  		
	  	skip = new JButton("Skip State");
	  	skip.addActionListener(this);
	  		
	  	across = new JButton("Move Forward");
	  	across.addActionListener(this);

	  	menu.add( gameStatus );
	  	menu.add( turnPlayer );
	  	menu.add( turnStatus );
	  	menu.add( roll );
	  	menu.add( select );
	  	menu.add( skip );
		menu.add( up );
		menu.add( down );
		menu.add( across );
		
		getContentPane().add( board );
		getContentPane().add( menu );

		updateStatus();
		setVisible(true);
	}

	public void actionPerformed( ActionEvent e )
	{	
		JButton source = (JButton) e.getSource();
		if (source == select) {
			if (Weasels.DEBUG)
				System.out.println("Select Pressed");
			SelectionDialog sd = new SelectionDialog(this, game.getCurrentSelectableTotal());
			switch(game.getStateEnum()) {
				case SETTING_UP:
					game.addPiece(sd.getSelection() - 1); //Subtract 1 because rows are labeled 1 to 6, but stored 0 to 5
					break;
				case PLAYING:
					game.setSelected(sd.getSelection());
					break;
			}
		}
		if (source == roll) {
			if (Weasels.DEBUG)
				System.out.println("Roll Pressed");
			game.rollDie();
		}
		if (source == skip) {
			if (Weasels.DEBUG)
				System.out.println("Skip Pressed");
			game.skipState();
		}
		if (source == up) {
			if (Weasels.DEBUG)
				System.out.println("Up Pressed");
			game.movePiece(game.getSelected(), Board.Direction.UP);
		}
		if (source == down) {
			if (Weasels.DEBUG)
				System.out.println("Down Pressed");
			game.movePiece(game.getSelected(), Board.Direction.DOWN);
		}
		if (source == across) {
			//Forward
			if (Weasels.DEBUG)
				System.out.println("Forward Pressed");
			game.movePiece(game.getSelected(), Board.Direction.FORWARD);
		}

		updateStatus();
	}

	public void updateStatus()
	{
		gameStatus.setText(String.format("Status: %s", game.getState()));
		if (game.getRoll() != -1) {
			turnPlayer.setText(String.format("Player %d's Turn Roll: %d", game.getTurnPlayer(), game.getRoll()));
		} else {
			turnPlayer.setText(String.format("Player %d's Turn", game.getTurnPlayer()));
		}
		turnStatus.setText(String.format("Turn Status: %s", game.getTurnState()));
		switch(game.getStateEnum()) {
			case SETTING_UP:
				//Most buttons should be disabled
				up.setEnabled(false);
				down.setEnabled(false);
				skip.setEnabled(false);
				across.setEnabled(false);
				select.setEnabled(true);
				roll.setEnabled(false);
				break;
			case PLAYING:
				//Several buttons should be disabled until they are set by the selectableGameplay Buttons
				up.setEnabled(false);
				down.setEnabled(false);
				skip.setEnabled(false);
				across.setEnabled(false);
				select.setEnabled(true);
				roll.setEnabled(true);
				setSelectableGameplayButtons(game.getSelected());
				break;
			case WON:
				//All buttons should be disabled
				up.setEnabled(false);
				down.setEnabled(false);
				skip.setEnabled(false);
				across.setEnabled(false);
				select.setEnabled(false);
				roll.setEnabled(false);
				break;
		}
		board.repaint();
	}

	private void setSelectableGameplayButtons( int selection)
	{
		if (Weasels.DEBUG)
			System.out.println("Selection: " + selection);
		switch(game.getTurnStateEnum()) {
			case ROLL_DIE:
				//Several buttons should be disabled
				up.setEnabled(false);
				down.setEnabled(false);
				skip.setEnabled(false);
				across.setEnabled(false);
				select.setEnabled(false);
				roll.setEnabled(true);
				break;
			case VERTICAL:
				//Several buttons should be disabled
				up.setEnabled(false);
				down.setEnabled(false);
				skip.setEnabled(true);
				across.setEnabled(false);
				select.setEnabled(true);
				roll.setEnabled(false);
				//Check for possible movements with current selection
				if (selection != -1) {
					if (game.isValidMove(selection, Board.Direction.UP))
						up.setEnabled(true);
					if (game.isValidMove(selection, Board.Direction.DOWN))
						down.setEnabled(true);
				}
				break;
			case HORIZONTAL:
				//Several buttons should be disabled
				up.setEnabled(false);
				down.setEnabled(false);
				skip.setEnabled(false);
				across.setEnabled(false);
				select.setEnabled(true);
				roll.setEnabled(false);
				//Check for possible movements with current selection
				if (selection != -1) {
					if (game.isValidMove(selection, Board.Direction.FORWARD))
						across.setEnabled(true);
				}
				if (!(game.getPiecesInRow(game.getRoll() - 1)));
					skip.setEnabled(true);
				break;
		}
		
	}
}

class BoardGUI extends JPanel
{
	private Game game;
	public BoardGUI( Game nGame)
	{
		game = nGame;
		setPreferredSize(new Dimension(901, 601));
		setMinimumSize(new Dimension(901, 601));
		setMaximumSize(new Dimension(901, 601));
		//setBorder(BorderFactory.createLineBorder(Color.black));
	}

	public void paintComponent(Graphics gr)
  	{
  		super.paintComponent(gr);
  		gr.setFont(new Font("Arial", Font.PLAIN, 12));
  		if (game.getRoll() != -1) {
  			gr.setColor(Color.CYAN);
  			gr.fillRect(0, 100 * (game.getRoll() - 1), 901, 100);
  		}
  		gr.setColor(Color.BLACK);
  		int x = 0;
  		for(int i = 0; i < 900; i += 100)
  		{
  			for(int p = 0; p < 600; p += 100)
  			{
  				gr.drawRect(x, p, 100, 100);
  			}
  			x += 100;
  		}
  		gr.drawString("Start", 30, 15);
  		gr.drawString("End", 840, 15);

  		//Add pieces to board
  		Slot[][] board = game.getBoard();
  		for(x = 0; x < board.length; x++) {
			for(int y = 0; y < board[0].length; y++) {
				gr.drawString(board[x][y].toString(), 10 + x * 100, 90 + y  * 100);
			}
		}

		//Add selection numbers
		int[][] selectable = game.getSelectable();
		for(x = 0; x < selectable.length; x++) {
			for(int y = 0; y < selectable[0].length; y++) {
				if (selectable[x][y] != 0) {
					if (selectable[x][y] == game.getSelected()) {
						gr.setColor(Color.RED);
					} else {
						gr.setColor(Color.BLUE);
					}
					gr.setFont(new Font("Arial", Font.BOLD, 16));
					gr.drawString(String.format("%d",selectable[x][y]), 44 + x * 100, 54 + y  * 100);
				}
			}
		}
  	}

}

class SelectionDialog extends JDialog implements ActionListener
{
	private int selection = 0;
	private JButton[] selectionButtons;

	public SelectionDialog( Frame frame, int options )
	{
		super(frame, "Select Slot", true);
		setBounds(100, 100, 200, (options + (options % 2)) * 30);
		getContentPane().setLayout(new GridLayout(0,2));
		selectionButtons = new JButton[options];
		for (int i=0; i < options; i++) {
			JButton tmpButton = new JButton("" + (i + 1));
			tmpButton.addActionListener(this);
			getContentPane().add(tmpButton);
		}
		setVisible(true);
	}

	public void actionPerformed( ActionEvent e )
	{
		//if (e.getSource() == ok)
			//answer = answerField.getText();
		selection = (int) Integer.parseInt(((JButton) e.getSource()).getText());
		dispose();
	}

	public int getSelection()
	{
		return selection;
	}
}

class WinQuit extends WindowAdapter
{
	public void windowClosing( WindowEvent e )
	{
		System.exit(0);
	}
}