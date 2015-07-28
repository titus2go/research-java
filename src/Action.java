
public class Action {
	
	private String myAction;
	private int myID;
	
	public Action(final String theAction, int theID)
	{
		myAction = theAction;
		myID = theID;
	}
	
	public String getName()
	{
		return myAction;
	}
	
	public int getID()
	{
		return myID;
	}

}
