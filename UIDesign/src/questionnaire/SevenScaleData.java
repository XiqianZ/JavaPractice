package questionnaire;

public class SevenScaleData {

	private String emotion = "";
	private String touchPattern = "";
	private String strength = "";
	private String natureness = "";
	private String friendliness = "";
	
	public SevenScaleData(String emotion, String touchPattern, String strength, String natureness, String friendliness) {
		super();
		this.emotion = emotion;
		this.touchPattern = touchPattern;
		this.strength = strength;
		this.natureness = natureness;
		this.friendliness = friendliness;
	}
	
	String getAll() {
		return this.emotion + "," + this.touchPattern + "," + this.strength + "," + this.natureness + "," + this.friendliness;
	}

	String getEmotion() {
		return emotion;
	}

	String getTouchPattern() {
		return touchPattern;
	}

	String getStrength() {
		return strength;
	}

	String getNatureness() {
		return natureness;
	}
	
	String getFriendliness() {
		return friendliness;
	}
}
