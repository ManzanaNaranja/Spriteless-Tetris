package game.states;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import game.Player;

public class AEnv {
	public static ArrayList<Player> readEnv(String path) {
		ArrayList<Player> parsedData = new ArrayList<Player>();
		
		
		InputStream is = AEnv.class.getClassLoader().getResourceAsStream("leaderboard.txt");
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		try {
			while((line = br.readLine()) != null) {
				String[] data = line.split("=");
				parsedData.add(new Player(data[0], data[1]));
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		return parsedData;
	}
}

