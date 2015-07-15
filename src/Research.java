
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
import java.net.MalformedURLException;
import java.net.URL;

import org.json.*;


/**
 * Created by tituscheng on 7/14/15.
 */
public class Research {

    private String url = "http://localhost:8080/";
    private String userID = "";
    private String userName = "";
    private String password = "";
    private boolean isSignedIn;
    
    public Research()
    {
    	isSignedIn = false;
    }
    
    public String getUserID()
    {
    	return userID;
    }
    
    public String getUserName()
    {
    	return userName;
    }
    
    public String getPassword()
    {
    	return password;
    }

    //Create a new user, good for new user signup 
    public JSONObject signup(final String username, final String password){
    	final String api = "api/signup";
    	JSONObject userSignupInfo = new JSONObject();
    	userSignupInfo.put("username", username);
		userSignupInfo.put("password", password);
		
		//call api
		JSONObject result = new JSONObject(callAPI(userSignupInfo, api));
		
		//Result
		return result;
    }
    
    //Supply this method with a username and password
    //Return: user id if successful
    public JSONObject signin(final String theUsername, final String thePassword) {
    	
	    	final String api  = "api/signin";
	    	
	    	JSONObject userSigninInfo = new JSONObject();
	    	userSigninInfo.put("username", theUsername);
			userSigninInfo.put("password", thePassword);
			
			JSONObject userinfo = new JSONObject(callAPI(userSigninInfo, api));
			if(userinfo.getBoolean("status") == true) {
				userID =  userinfo.getString("id");
				password = thePassword;
				userName = theUsername;
			}
			return userinfo;
    }
    
    public JSONObject getSurveyIDS(final String userid)
    {
    	final String api = "api/getsurveyids";
    	
    	JSONObject myinfo = new JSONObject();
    	myinfo.put("userid", userid);
    	
    	return new JSONObject(callAPI(myinfo, api));
    }
    
//    public JSONArray getSurveyResult(final String userid, final String surveyid)
//    {
//    	
//    }
    
    
    //Submitting survey
    public JSONObject submitSurvey(final String userid, final String startTime, final String endTime, final JSONArray questions)
    {
    	final String api = "api/submitsurvey";
    	
    	JSONObject survey = new JSONObject();
    	survey.put("userid", userid);
    	survey.put("starttime", startTime);
    	survey.put("endtime", endTime);
    	survey.put("survey", questions);
    	
    	
    	JSONObject result = new JSONObject(callAPI(survey, api));
    	System.out.println("status: " + result.getBoolean("status") + "  message: " + result.getString("message"));
    	return result;
    	
    	
    }
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
