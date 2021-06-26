package game.leaderboard;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import game.AEnv;
import game.Player;

public class Leaderboard {
	private ArrayList<Player> data;
	public Leaderboard() {
		data = new ArrayList<Player>();
		this.loadData();
		this.sortData();
		this.limit(10);
		this.saveData();
		

	}
	
	private void limit(int n) {
		if(this.data.size() == 0) return;
		if(n <= 1) return;
		while(this.data.size() > n) {
			data.remove(data.size()-1);
		}
	}
	
	public boolean isHighScore(int score) {
		return this.data.size() < 10 || score > data.get(data.size()-1).getScore();
	}
	
	
	private void loadData() {
		this.data = AEnv.readEnv("res/leaderboard.txt");
	}
	
	public boolean update(Player candidate) {
		if(this.data.size() == 0) {
			data.add(candidate);
			return true;
		}
		boolean isHighScore = this.isHighScore(candidate.getScore());
		if(isHighScore) {
			for(int i = 0; i < this.data.size(); i++) {
				if(candidate.getScore() > data.get(i).getScore()) {
					data.add(i, candidate);
					this.limit(10);
					break;
				}
				if(i == this.data.size() - 1) {
					data.add(candidate);
					break;
				}
			}
			this.saveData();
		}
		return isHighScore;
		
	}
	
	public void saveData() {
		try {
			FileWriter w = new FileWriter("res/leaderboard.txt");
			w.write(this.toString());
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sortData() {
		if(this.data.size() == 0) return;
		data.sort(new Comparator<Player>() {

			@Override
			public int compare(Player p1, Player p2) {
				int s1 = Integer.parseInt(p1.score);
				int s2 = Integer.parseInt(p2.score);
				if(s1 < s2) return 1;
				else if(s1 > s2) return -1;
				else return 0;
			}
			
		});
	}
	public ArrayList<Player> getData() {
		return this.data;
	}
	
	public String toString() {
		if(this.data.size() == 0) return "";
		String s = "";
		for(Player p : this.data) {
			s += p.name + "=" + p.score + "\n";
		}
		return s.substring(0,s.length()-1);
	}
}
