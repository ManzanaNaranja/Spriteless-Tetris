import java.awt.image.BufferedImage;

public class Assets {
	private static Assets instance = new Assets();
	public BufferedImage box, minus, empty;
	
	private Assets() {
		box = ImageLoader.loadImage("res/box.png");
		minus = ImageLoader.loadImage("res/minus.png");
		empty = ImageLoader.loadImage("res/empty.png");
	}
	
	public static Assets getInstance() {
		return instance;
	}
	
}
