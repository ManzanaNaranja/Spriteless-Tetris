package game.states;
import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import game.Game;

public class IntroState extends State{
	
	public IntroState(Game game) {
		super(game);
	}

	final int W = 13;
	final int H = 16;
	
	private long time = 0;
	
	@Override
	public void tick() {
		time += 17;
		if(time >= 3000) {
			game.getStateManager().setState(game.menuState);
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.decode("#FF8C1A"));
		g.fillRect(0, 0, this.game.width, this.game.height);
		g.setColor(Color.white);
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("introState.txt");
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(is, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
				//	System.out.println(times);
					for(int j = 0; j < times; j++) {
						
						g.drawString(character,column*W+8, row*H+15);
						column++;
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

}
