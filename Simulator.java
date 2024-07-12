package igra;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Simulator extends Frame {
	
	private Scena scena;
	
	public Simulator()
	{
		setBounds(700, 200, 500, 400);
		
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				scena.stop();
				dispose();
			}
			
		});
		scena = new Scena(this);
		add(scena,BorderLayout.CENTER);
		setVisible(true);
		scena.requestFocus();
	}
		
	
	
	public static void main(String[] args) {
		new Simulator();
	}
}
