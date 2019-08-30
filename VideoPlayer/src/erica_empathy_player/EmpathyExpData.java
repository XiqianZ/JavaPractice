package erica_empathy_player;

public class EmpathyExpData {

	private double fearTouchTiming = 60;
	private double heartTouchTiming = 60;
	private double fearClimaxTiming = 60;
	private double heartClimaxTiming = 60;
	private double fearTouchLength = 1;
	private double heartTouchLength = 1;
	
	public EmpathyExpData() {
	
	}

	String getFearTouchTiming() {
		return Double.toString(fearTouchTiming);
	}

	String getHeartTouchTiming() {
		return Double.toString(heartTouchTiming);
	}
	
	String getFearClimaxTiming() {
		return Double.toString(fearClimaxTiming);
	}
	
	String  getHeartClimaxTiming() {
		return Double.toString(heartClimaxTiming);
	}
	
	String getFearTouchLength() {
		return Double.toString(fearTouchLength);
	}
	
	String getHeartTouchLength() {
		return Double.toString(heartTouchLength);
	}

	void setFearClimaxTiming(double d) {
		this.fearClimaxTiming = d;
	}

	void setHeartClimaxTiming(double d) {
		this.heartClimaxTiming = d;
	}
	
	void setFearTouchTiming(double fearTouchTiming) {
		this.fearTouchTiming = fearTouchTiming;
	}

	void setHeartTouchTiming(double heartTouchTiming) {
		this.heartTouchTiming = heartTouchTiming;
	}

	void setFearTouchLength(double fearTouchLength) {
		this.fearTouchLength = fearTouchLength;
	}

	void setHeartTouchLength(double heartTouchLength) {
		this.heartTouchLength = heartTouchLength;
	}

	
}
