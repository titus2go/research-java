//Question is a class that abstracts a question in a survey

public class Question {
	
	private String myText;
	private int myAnswer;
	
	public Question(final String theText, int theAnswer)
	{
		myText = theText;
		myAnswer = theAnswer;
	}
	
	public String getText()
	{
		return myText;
	}
	
	public int getAnswer()
	{
		return myAnswer;
	}
	
	public String toString()
	{
		return myText + " - " + myAnswer;
	}

}
