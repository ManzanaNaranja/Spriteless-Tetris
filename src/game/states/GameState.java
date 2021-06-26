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
import java.util.Scanner;

import ai.FurryBrain;
import game.CharacterConverter;
import game.Game;
import game.Player;
import game.Cursor;
import game.StringFormatter;
import game.input.KeyManager;
import helpers.AVector;
import tetris.Move;
import tetris.Tetris;
import tetris.TetrisEventListener;

public class GameState extends State implements KeyListener{
	final int W = 13;
	final int H = 16;
	Tetris tetris;
	private boolean nextState;
	private int level;
	private int score;
	private int time;
	String[][] boardData;
	String[][] overrideBoardData;
	private int[] speed = new int[] {350,300,250,200,175,150,125,100, 80};
	private boolean over;
	private int nextLevel;
	private Cursor cursor;
	private boolean isHighScore;
	private double speedMultiplier;
	private boolean aion;
	TetrisEventListener t = new TetrisEventListener() {

		@Override
		public void linesCleared(int lines) {
			if(lines == 1) {
				score += 40 * (level + 1);
			} else if(lines == 2) {
				score += 100 * (level + 1);
			} else if(lines == 3) {
				score += 300 * (level + 1);
			} else if(lines == 4) {
				score += 1200 * (level+ 1);
			}	
		}

		@Override
		public void finalizedPiecePlacement() {
			
		}
	};
	public GameState(Game game) {
		super(game);
		this.reset();
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	private void reset() {
		tetris = new Tetris();
		tetris.addEventListener(t);
		boardData = new String[22][36];
		this.overrideBoardData = new String[22][36];
		nextLevel = 10;
		speedMultiplier = 1;
		score = 0;
		time = 0;
		aion = false;
		level = 1;
		over = false;
		cursor = new Cursor(17,11,17,24);
		isHighScore = false;
		nextState = false;
	}

	@Override
	public void tick() {
		if(nextState) {
			game.getStateManager().setState(game.menuState);
			this.reset();
			return;
		}
		time += 17;
		if(tetris.game_over()) {
			if(over == false) {
				isHighScore = game.leaderboard.isHighScore(this.score);
				over = true;
				time = 0;
			} else {
				if(time > 3000 && isHighScore == false) {
					game.getStateManager().setState(game.menuState);
					this.reset();
				}
			}
			return; 
		}
		
		if(time > (int)this.speed[level-1] / this.speedMultiplier) {
			time = 0;
			tetris.down();
			
			if(this.aion) {
				this.speedMultiplier = 30;
				if(tetris.current_piece().position.y == 0){
					FurryBrain f = new FurryBrain();
					Move m = f.bestMove(tetris);
					tetris.current_piece().position.y = 0;
					if(m == null) tetris.drop();
					else {
						tetris.current_piece().rotation = m.getRotation();
						tetris.current_piece().position.x = m.getX();
						
					}
				}
			}
			
			
			
			
			if(tetris.lines_cleared() >= this.nextLevel && this.level < 9) {
				this.level++;
				this.nextLevel += 10;
			}
		}
		if(KeyManager.JD)tetris.right();
		if(KeyManager.JA)tetris.left();
		if(KeyManager.JS)tetris.drop();
		if(KeyManager.JW)tetris.rotate(); 
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.decode("#FF8C1A"));
		g.fillRect(0, 0, this.game.width, this.game.height);
		g.setColor(Color.white);
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("gameState.txt");
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
					int times;
					if(data[i].length() == 1) {
						times = 1;
					} else {
						times = Integer.parseInt(data[i].substring(1));
					}
					for(int j = 0; j < times; j++) {
						boardData[row][column] = character;
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

		this.setLevel();
		this.setLines();
		this.setScore();
		this.setNext();
		this.setBoard();
		if(tetris.game_over()) {
			if(this.isHighScore) {
				this.setInputName();
			} else {
				this.setGameOver();
			}
		}
		this.checkOverride();
		this.drawBoard(g);
	}
	
	public void setGameOver() {
		alterBoardData("＋－－－－－－－－－－－－－－－－－－＋", 8, 6);
		alterBoardData("￤囗囗囗囗囗囗囗囗囗囗囗囗囗囗囗囗囗囗￤",8,7);
		alterBoardData("￤囗＋－－－－－－－－－－－－－－＋囗￤",8,8);
		alterBoardData("￤囗￤              ￤囗￤",8,9);
		alterBoardData("￤囗￤  ＧＡＭＥ  ＯＶＥＲ  ￤囗￤",8,10); // space =>[ ]
		alterBoardData("￤囗￤              ￤囗￤",8,11);
		alterBoardData("￤囗＋－－－－－－－－－－－－－－＋囗￤",8,12);
		alterBoardData("￤囗囗囗囗囗囗囗囗囗囗囗囗囗囗囗囗囗囗￤",8,13);
		alterBoardData("＋－－－－－－－－－－－－－－－－－－＋", 8, 14);
	}
	
	public void setInputName() {
		alterBoardData("＋－－－－－－－－－－－－－－－－－－＋", 8, 6);
		alterBoardData("￤囗囗囗囗囗囗囗囗囗囗囗囗囗囗囗囗囗囗￤",8,7);
		alterBoardData("￤囗＋－－－－－－－－－－－－－－＋囗￤",8,8);
		alterBoardData("￤囗￤ ＨＩＧＨ  ＳＣＯＲＥ！ ￤囗￤",8,9);
		alterBoardData("￤囗￤              ￤囗￤",8,10);
		alterBoardData("￤囗￤ ＮＡＭＥ：＿＿＿＿＿＿＿ ￤囗￤",8,11);
		alterBoardData("￤囗＋－－－－－－－－－－－－－－＋囗￤",8,12);
		alterBoardData("￤囗囗囗囗囗囗囗囗囗囗囗囗囗囗囗囗囗囗￤",8,13);
		alterBoardData("＋－－－－－－－－－－－－－－－－－－＋", 8, 14);
	}
	
	private void checkOverride() {
		for(int i = 0; i < this.boardData.length; i++) {
			for(int j = 0; j < this.boardData[0].length; j++) {
				if(this.overrideBoardData[i][j] != null) {
					this.boardData[i][j] = this.overrideBoardData[i][j];
				}
			}
		}
	}
	
	private void alterBoardData(String data, int x, int y) {
		for(int i = 0; i < data.length(); i++) {
			this.boardData[y][x+i] = data.substring(i,i+1);
		}
	}
	
	private void alterOverrideData(String data, int x, int y) {
		for(int i = 0; i < data.length(); i++) {
			this.overrideBoardData[y][x+i] = data.substring(i,i+1);
		}
	}
	
	
	public void drawBoard(Graphics g) {
		for(int i = 0; i < this.boardData.length; i++) {
			for(int j = 0; j < this.boardData[0].length; j++) {
				g.drawString(this.boardData[i][j],j*W+8, i*H+15);
			}
		}
	}

	public void setLevel() {
		String cleared = Integer.toString(this.level);
		cleared = CharacterConverter.convertNums(cleared);
		cleared = StringFormatter.formatString(cleared);
		this.alterBoardData(cleared, 26, 5);
		
	}

	public void setLines() {
		String lines = Integer.toString(tetris.lines_cleared());
		lines = CharacterConverter.convertNums(lines);
		lines = StringFormatter.formatString(lines);
		this.alterBoardData(lines, 26, 11);
		
	}

	public void setScore() {
		String score = Integer.toString(this.score);
		score = CharacterConverter.convertNums(score);
		score = StringFormatter.formatString(score);
		this.alterBoardData(score, 26, 17);
		
	}


	public void setNext() {
		AVector[] points = tetris.next_piece().getPiece().getPoints(0);
		int x = 6;
		int y = 15;
		for(AVector p : points) {
			this.alterBoardData("⛋", (int)(x+p.x), (int)(y+p.y));
		}
		
	}

	public void setBoard() {
		int x = -1;
		int y = 13;
		int[][] b = tetris.board();
		for(int i = 2; i < b.length; i++) {
			for(int j = 0; j < b[0].length; j++) {
				if(b[i][j] != 0) {
					this.alterBoardData("⛋", (int)(y+j), (int)(x+i));
				}
			}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(game.getStateManager().getState() != this) return;
		if(this.isHighScore && this.over) {
			if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				this.cursor.decrementX();
				alterOverrideData("＿",cursor.getX(),cursor.getY());
				
			} else {
				String key = Character.toString(e.getKeyChar()).toUpperCase();
				if("ABCDEFGHIJKLMNOPQRSTUVWZYZ".contains(key)) {
					String s = CharacterConverter.convert(key);
					if(!cursor.rightEnd()) alterOverrideData(s,cursor.getX(),cursor.getY());
					cursor.incrementX(s);
				}
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER && this.cursor.getText().length() > 1) {
			game.leaderboard.update(new Player(this.cursor.getText(),Integer.toString(this.score)));
			this.nextState = true;
			
		}
		
//		if(e.getKeyChar() == '*') {
//			this.aion = !this.aion;
//			this.speedMultiplier = 1;
//		}
//		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
