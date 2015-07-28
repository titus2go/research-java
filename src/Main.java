

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		final String test_username = "tester";
		final String test_password = "test1ng";
//		testGetSurvey();
//		testGetSurveyHeadings();
//		testSubmitSurvey();
		
	}
	
	public static void testGetSurvey()
	{
		final String test_username = "tester";
		final String test_password = "test1ng";
		
		User user = new User();
		user.setUserInfo(test_username, test_password);
		user.signin();
		List<SurveyHeading> headings = user.getSurveyHeadings();
		for(int i = 0 ; i < headings.size(); i++)
		{
			SurveyHeading head = headings.get(i);
			Survey result = user.getSurvey(head);
			System.out.println("Survey title: " + result.getHeading().getStartDate() + " ~ " + result.getHeading().getEndDate());
			List<Question> questions = result.getQuestions();
			for(int j = 0; j < questions.size(); j++)
			{
				System.out.println((j + 1) + ". " + questions.get(j).getName());
				List<Option> options = questions.get(j).getOptions();
				for(int k = 0; k < options.size(); k++)
				{
					System.out.println("    " + options.get(k).getName() + ": " + options.get(k).getStatus());
				}
			}
		}
	}
	
	public static void testGetSurveyHeadings()
	{
		final String test_username = "tester";
		final String test_password = "test1ng";
		
		User user = new User();
		user.setUserInfo(test_username, test_password);
		user.signin();
		List<SurveyHeading> headings = user.getSurveyHeadings();
		for(int i = 0 ; i < headings.size(); i++)
		{
			SurveyHeading head = headings.get(i);
			System.out.println("survey id: " + head.getSurveyID() + "    start time: " + head.getStartDate() + "  end time: " + head.getEndDate());
		}
	}
	
	public static void testSubmitSurvey()
	{
		final String test_username = "tester";
		final String test_password = "test1ng";
		
		User user = new User();
		user.setUserInfo(test_username, test_password);
		user.signin();
		
		//test submit survey
		
		//Build up a survey
		
		//Step 1:  Start with Option, then with Questions, lastly, add question to survey;
		Option op1 = new Option(602, 1);
		Option op2 = new Option(603, 1);
		Option op3 = new Option(604, 0);
		Option op4 = new Option(605, 1);
		Question q1 = new Question(900);
		q1.addOption(op1);
		q1.addOption(op2);
		q1.addOption(op3);
		q1.addOption(op4);
		
		Option op11 = new Option(701, 0);
		Option op12 = new Option(702, 1);
		Question q2 = new Question(901);
		q2.addOption(op11);
		q2.addOption(op12);
		
		final String startTime = "2015-07-21 11:59:00";
		final String endTime = "2015-07-30 11:59:00";
		Survey survey = new Survey();
		survey.addQuestion(q1);
		survey.addQuestion(q2);
		
		SurveyHeading head = new SurveyHeading(startTime, endTime);
		survey.setHeading(head);
		
		user.submitSurvey(survey);
	}
	

}
