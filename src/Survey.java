//Survey is a class that abstracts a survey
//A survey can contain many different questions;

import java.util.List;

public class Survey {
	private String mySurveyID = "";
	private List<Question> myQuestions;
	public Survey(final String theSurveyID, final List<Question> theQuestionList)
	{
		myQuestions = theQuestionList;
		mySurveyID = theSurveyID;
	}
	
	public String getID()
	{
		return mySurveyID;
	}
	
	public List<Question> getQuestions()
	{
		return myQuestions;
	}
	
}
