/**
 * @(#)Game.java
 * Game logic
 */
public class Game
{
	//Instance Variables
	private int players; //Number of players in the game
	private enum State { SETTING_UP, PLAYING, WON };
	private State gameState;
	private Board gameBoard;

	//Player info
	private int currentPlayerId;
	private enum TurnState { VERTICAL, HORIZONTAL };
	private TurnState currentTurnState;
	private int currentRow;

	//Public Methods
	public Game( int newPlayers )
	{
		if (newPlayers < 1) {
			IO.showMsg("Not enough players!");
			return;
		}
		players = newPlayers; //Set the number of players
		gameState = State.SETTING_UP; //Start the game in the setting up state

		gameBoard = new Board();
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

	public int getTurnPlayer()
	{
		return currentPlayerId;
	}

	//Methods needed during setup
	public void addPiece( int row )
	{
		if (gameState != State.SETTING_UP)
			return;
		gameBoard.addPiece(Board.START_COL, row, currentPlayerId);
		updateState();
	}

	//Methods needed during gameplay
	public void movePiece( int x, int y, Board.Direction direction ) {
		gameBoard.movePiece(x, y, direction);
		updateState();
	}

	public int rollDie()
	{
		currentRow = (int) ((Math.random() * 6) + 1);
		return currentRow;
	}

	public int getRoll()
	{
		//Not entirely neccessary
		return currentRow;
	}

	public void skipState()
	{
		currentTurnState = TurnState.HORIZONTAL;
	}

	public String getTurnState()
	{
		//Return the current state of the turn as a string, either vertical or horizontal
		switch(currentTurnState) {
			case VERTICAL:
				return("Vertical (Optional)");
			case HORIZONTAL:
				return("Horizontal");
		}
		return("Error"); // This should never be reached. If it was, there are some pretty bad issues.
	}

	//Private Methods
	private void updateState()
	{
		//Logic to advance the game foward
		switch(gameState) {
			case SETTING_UP:
				if (gameBoard.getTotalPieces() == (players * 4)) {
					//Change to the playing state if all pieces are on the board
					gameState = State.PLAYING;
				}
				nextPlayer(); //Each player only gets one turn to place one piece on this state
				break;
			case PLAYING:
				if (gameBoard.getWinningPlayer() != -1) {
					//Someone won!
					gameState = State.WON;
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
		if (currentPlayerId > players)
			currentPlayerId = 0;
	}
}