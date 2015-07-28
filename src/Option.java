
public class Option {
	
	private int myID;
	private int myStatus;
	private String myName;
	
	public Option(int theID, int theStatus, final String theName)
	{
		myID = theID;
		myStatus = theStatus;
		myName = theName;
	}
	
	public Option(int theID, int theStatus)
	{
		myID = theID;
		myStatus = theStatus;
		myName = "";
	}
	
	public String getName()
	{
		return myName;
	}
	
	public int getID()
	{
		return myID;
	}
	
	public int getStatus()
	{
		return myStatus;
	}
	

}
