/**
 * @(#)Slot.java
 * Store data on each slot
 */
import java.util.*;

public class Slot
{
	//Instance Variables
	private ArrayList<Integer> pieces = new ArrayList<Integer>();

	//Public Methods
	public void add( int playerId )
	{
		pieces.add( new Integer(playerId) );
	}

	public int getHeight()
	{
		return pieces.size();
	}

	public int getTop()
	{
		//Get the id of the top piece
		if (pieces.size() > 0) {
			return pieces.get(pieces.size() - 1);
		}
		return 0;
	}

	public int getPlayerPiecesCount( int playerId )
	{
		int count = 0;
		for(int i = 0; i < pieces.size(); i++)
		{
			if (pieces.get(i) == playerId)
				count++;
		}
		return count;
	}

	public void removeTop()
	{	
		//Remove the top piece
		pieces.remove( pieces.size() - 1);
	}

	public String toString()
	{
		String s = "";

 		for(int i = 0; i < pieces.size(); i++)
 		{
 			s += pieces.get(i) + " ";
 		}
 		return s;
	}
}