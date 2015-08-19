//User is a class that abstracts a survey taker

import java.util.ArrayList;
import java.util.List;

import org.json.*;

public class User {
	
	private String mySessionID = "";
	private List<SurveyHeading> mySurveyHeadings;
	
	private Research myResearch;
	
	private String myUsername;
	private String myPassword;
	
	private JSONObject receivedData;
	
	private boolean isSignedIn;
	
	public static User getInstance(final String theUsername, final String thePassword)
	{
		if(instance == null){
			instance = new User();
		}
		return instance;
		
	}
	
	private static User instance = null;
	
	protected User() {
		
	}
	
	public void setUserInfo(final String theUsername, final String thePassword)
	{
		myUsername = theUsername;
		myPassword = thePassword;
		myResearch = new Research();
		isSignedIn = false;
	}
	
	
	public void signin()
	{
		receivedData = myResearch.signin(myUsername, myPassword);
		if(receivedData.getBoolean("status")) {
			mySessionID = receivedData.getString("sessionid");
			isSignedIn = true;
			System.out.println("Sign in successfull");
		} else {
			System.out.println("User not found, maybe use signup() first?");
		}
		
	}
	
	public boolean submitSurvey(final Survey theSurvey)
	{
		if(isSignedIn) {
			JSONObject response = myResearch.submitSurvey(theSurvey, mySessionID);
			return response.getBoolean("status");
		} else {
			System.out.println("You must be signed in first");
			return false;
		}
	}
	
	public Survey getSurvey(final SurveyHeading heading)
	{
		if(isSignedIn){
			Survey mySurvey = new Survey();
			mySurvey.setHeading(heading);
			List<Question> surveyquestions = new ArrayList<Question>();
			JSONObject response = myResearch.getQuestions(mySessionID, heading.getSurveyID());
			JSONArray questions = response.getJSONArray("questions");
			for(int i = 0 ; i < questions.length(); i++) 
			{
				JSONObject json_question = questions.getJSONObject(i);
				final String qID = json_question.getString("QuestionID");
				final int qType = json_question.getInt("QuestionType");
				final String qText = json_question.getString("QuestionText");
				Question q = new Question(qType, qID, qText);
				
				JSONObject options_response = myResearch.getOptions(mySessionID, q.getID());
				JSONArray options_array = options_response.getJSONArray("options");
				for(int j = 0; j < options_array.length(); j++)
				{
					JSONObject option = options_array.getJSONObject(j);
					final int opID = option.getInt("OptionID");
					final int opStatus = option.getInt("OptionStatus");
					final String opName = option.getString("OptionName");
					final int opType = option.getInt("OptionType");
					Option newOp = new Option(opID, opStatus, opName, opType);
					q.addOption(newOp);
				}
				mySurvey.addQuestion(q);
	//			System.out.println("QuestionID: " + qID + "  QuestionType: " + qType + "   QuestionText: " + qText);
			}
			return mySurvey;
		}
		return null;
	}
	
	
	public List<Action> getActions()
	{
		if(isSignedIn) {
			List<Action> actionList = new ArrayList<Action>();
			receivedData = myResearch.getActions(mySessionID);
			if(receivedData.getBoolean("status")) {
				JSONArray list = receivedData.getJSONArray("actions");
				for(int i = 0; i < list.length(); i++) 
				{
					JSONObject json_action = list.getJSONObject(i);
					Action newAction = new Action(json_action.getString("ActionName"), json_action.getInt("ActionID"));
					actionList.add(newAction);
				}
				return actionList;
			}
		}
		return null;
	}
	
	public List<SurveyHeading> getSurveyHeadings()
	{
		
		if(isSignedIn) {
			mySurveyHeadings = new ArrayList<SurveyHeading>();
			receivedData = myResearch.getSurveyIDS(mySessionID);
			if(receivedData.getBoolean("status")) {
				JSONArray list = receivedData.getJSONArray("id");
				final List<String> temp = new ArrayList<String>();
				for(int i = 0; i < list.length(); i++) {
					JSONObject surveyid = list.getJSONObject(i);
					SurveyHeading head = new SurveyHeading(surveyid.getString("StartTime"), surveyid.getString("EndTime"), surveyid.getString("SurveyID"));
					mySurveyHeadings.add(head);
				}
				return mySurveyHeadings;
			}
			return null;
			
		} else {
			System.out.println("Please sign in first, use signin() function");
			return null;
		}
	}
	
	public void signup()
	{
		receivedData = myResearch.signup(myUsername, myPassword);
		System.out.println(receivedData.getString("message"));
	}
	
	//Get the sessionid of the user
	//Note:  if sessionid is empty, the user does not exists
	public String getSessionID()
	{
		return mySessionID;
	}

}
