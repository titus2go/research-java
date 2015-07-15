import org.json.*;
public class Main {
	public static void main(String[] args) {
		final String test_username = "tester";
		final String test_password = "test1ng";
		testSignUp(test_username, test_password);
		testSignIn(test_username, test_password);
		testSubmitSurvey(test_username, test_password);
		testGetSurveyIDS(test_username, test_password);
//		Research newResearch = new Research();
//		newResearch.signin("tester2", "test2ng");
//		newResearch.getAllSymptoms();
		
//		JSONArray myQuestions = new JSONArray();
//		myQuestions.put(new JSONObject("{'symptom':601, 'status':1}"));
//		myQuestions.put(new JSONObject("{'symptom':602, 'status':1}"));
//		myQuestions.put(new JSONObject("{'symptom':604, 'status':0}"));
//		newResearch.submitSurvey("Ey6aLLCd", "2015-11-08 11:59:00", "2015-11-15 11:59:00", myQuestions);
	}
	
	public static void testSignUp(final String username, final String password) {
		Research testSignUp  = new Research();
		JSONObject result = testSignUp.signup(username, password);
		printStatus(result, "sign up");
	}
	
	public static void testSignIn(final String username, final String password) {
		Research testSignin = new Research();
		JSONObject result = testSignin.signin(username, password);
		printStatus(result, "signin");
//		System.out.println("id:" + testSignin.getUserID() + "  username:" + testSignin.getUserName() + "    password:" + testSignin.getPassword());
	}
	
	public static void testSubmitSurvey(final String username, final String password) {
		Research research = new Research();
		final String topic = "submit survey";
		//By creating a json object array in this way, you can create your own custom survey;
		JSONArray questions = new JSONArray();
		questions.put(new JSONObject("{'symptom':602, 'status':1}"));
		questions.put(new JSONObject("{'symptom':601, 'status':0}"));
		questions.put(new JSONObject("{'symptom':604, 'status':1}"));
		questions.put(new JSONObject("{'symptom':605, 'status':1}"));
		
		//retrieve user id
		JSONObject result = research.signin(username, password);
		if(result.getBoolean("status")) {
			final String userid = result.getString("id");
			if(userid != null) {
				JSONObject submitResult = research.submitSurvey(userid, "2015-07-10 11:59:00", "2015-07-17 11:59:00", questions);
				printStatus(submitResult, topic);
			}
		} else {
			printStatus(result, topic);
		}	
	}
	
	public static void testGetSurveyIDS(final String username, final String password) {
		Research research = new Research();
		final String topic = "survey ids";
		
		JSONObject usr = research.signin(username, password);
		if(usr.getBoolean("status")) {
			final String userid = usr.getString("id");
			JSONObject idlist = research.getSurveyIDS(userid);
			printStatus(idlist, "survey ids");
		}
	}
	
	public static void printStatus(final JSONObject result, final String testTopic) {
		if(result.getBoolean("status")) {
			System.out.println("Testing " + testTopic + ".....................passed");
		} else {
			System.out.println("Testing " + testTopic + ".....................failed");
			System.out.println("Error in " + testTopic + ": " + result.getString("message"));
		}
		System.out.println("");
	}
}
