package igra;
import java.awt.Color;
import java.awt.Graphics;

public class Kamencic extends Figura{

	public Kamencic(int x1, int y1) {
		super(x1, y1);
		color = Color.BLACK;
		r = 5;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(x-r, y-r, 2*r, 2*r);
	}

}
