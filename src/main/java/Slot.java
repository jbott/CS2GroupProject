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
		return pieces.get(pieces.size());
	}

	public void removeTop()
	{	
		//Remove the top piece
		pieces.remove( pieces.size() );
	}
}