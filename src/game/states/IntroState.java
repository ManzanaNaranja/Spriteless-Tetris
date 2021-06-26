package game.states;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
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
		File f = new File("res/introState.txt");
		try {
			Scanner s = new Scanner(f, "utf-8");
			int row = 0;
			
			while(s.hasNextLine()) {

				String text = s.nextLine();
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
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

//		for(int i = 0; i < 22; i++) {
//			for(int j = 0; j < 36; j++) {
//				g.drawString("⛝",j*W+8, i*H+15);
//			}
//		}
	
		
		
	}

}
