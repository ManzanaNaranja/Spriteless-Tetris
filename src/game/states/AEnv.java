package game.states;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import game.Player;

public class AEnv {
	public static ArrayList<Player> readEnv(String path) {
		ArrayList<Player> parsedData = new ArrayList<Player>();
		Scanner s;
		try {
			s = new Scanner(new File(path));
			while(s.hasNextLine()) {
				String[] data = s.nextLine().split("=");
				parsedData.add(new Player(data[0], data[1]));
				
			}
			s.close();
		} catch (FileNotFoundException e) {
			// file will be created instead
		}

		return parsedData;
	}
}

