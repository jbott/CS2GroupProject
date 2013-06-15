/**
 * @(#)Simple_Graphics.java
 * Game logic
 */

public class Simple_Graphics
{
	private Game game;
	public Simple_Graphics( Game nGame)
	{
		game = nGame;
		System.out.println(game.getState());
		showBoard(game.getBoard());

		populate(game);

		System.out.println(game.getState());

		showBoard(game.getBoard());
		game.movePiece(0,5,Board.Direction.FORWARD);
		showBoard(game.getBoard());

		showBoard(game.getBoard());
		game.movePiece(0,3,Board.Direction.DOWN);
		showBoard(game.getBoard());

		showBoard(game.getBoard());
		game.movePiece(0,3,Board.Direction.UP);
		showBoard(game.getBoard());
		//showBoard(game.getSelectable());
	}

	public void showBoard(Slot[][] slots)
	{
		String boardString = "\n";
		String[] board = {"", "", "", "", "", ""};
		for(int x = 0; x < slots.length; x++) {
			for(int y = 0; y < slots[0].length; y++) {
				board[y] += slots[x][y].getTop() + " ";
			}
		}
		for(int i = 0; i < board.length; i++) {
			boardString += board[i] + "\n";
		}
		System.out.println(boardString);
	}

	public void showBoard(int[][] slots)
	{
		String boardString = "\n";
		String[] board = {"", "", "", "", "", ""};
		for(int x = 0; x < slots.length; x++) {
			for(int y = 0; y < slots[0].length; y++) {
				board[y] += slots[x][y] + " ";
			}
		}
		for(int i = 0; i < board.length; i++) {
			boardString += board[i] + "\n";
		}
		System.out.println(boardString);
	}

	public static void populate( Game game )
	{
		//Add 12 pieces to the board
		game.addPiece(0);
		game.addPiece(1);
		game.addPiece(2);
		game.addPiece(3);
		game.addPiece(4);
		game.addPiece(5);
		game.addPiece(0);
		game.addPiece(1);
		game.addPiece(2);
		game.addPiece(3);
		game.addPiece(4);
		game.addPiece(5);
	}
}