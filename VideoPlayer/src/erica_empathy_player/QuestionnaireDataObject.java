package erica_empathy_player;

public class QuestionnaireDataObject {

	private String emotionType = "N/A";
	private String questionOneData = "N/A";
	private String questionTwoData = "N/A";
	
	QuestionnaireDataObject(String emotion, String data1, String data2){
		this.emotionType = emotion;
		this.questionOneData = data1;
		this.questionTwoData = data2;
	}
	
	String getAll() {
		return this.emotionType + "," + this.questionOneData + "," + this.questionTwoData;
	}
}
