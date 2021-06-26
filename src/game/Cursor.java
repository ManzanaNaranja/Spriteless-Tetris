package game;

import helpers.AVector;

public class Cursor {
	private AVector position;
	private int xMin, xMax;
	private String text;
	public Cursor(int x, int y, int xMin, int xMax) {
		this.position = new AVector(x, y);
		this.xMin = xMin;
		this.xMax = xMax;
		text = "";
	}
	
	public String getText() {
		return CharacterConverter.decode(this.text);
	}
	
	public boolean rightEnd() {
		return this.xMax == this.position.x;
	}
	
	public int getX() {
		return (int)this.position.x;
	}
	
	public int getY() {
		return (int)this.position.y;
	}
	
	public void incrementX(String s) {
		if(position.x < this.xMax) {
			this.text += s;
		}
		this.position.x++;
		this.position.x = this.clamp(position.x);
	}
	
	public void decrementX() {
		if(this.text.length() > 0) this.text = text.substring(0,text.length()-1);
		this.position.x--;
		this.position.x = this.clamp(position.x);
	}
	
	private int clamp(double val) {
		return  Math.max(xMin, Math.min((int)val, xMax));
	}
	
	public static void main(String[] args) {
		Cursor c = new Cursor(1,1,0,10); 
		c.incrementX("A");
		c.incrementX("B");
		
		System.out.println(c.getText());
	}
}
