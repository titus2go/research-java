

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		final String test_username = "tester";
		final String test_password = "test1ng";
		
		User user = new User(test_username, test_password);
		user.signin();
		
		//test get survey
//		List<String> sid = user.getSurveyIDS();
//		Survey survey = user.getSurveyResult(sid.get(0));
//		System.out.println("Survey: " + survey.getID());
//		for(int i = 0 ; i < survey.getQuestions().size(); i++) {
//			Question q = survey.getQuestions().get(i);
//			System.out.println(q.toString());
//		}
		
		//test submit survey
		final String startTime = "2015-07-21 11:59:00";
		final String endTime = "2015-07-30 11:59:00";
		Question q1 = new Question("602", 1);
		Question q2 = new Question("603", 1);
		Question q3 = new Question("604", 0);
		Question q4 = new Question("605", 1);
		Question q5 = new Question("606", 0);
		Question q6 = new Question("607", 1);
		
		List<Question> questions = new ArrayList<Question>();
		questions.add(q1);
		questions.add(q2);
		questions.add(q3);
		questions.add(q4);
		questions.add(q5);
		questions.add(q6);
		
		user.submitSurvey(startTime, endTime, questions);
		
		
//		testGetSurveyResult(test_username, test_password);
//		testGetSurveyIDS(test_username, test_password);
//		testSignIn(test_username, test_password);
//		testSubmitSurvey(test_username, test_password);
//		testGetSurveyIDS(test_username, test_password);
//		Research newResearch = new Research();
//		newResearch.signin("tester2", "test2ng");
//		newResearch.getAllSymptoms();
		
//		JSONArray myQuestions = new JSONArray();
//		myQuestions.put(new JSONObject("{'symptom':601, 'status':1}"));
//		myQuestions.put(new JSONObject("{'symptom':602, 'status':1}"));
//		myQuestions.put(new JSONObject("{'symptom':604, 'status':0}"));
//		newResearch.submitSurvey("Ey6aLLCd", "2015-11-08 11:59:00", "2015-11-15 11:59:00", myQuestions);
	}
	
//	public static void testSignUp(final String username, final String password) {
//		Research testSignUp  = new Research();
//		User testNewUser  = testSignUp.signup(username, password);
//		if(testNewUser.getSessionID() != "") {
//			System.out.println("Test sign up new user.......................successfull");
//			System.out.println("Received Data: " + testNewUser.getSessionID());
//		} else {
//			System.out.println("Test sign up new user........................failed");
//		}
//	}
//	
//	public static void testSignIn(final String username, final String password) {
//		Research testSignin = new Research();
//		User testUser = testSignin.signin(username, password);
//		if(testUser != null) {
//			System.out.println("Test sign in.....................successfull");
//			System.out.println("Data received: " + testUser.getSessionID());
//		}
//
//	}
	
//	public static void testSubmitSurvey(final String username, final String password) {
//		Research research = new Research();
//		final String topic = "submit survey";
//		//By creating a json object array in this way, you can create your own custom survey;
//		JSONArray questions = new JSONArray();
//		questions.put(new JSONObject("{'symptom':602, 'status':1}"));
//		questions.put(new JSONObject("{'symptom':601, 'status':0}"));
//		questions.put(new JSONObject("{'symptom':604, 'status':1}"));
//		questions.put(new JSONObject("{'symptom':605, 'status':1}"));
//		
//		//retrieve user id
//		JSONObject result = research.signin(username, password);
//		if(result.getBoolean("status")) {
//			final String userid = result.getString("id");
//			if(userid != null) {
//				JSONObject submitResult = research.submitSurvey(userid, "2015-07-10 11:59:00", "2015-07-17 11:59:00", questions);
//				printStatus(submitResult, topic);
//			}
//		} else {
//			printStatus(result, topic);
//		}	
//	}
	
//	public static void testGetSurveyIDS(final String username, final String password) {
//		Research research = new Research();
//		final String topic = "survey ids";
//		
//		String mySessionID = "";
//		if(!research.isLogin()) {
//			research.signin(username, password);
//		} 
//		mySessionID = research.getSessionID();
//		JSONObject usr = research.getSurveyIDS(mySessionID);
//		if(usr.getBoolean("status")) {
//			printStatus(usr, topic);
//			JSONArray idlist = usr.getJSONArray("id");
//			for(int i = 0; i < idlist.length(); i++)
//			{
//				JSONObject id = idlist.getJSONObject(i);
//				System.out.println("sid: " + id.getString("SurveyID"));
//			}
////			printStatus(idlist, "survey ids");
//		} else {
//			System.out.println(usr.getString("message"));
//		}
//	}
//	
//	public static void testGetSurveyResult(final String username, final String password) {
//		Research research = new Research();
//		final String topic = "survey results";
//		
//		String mySessionID = "";
//		if(!research.isLogin()) {
//			research.signin(username, password);
//		} 
//		mySessionID = research.getSessionID();
//		JSONObject surveyIDs = research.getSurveyIDS(mySessionID);
//		JSONObject surveyid = surveyIDs.getJSONArray("id").getJSONObject(0);
//		JSONObject usr = research.getSurveyResult(surveyid.getString("SurveyID"));
//		if(usr.getBoolean("status")) {
//			printStatus(usr, topic);
//			JSONArray idlist = usr.getJSONArray("survey");
//			for(int i = 0; i < idlist.length(); i++)
//			{
//				JSONObject id = idlist.getJSONObject(i);
//				System.out.println("Symptom: " + id.getString("SymptomName") + "   Status: " + id.getInt("SymptomStatus"));
//			}
//			
//		} else {
//			System.out.println(usr.getString("message"));
//		}
//	}
//	
//	public static void printStatus(final JSONObject result, final String testTopic) {
//		if(result.getBoolean("status")) {
//			System.out.println("Testing " + testTopic + ".....................passed");
//		} else {
//			System.out.println("Testing " + testTopic + ".....................failed");
//			System.out.println("Error in " + testTopic + ": " + result.getString("message"));
//		}
//		System.out.println("");
//	}
}
