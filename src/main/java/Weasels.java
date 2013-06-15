/**
 * @(#)Weasels.java
 * @author Blue Team
 */
public class Weasels
{
	protected static boolean DEBUG = false;
	protected static boolean GUI = true;
	protected static boolean AUTOPOPULATE = false;

	public static void main( String[] args )
	{
		for(String s : args ) {
			if (s.equals("-debug" )) {
				System.out.println("Debug mode enabled");
				DEBUG = true;
			}
			if (s.equals("-nogui" )) {
				System.out.println("GUI disabled");
				GUI = false;
			}
			if (s.equals("-autopopulate")) {
				System.out.println("Autopopulate enabled");
				AUTOPOPULATE = true;
			}
		}
		//Main method, should start entire game
		//initialize main game logic
		Game weaselsGame = new Game(3); //Initialize the game logic for 3 players

		//Autopopulate board with some things if set
		if(AUTOPOPULATE) {
			Simple_Graphics.populate(weaselsGame);
			weaselsGame.rollDie();
		}

		//Only run simple graphics test in nogui mode
		if (!GUI) {
			Simple_Graphics test = new Simple_Graphics(weaselsGame);
		}
		
		//initialize gui if not in nogui mode
		if (GUI) {
			GUI gui = new GUI(weaselsGame);

		}

		//start game
		
	}
}
