package game.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import game.CharacterConverter;
import game.Game;
import game.Player;


public class MenuState extends State implements KeyListener{
	final int W = 13;
	final int H = 16;
	ArrayList<Player> records;
	public MenuState(Game game) {
		super(game);
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.decode("#FF8C1A"));
		g.fillRect(0, 0, this.game.width, this.game.height);
		g.setColor(Color.white);
		records = game.leaderboard.getData();
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("menuState.txt");
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(is, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(isr);
		String text;
		int row = 0;
		try {
			while((text = br.readLine()) != null) {
				int column = 0;
				String[] data = text.split("/");
				for(int i = 0; i < data.length; i++) {
					String character = data[i].substring(0,1);
					int times = Integer.parseInt(data[i].substring(1));
					int size = 22;
					if(character.equals("<")) { // (left side leaderboard)
						if(times < records.size()) {
							character = records.get(times).name;
							character = CharacterConverter.convert(character);
							times = 1;
						} else {
							character = "";
							times = 0;
						}
					} else if(character.equals(">")) {// (right side leaderboard)
						if(times < records.size()) {
							character = records.get(times).score;
							character = CharacterConverter.convertNums(character);
							times = 1;
					
						} else {
							character = " ";
							times = 29;
						}
					}
					for(int j = 0; j < times; j++) {
						
						g.drawString(character,column*W+8, row*H+15);
						boolean isnum = CharacterConverter.decodeNums(character) != null;
						if(character.length() != 1 && !isnum) {
							column += 22;
						} else if(character.length() != 1 && isnum) {
							column += 7;
						} else {
							column++;
						}
					}
					
				}
				row++;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(game.getStateManager().getState() != this) return;
		int a;
		try {
			a = Integer.parseInt(Character.toString(e.getKeyChar()));
			if(a >= 1 && a <= 9) {
				((GameState) game.gameState).setLevel(a);
				game.getStateManager().setState(game.gameState);
			}
		} catch(Exception except) {
			
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
