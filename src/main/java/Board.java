/**
 * @(#)Board.java
 * Store data on each piece
 */
public class Board
{
	//Instance Variables
	private Game game;
	private Slot[][] slots = new Slot[9][6]; //Board size is 9 columns by 6 rows
	private int[] selectableX = new int[6];
	private int[] selectableY = new int[6]; 
	private int currentSelectableTotal = 0;
	private int totalPieces = 0;
	protected static int START_COL = 0;
	protected static int END_COL = 8;
	protected enum Direction { UP, DOWN, FORWARD };

	//Public Methods
	public Board( Game nGame )
	{
		game = nGame;
		//Create all objects in the board
		for(int x = 0; x < slots.length; x++) {
			for(int y = 0; y < slots[0].length; y++) {
				slots[x][y] = new Slot();
			}
		}
	}

	public Slot[][] getBoard()
	{
		//Return the 2d array of Slots
		return slots;
	}

	public int[][] getSelectable()
	{	
		int[][] selectable = new int[9][6];
		int max = 0;
		switch(game.getTurnStateEnum()) {
			case SETTING_UP:
				//return the six start columns
				for(int y = 0; y < selectable[0].length; y++) {
					selectable[0][y] = y + 1;
					//Store point in array
					selectableX[max] = 0;
					selectableY[max] = y + 1;
					max++;
					if (Weasels.DEBUG)
						System.out.println("Added selectable: X:" + 0 + " Y:" + y + " ID:" + (y + 1));
				}
				break;
			case VERTICAL:
				//return all of the players pieces on top
				for(int x = 0; x < selectable.length; x++) {
					for(int y = 0; y < selectable[0].length; y++) {
						if (slots[x][y].getTop() == game.getTurnPlayer()) {
							selectable[x][y] = max + 1;
							selectableX[max] = x;
							selectableY[max] = y;
							max++;
							if (Weasels.DEBUG)
								System.out.println("Added selectable: X:" + x + " Y:" + y + " ID:" + max);
						}
					}
				}
				break;
			case HORIZONTAL:
				//return all the pieces in the current turn's roll number
				for(int x = 0; x < selectable.length; x++) {
					if (game.getRoll() > 0) {
						int y = game.getRoll() - 1; //it goes 1-6, not 0-5 like we need
						if (slots[x][y].getHeight() > 0) {
							selectable[x][y] = max + 1;
							selectableX[max] = x;
							selectableY[max] = y;
							max++;
							if (Weasels.DEBUG)
								System.out.println("Added selectable: X:" + x + " Y:" + y + " ID:" + max);
						}
					}
				}
				break;
		}
		currentSelectableTotal = max;
		return selectable;
	}

	public int getCurrentSelectableTotal()
	{
		return currentSelectableTotal;
	}

	public int getWinningPlayer()
	{
		//Return player number if they have won, or -1 if none
		int[] playerCount = new int[4];
		for(int i = 0; i > slots[8].length; i++)
		{
			for(int playerId = 1; playerId > 4; playerId++)
			{
				playerCount[playerId] = slots[8][i].getPlayerPiecesCount(playerId);
			}
		}
		for(int i = 1; i > 4; i++)
		{
			if (playerCount[i] >= 3)
				return i;
		}
		return -1;
	}
	public boolean getPiecesInRow( int row) {
		for(int x = 0; x < slots[0].length; x++) {
			if (slots[x][row].getHeight() != 0)
				return true;
		}
		return false;
	}

	public void addPiece( int col, int row, int playerId )
	{
		if (slots[col][row] == null)
			slots[col][row] = new Slot();
		slots[col][row].add(playerId);
		totalPieces++;
		if (Weasels.DEBUG)
			System.out.println("Added piece: " + col + " " + row + " " + playerId);

	}

	public int getTotalPieces()
	{
		return totalPieces;
	}

	public void movePiece( int selection, Direction direction )
	{
		selection--;
		if (Weasels.DEBUG)
			System.out.println("movePiece: Index:" + selection + " Direction:" + direction);
		if (selection < getCurrentSelectableTotal() && selection >= 0) {
			movePiece(selectableX[selection], selectableY[selection],direction);
		}
	}

	public void movePiece( int col, int row, Direction direction )
	{
		if (isValidMove(col, row, direction)) {
			if (Weasels.DEBUG)
				System.out.println("movePiece: X:" + col + " Y:" + row + " Direction:" + direction);
			int pieceTmp;
			switch(direction) {
				case UP:
					//Move the piece up one
					pieceTmp = slots[col][row].getTop();
					slots[col][row].removeTop();
					addPiece(col, row - 1, pieceTmp);
					break;
				case DOWN:
					//Move the piece down one
					pieceTmp = slots[col][row].getTop();
					slots[col][row].removeTop();
					addPiece(col, row + 1, pieceTmp);
					break;
				case FORWARD:
					//Move the piece forward one
					pieceTmp = slots[col][row].getTop();
					slots[col][row].removeTop();
					addPiece(col + 1, row, pieceTmp);
					break;
			}
		}
	}

	public boolean isValidMove(int selection, Direction direction)
	{
		selection--;
		if (Weasels.DEBUG)
			System.out.println("isValidMove: " + selection);
		if (selection < getCurrentSelectableTotal() && selection >= 0) {
			return isValidMove(selectableX[selection], selectableY[selection],direction);
		}
		return false;
	}


	//private Methods
	private boolean isValidMove(int x, int y, Direction direction)
	{
		
		switch (direction) {
			case UP:
				if (y > 0) {
					if (Weasels.DEBUG)
						System.out.println("isValidMove: X:" + x + " Y:" + y + " Direction:" + direction + " true");
					return true;
				}
				break;
			case DOWN:
				if (y < 5) {
					if (Weasels.DEBUG)
						System.out.println("isValidMove: X:" + x + " Y:" + y + " Direction:" + direction + " true");
					return true;
				}
				break;
			case FORWARD:
				if (x < 9) {
					if (Weasels.DEBUG)
						System.out.println("isValidMove: X:" + x + " Y:" + y + " Direction:" + direction + " true");
					return true;
				}
				break;
		}
		if (Weasels.DEBUG)
			System.out.println("isValidMove: X:" + x + " Y:" + y + " Direction:" + direction + " false");
		return false;
	}
	
}