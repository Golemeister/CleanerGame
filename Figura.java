package igra;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Figura {
	
	protected int x;
	protected int y;
	protected int r;
	protected Color color;
	
	public Figura(int x1,int y1) {
		x = x1;
		y = y1;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getR() {
		return r;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public double findDistance(Figura F) {
		
		return Math.sqrt(Math.pow(this.x - F.getX(), 2)+Math.pow(this.y - F.getY(), 2));
	}
	
	public double getSurface() {
		return Math.pow(r, 2)*Math.PI;
	}
	
	public  boolean isIntersected(Figura F) {
		return this.findDistance(F) <= this.getR() + F.getR();
	}
	
	public  boolean isInside(Figura F) {
		return this.findDistance(F) <= this.getR() - F.getR();
	}
	
	public abstract void paint(Graphics g);
}
