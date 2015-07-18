//User is a class that abstracts a survey taker

import java.util.ArrayList;
import java.util.List;

import org.json.*;

public class User {
	
	private String mySessionID = "";
	private List<String> mySurveyIDS;
	
	private Research myResearch;
	
	private String myUsername;
	private String myPassword;
	
	private JSONObject receivedData;
	
	private boolean isSignedIn;
	
	public User(final String theUsername, final String thePassword)
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
	
	public boolean submitSurvey(final String theStartTime, final String theEndTime, final List<Question> theQuestionList)
	{
		if(isSignedIn) {
			JSONArray qlist = new JSONArray();
			for(int i = 0; i < theQuestionList.size(); i++){
				final Question theQ = theQuestionList.get(i);
				final String q = "{'symptom':" + theQ.getText() + ", 'status':" + theQ.getAnswer() + "}";
				System.out.println(q);
				JSONObject jq = new JSONObject(q);
				qlist.put(jq);
			}
			JSONObject result = myResearch.submitSurvey(mySessionID, theStartTime, theEndTime, qlist);
			if(result.getBoolean("status")) {
				System.out.println(result.getString("message"));
				return true;
			} else {
				System.out.println(result.getString("message"));
				return false;
			}
		} else {
			System.out.println("You must be signed in first");
			return false;
		}
	}
	
	public Survey getSurveyResult(final String theSurveyID)
	{
		Survey survey = null;
		if(isSignedIn){
			receivedData = myResearch.getSurveyResult(mySessionID, theSurveyID);
			if(receivedData.getBoolean("status")) {
				List<Question> questionList = new ArrayList<Question>();
				JSONArray questions = receivedData.getJSONArray("survey");
				for(int i = 0; i < questions.length(); i++) {
					final JSONObject question = questions.getJSONObject(i);
					final String text = question.getString("SymptomName");
					final int answer = question.getInt("SymptomStatus");
					Question newQuestion = new Question(text, answer);
					questionList.add(newQuestion);
				}
				survey = new Survey(theSurveyID, questionList);
				
			}
		}
		return survey;
		
	}
	
	public List<String> getSurveyIDS()
	{
		
		if(isSignedIn) {
			mySurveyIDS = new ArrayList<String>();
			receivedData = myResearch.getSurveyIDS(mySessionID);
			if(receivedData.getBoolean("status")) {
				JSONArray list = receivedData.getJSONArray("id");
				final List<String> temp = new ArrayList<String>();
				for(int i = 0; i < list.length(); i++) {
					JSONObject surveyid = list.getJSONObject(i);
					temp.add(surveyid.getString("SurveyID"));
					System.out.println("survey ids: " + temp.get(i));
				}
				mySurveyIDS = temp;
				return mySurveyIDS;
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
