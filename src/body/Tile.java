package body;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Tile {
	
	private int x,y,width,height;
	
	public Tile(int x,int y,int tileSize){
		this.x=x;
		this.y=y;
		width = tileSize;
		height = tileSize;
	}

	public void render(Graphics2D g){
		g.setColor(Color.WHITE);
		g.fillRect(x*width, y*height, width, height);
		g.setColor(Color.RED);
		g.draw(getBounds());
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x*width, y*height, width, height);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	

}
