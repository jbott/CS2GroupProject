/**
 * @(#)Game.java
 * Game logic
 */
public class Game
{
	//Instance Variables
	private int players; //Number of players in the game
	protected enum State { SETTING_UP, PLAYING, WON };
	private State gameState;
	private Board gameBoard;

	//Player info
	private int currentPlayerId, currentRow, selected;
	protected enum TurnState { SETTING_UP, ROLL_DIE, VERTICAL, HORIZONTAL };
	private TurnState currentTurnState;

	//Public Methods
	public Game( int newPlayers )
	{
		if (newPlayers < 1) {
			System.out.println("Not enough players!");
			return;
		}
		players = newPlayers; //Set the number of players
		gameState = State.SETTING_UP; //Start the game in the setting up state
		currentTurnState = TurnState.SETTING_UP;
		currentRow = -1;

		gameBoard = new Board(this);
	}

	public String getState()
	{
		//Return the current state of the game as a string, either setting up, playing, or won
		switch(gameState) {
			case SETTING_UP:
				return("Setting up");
			case PLAYING:
				return("Playing");
			case WON:
				return("Won");
		}
		return("Error"); // This should never be reached. If it was, there are some pretty bad issues.
	}

	public State getStateEnum()
	{
		return gameState;
	}

	public int getTurnPlayer()
	{
		return currentPlayerId + 1;
	}

	//Methods needed during setup
	public void addPiece( int row )
	{
		if (gameState != State.SETTING_UP)
			return;
		if ( 0 <= row && row < 6) {
			//Valid row
			gameBoard.addPiece(Board.START_COL, row, getTurnPlayer());
			updateState();
		}
	}

	//Methods needed during gameplay
	public void movePiece( int x, int y, Board.Direction direction )
	{
		gameBoard.movePiece(x, y, direction);
		advanceTurnState();
		updateState();
	}
	public void movePiece( int selection, Board.Direction direction)
	{
		gameBoard.movePiece(selection, direction);
		advanceTurnState();
		updateState();
	}

	public void rollDie()
	{
		currentRow = (int) ((Math.random() * 6) + 1);
		if (Weasels.DEBUG)
			System.out.println("Player " + getTurnPlayer() + " Rolled " + currentRow);
		updateState();
	}

	public int getRoll()
	{
		return currentRow;
	}

	public void advanceTurnState()
	{
		selected = -1; //Make sure selected is reset every time
		switch(currentTurnState) {
			case ROLL_DIE:
				if (Weasels.DEBUG)
					System.out.println("advanceTurnState: ROLL_DIE > VERTICAL");
				currentTurnState = TurnState.VERTICAL;
				break;
			case VERTICAL:
				if (Weasels.DEBUG)
					System.out.println("advanceTurnState: VERTICAL > HORIZONTAL");
				currentTurnState = TurnState.HORIZONTAL;
				break;
			case HORIZONTAL:
				if (Weasels.DEBUG)
					System.out.println("advanceTurnState: HORIZONTAL > nextPlayer()");
				nextPlayer();
				break;
		}
	}

	public void skipState()
	{	
		if (currentTurnState == TurnState.VERTICAL && (currentTurnState == TurnState.HORIZONTAL && gameBoard.getCurrentSelectableTotal() == 0));
			advanceTurnState();
		updateState();
	}

	public String getTurnState()
	{
		//Return the current state of the turn as a string, either vertical or horizontal
		switch(currentTurnState) {
			case VERTICAL:
				return("Vertical (Optional)");
			case HORIZONTAL:
				return("Horizontal");
			case SETTING_UP:
				return("Adding Pieces");
			case ROLL_DIE:
				return("Roll Die");
		}
		return("Error"); // This should never be reached. If it was, there are some pretty bad issues.
	}

	public TurnState getTurnStateEnum()
	{
		return currentTurnState;
	}

	public Slot[][] getBoard()
	{
		//Return the current board state
		return gameBoard.getBoard();
	}

	public int[][] getSelectable()
	{
		//Return the current selectable pieces
		return gameBoard.getSelectable();
	}

	public int getCurrentSelectableTotal()
	{
		return gameBoard.getCurrentSelectableTotal();
	}

	public void setSelected( int nSelected )
	{
		if (Weasels.DEBUG) 
			System.out.println("Selected: " + nSelected);
		selected = nSelected;
	}

	public int getSelected()
	{
		return selected;
	}

	public boolean isValidMove( int selection, Board.Direction direction )
	{
		return gameBoard.isValidMove(selection, direction);
	}

	public boolean getPiecesInRow( int row) {
		return gameBoard.getPiecesInRow(row);
	}

	//Private Methods
	private void updateState()
	{
		//Logic to advance the game foward
		if (Weasels.DEBUG) {
			System.out.println("Game State: " +getState());
			System.out.println("Turn State: " + getTurnState());
		}
		switch(gameState) {
			case SETTING_UP:
				if (gameBoard.getTotalPieces() == (players * 4)) {
					//Change to the playing state if all pieces are on the board
					gameState = State.PLAYING;
					currentTurnState = TurnState.ROLL_DIE;
				}
				nextPlayer(); //Each player only gets one turn to place one piece on this state
				break;
			case PLAYING:
				if (gameBoard.getWinningPlayer() != -1) {
					//Someone won!
					gameState = State.WON;
				}
				switch(currentTurnState) {
					case ROLL_DIE:
						if (currentRow != -1)
							advanceTurnState();
						break;
				}
				break;
			case WON:
				//No logic to do, really, just kinda sit here. maybe offer a restart?
				break;
		}
	}

	private void nextPlayer()
	{
		//TODO: This may be wrong. Check it functions correctly, and remove this comment if true
		currentPlayerId++;
		if (currentPlayerId == players)
			currentPlayerId = 0;
		if (gameState != State.SETTING_UP) {
			currentRow = -1;
			selected = -1;
			currentTurnState = TurnState.ROLL_DIE;
		}
	}
}