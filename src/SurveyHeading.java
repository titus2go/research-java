
public class SurveyHeading {
	private String myStartDate = "";
	private String myEndDate = "";
	private String mySurveyID = "";

	public SurveyHeading(final String theStartDate, final String theEndDate, final String theSurveyID)
	{
		myStartDate = theStartDate;
		myEndDate = theEndDate;
		mySurveyID = theSurveyID;
	}
	
	public SurveyHeading(final String theStartDate, final String theEndDate)
	{
		myStartDate = theStartDate;
		myEndDate = theEndDate;
	}
	
	public String getSurveyID()
	{
		return mySurveyID;
	}
	
	public String getStartDate()
	{
		return myStartDate;
	}
	
	public String getEndDate()
	{
		return myEndDate;
	}
	
	
}
