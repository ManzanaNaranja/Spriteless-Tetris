package game;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import game.input.KeyManager;
import game.leaderboard.Leaderboard;
import game.states.GameState;
import game.states.IntroState;
import game.states.MenuState;
import game.states.State;
import game.states.StateManager;

public class Game {
	private Window window;
	private StateManager stateManager;
	public Leaderboard leaderboard;
	public State introState, menuState, gameState;
	private BufferStrategy bs;
	private Graphics g;
	public int width, height;
	public KeyManager keyManager;
	
	public Game(String title, int w, int h) {
		this.width = w;
		this.height = h;
		this.introState = new IntroState(this);
		this.menuState = new MenuState(this);
		this.gameState = new GameState(this);
		this.leaderboard = new Leaderboard();
		this.keyManager = new KeyManager();
		window = new Window(title, w, h);
		window.frame.addKeyListener(keyManager);
		window.frame.addKeyListener((KeyListener) menuState);
		window.frame.addKeyListener((KeyListener) gameState);
		stateManager = new StateManager();
		stateManager.setState(introState);
	}
	
	private void render() {
		bs = window.canvas.getBufferStrategy();
		if(bs == null) {
			window.canvas.createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		if(stateManager.getState() != null) {
			stateManager.getState().render(g);
		}
		bs.show();
		g.dispose();
	}
	
	private void tick() {
		if(stateManager.getState() != null) {
			keyManager.tick();
			stateManager.getState().tick();
		}
	}
	
	public void start() {
		double last = System.currentTimeMillis();
		double time = 0;
		while(true) {
			double current = System.currentTimeMillis();
			double elapsed = current - last;
			time += elapsed;
			while(time >= 17) {
				this.tick();
				time -= 17;
			}
			this.render();

			last = current;
		}
	}
	
	public StateManager getStateManager() {
		return this.stateManager;
	}
	
	public static void main(String[] args) {
		Game g = new Game("Tetris", 500, 410);
		g.start();
	}
	
	
}
