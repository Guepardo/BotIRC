package puzzle;

import java.util.ArrayList;

public class Vote{
	private String question; 
	private ArrayList<String> alternatives; 
	
	
	public Vote(){
		alternatives = new ArrayList<>(); 
	}
	
	public void addQuestion(String question){
		this.question = question;
	}; 
	
	public void addAlternative(String alternative){
		alternatives.add(alternative); 
	}; 
	
}
