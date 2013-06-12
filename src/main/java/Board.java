/**
 * @(#)Board.java
 * Store data on each piece
 */
public class Board
{
	//Instance Variables
	private Slot[][] slots = new Slot[9][6]; //Board size is 9 columns by 6 rows
	private int totalPieces = 0;
	protected static int START_COL = 0;
	protected static int END_COL = 8;
	protected enum Direction { UP, DOWN, FORWARD };

	//Public Methods

	public int[][] getBoardDisplay()
	{
		//Return a 2d array, with the number that should be displayed on top of each square
		//TODO
		return new int[1][1];
	}

	public int getWinningPlayer()
	{
		//Return player number if they have won, or -1 if none
		//TODO
		return -1;
	}

	public void addPiece( int col, int row, int playerId )
	{
		slots[col][row].add(playerId);
		totalPieces++;
	}

	public int getTotalPieces()
	{
		return totalPieces;
	}

	public void movePiece( int col, int row, Direction direction )
	{
		//TODO: Error checking needed
		int pieceTmp;
		switch(direction) {
			case UP:
				//Move the piece up one
				pieceTmp = slots[col][row].getTop();
				slots[col][row].removeTop();
				slots[col][row - 1].add(pieceTmp);
				break;
			case DOWN:
				//Move the piece down one
				pieceTmp = slots[col][row].getTop();
				slots[col][row].removeTop();
				slots[col][row + 1].add(pieceTmp);
				break;
			case FORWARD:
				//Move the piece forward one
				pieceTmp = slots[col][row].getTop();
				slots[col][row].removeTop();
				slots[col + 1][row].add(pieceTmp);
				break;
		}
	}

	//Private Methods
}