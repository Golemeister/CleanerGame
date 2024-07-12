package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Usisivac extends Figura{
	
	public Usisivac(int x1, int y1) {
		super(x1, y1);
		color = Color.RED;
		r = 15;
	}


	@Override
	public void paint(Graphics g) {
		
		int[] tackeX= {x-r,x,x+r};
		int[] tackeY= {y+r,y-r,y+r};		
		g.setColor(color);
		g.fillPolygon(tackeX,tackeY,3);	
	}

	public int pomeraj() {
		return r/2;
	}
	
}
