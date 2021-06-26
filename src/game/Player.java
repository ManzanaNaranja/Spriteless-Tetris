package game;

public class Player {
	public String name, score;
	
	public Player(String name, String score) {
		this.name = name;
		int len = score.length();
		for(int i = 0; i < (6 - len); i++) {
			score = "0" + score; 
			
		}
		this.score = score;
	}
	
	public int getScore() {
		return Integer.parseInt(this.score);
	}
}
