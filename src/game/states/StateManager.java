package game.states;

public class StateManager {
	private State currentState = null;
	
	public void setState(State s) {
		this.currentState = s;
	}
	
	public State getState() {
		return currentState;
	}
}
