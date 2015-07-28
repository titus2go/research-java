
//import org.apache.http.client.HttpClient;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.NameValuePair;
//import java.util.List;
//import java.util.ArrayList;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import java.io.UnsupportedEncodingException;
//import org.apache.http.HttpResponse;
//import android.util.Log;
//import java.io.IOException;
//import org.apache.http.client.ClientProtocolException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.*;


/**
 * Created by tituscheng on 7/14/15.
 */
public class Research {

    private String localTestUrl = "http://localhost:8080/";
    private String stagingURL = "http://52.27.225.80:8080/";
    
    private String url = stagingURL;
    
    public Research()
    {

    }
    
 

    //Create a new user, good for new user signup 
    public JSONObject signup(final String username, final String password){
    	final String api = "api/signup";
    	
    	//Post object parameter
    	JSONObject userSignupInfo = new JSONObject();
    	userSignupInfo.put("username", username);
		userSignupInfo.put("password", password);
		
		//Response data as JSONobject
		return new JSONObject(callAPI(userSignupInfo, api));

    }
    
    //Login user using their name and password
    //Parameter:  theUsername(String), thePassword(String)
    //Return:  JSONObject for response object
    public JSONObject signin(final String theUsername, final String thePassword) {
    	
	    	final String api  = "api/signin";
	    	
	    	//Post object parameter
	    	JSONObject userSigninInfo = new JSONObject();
	    	userSigninInfo.put("username", theUsername);
			userSigninInfo.put("password", thePassword);
			
	
			return new JSONObject(callAPI(userSigninInfo, api));
    }
    
    public JSONObject getActions(final String theSessionID)
    {
    	final String api = "api/getactions";
    	
    	JSONObject myinfo = new JSONObject();
    	myinfo.put("sessionid", theSessionID);
    	
    	return new JSONObject(callAPI(myinfo, api));
    	
    }
    
    
    //Get all the survey id from a user
    //Parameter:  theSessionID(String)
    //Return:  JSONObject for response object
    public JSONObject getSurveyIDS(final String theSessionID)
    {
    	final String api = "api/getsurveyids";
    	
    	JSONObject myinfo = new JSONObject();
    	myinfo.put("sessionid", theSessionID);
    	
    	return new JSONObject(callAPI(myinfo, api));
    }
    
    //Get the survey result from a survey id
    //Parameter:  theSessionID(String), theSurveyID(String)
    //Return:  JSONObject for response object
//    public JSONObject getSurveyResult(final String theSessionID, final String theSurveyID)
//    {
//		final String api = "api/getsurveyresult";
//		
//		JSONObject data = new JSONObject();
//		data.put("surveyid", theSurveyID);
//		data.put("sessionid", theSessionID);
//		
//		return new JSONObject(callAPI(data, api));
//    }
    
    public JSONObject getQuestions(final String theSessionID, final String theSurveyID)
    {
    	final String api = "api/getquestions";
    	
    	JSONObject data = new JSONObject();
    	data.put("sessionid", theSessionID);
    	data.put("surveyid", theSurveyID);
    	
    	return new JSONObject(callAPI(data, api));
    	
    }
    
    public JSONObject getOptions(final String theSessionID, final String theQuestionID)
    {
    	final String api = "api/getoptions";
    	
    	JSONObject data = new JSONObject();
    	data.put("sessionid", theSessionID);
    	data.put("questionid", theQuestionID);
    	
    	return new JSONObject(callAPI(data, api));
    
    }

    
    
    //Submit a survey to the server
    //Parameter:  theSessionID(String), theStartTime(String), theEndTime(String), theQuestionList(String)
    //Return:  JSONObject for response object
    public JSONObject submitSurvey(final Survey theSurvey, final String theSessionID)
    {
    	final String api = "api/submitsurvey";
    	boolean levelOneCompleted = false;
    	JSONObject finalresponse = new JSONObject();

    	int qcount = 0; //quesiton count
    	int opcount = 0; //option count;
    	String surveyid = "";
    	
    	//Level 0
    	//WeeklySymptomSurvey
    	JSONObject level0 = new JSONObject();
    	level0.put("entrylevel", 0);
    	level0.put("starttime", theSurvey.getHeading().getStartDate());
    	level0.put("endtime", theSurvey.getHeading().getEndDate());
    	level0.put("sessionid", theSessionID);
    	
    	JSONObject stage1 = new JSONObject(callAPI(level0, api));
    	if(stage1.getBoolean("status"))
    	{
    		levelOneCompleted = true;
    		surveyid = stage1.getString("surveyid");
    	}
    	
    	//Level 1 & 2
    	//SurveyQuestionBridge
    	if(levelOneCompleted) 
    	{
    		List<Question> questions = theSurvey.getQuestions();
    		for(int i = 0; i < questions.size(); i++)
    		{
    			Question myQuestion = questions.get(i);
    			JSONObject myType = new JSONObject();
    			myType.put("entrylevel", 1);
    			myType.put("sessionid", theSessionID);
    			myType.put("surveyid", surveyid);
    			myType.put("questiontype", myQuestion.getType());
    			
    			JSONObject response = new JSONObject(callAPI(myType, api));
    			if(response.getBoolean("status")) {
    				qcount++;
    				final String questionid = response.getString("questionid");
    				
    				//level 2
    				JSONObject finalLevel = new JSONObject();
    				finalLevel.put("sessionid", theSessionID);
    				finalLevel.put("questionid", questionid);
    				finalLevel.put("entrylevel", 2);
    				
    				JSONArray jsonoption = new JSONArray();
    				List<Option> options = myQuestion.getOptions();
    				for(int j = 0; j < options.size(); j++)
    				{
    					Option op = options.get(j);
    					JSONObject json_op = new JSONObject();
    					json_op.put("type", op.getID());
    					json_op.put("status", op.getStatus());
    					jsonoption.put(j, json_op);
    				}
    				finalLevel.put("options", jsonoption);
    				
    				JSONObject optionResponse = new JSONObject(callAPI(finalLevel, api));
    				if(optionResponse.getBoolean("status")) {
    					opcount++;
    				}
    			}
    			
    		}
    		int totaloptions = 0;
    		for(Question q : questions)
    		{
    			totaloptions += q.getOptions().size();
    		}
    		if(qcount == questions.size() && opcount == totaloptions)
    		{
    			finalresponse.put("status",  true);
    			finalresponse.put("message", "Successfully submitted survey");
    		} else {
    			finalresponse.put("status",  false);
    			finalresponse.put("message", "Failed to submit survey");
    		}
    		return finalresponse;

    	}
    	
    	return null;
   	
    }
    
//    private JSONObject addEntryToWeeklySymptomSurvey(final String theStartTime, final String theEndTime, final String theSessionID)
//    {
//    	final String api = "api/submitsurvey";
//    	JSONObject survey = new JSONObject();
//    	survey.put("sessionid", theSessionID);
//    	survey.put("starttime", theStartTime);
//    	survey.put("endtime", theEndTime);
//    	survey.put("entrylevel", 0);
//    	
//    	return new JSONObject(callAPI(survey, api));
//    }
//    
//    private JSONObject addEntryToSurveyQuestionBridge(final String theSurveyID, final String theSessionID)
//    {
//    	final String api = "api/submitsurvey";
//    	JSONObject survey = new JSONObject();
//    	survey.put("sessionid", theSessionID);
//    	survey.put("surveyid", theSurveyID);
//    	survey.put("entrylevel", 1);
//    	
//    	return new JSONObject(callAPI(survey, api));
//    }
//    
//    private JSONObject addEntriesToSurvey(final String theQuestionID, JSONArray theQuestionList, final String theSessionID)
//    {
//    	final String api = "api/submitsurvey";
//    	JSONObject survey = new JSONObject();
//    	survey.put("sessionid", theSessionID);
//    	survey.put("questionid", theQuestionID);
//    	survey.put("survey", theQuestionList);
//    	survey.put("entrylevel", 2);
//    	
//    	return new JSONObject(callAPI(survey, api));
//    }
    //Get the symptom name with their corresponding id number
    //symptom id is used for constructing survey using json object
    public void getAllSymptoms()
    {
    	final String api = "api/getallsymptoms";

    	JSONArray result = new JSONArray(callAPI(new JSONObject(), api));
    	for(int i = 0; i < result.length(); i++) {
    		JSONObject row = result.getJSONObject(i);
    		System.out.println(row.get("SymptomID") + "   " +  row.get("SymptomName"));
    	}
    	
    }
    
    
    //A general method for calling api and receiving response
    private String callAPI(final JSONObject param, final String api)
    {
    	URL apiURL;
		try {
			
			apiURL = new URL(url + api);
			HttpURLConnection conn;
			
			conn = (HttpURLConnection) apiURL.openConnection();
	        
	        conn.setRequestMethod("POST");
	        conn.setDoOutput(true);
	        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(param.toString());
	        wr.flush();
	        
	        BufferedReader in = new BufferedReader(
			        new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
//			System.out.println(response.toString());
			return response.toString();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    
}
