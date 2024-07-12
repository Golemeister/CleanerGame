package igra;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Scena extends Canvas implements Runnable {
    private SkupFigura figure = new SkupFigura();
    private Simulator simulator;
    private Thread nit;
    private boolean pauza = true;
    private Figura minF = null;
    private Image offScreenImage;
    private Graphics offScreenGraphics;

    public Scena(Simulator s) {
        simulator = s;
        nit = new Thread(this);
        nit.start();
        setBackground(Color.GRAY);
        Usisivac u = new Usisivac(s.getWidth() / 2 - 15, s.getHeight() / 2 - 15);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Kamencic k = new Kamencic(e.getX(), e.getY());
                synchronized (figure) {
                    figure.pointFirst();
                    while (figure.hasNext()) {
                        if (figure.getCurrent().isIntersected(k)) {
                            return;
                        }
                        try {
                            figure.moveToNext();
                        } catch (GNemaSledbenik e1) {
                            break;
                        }
                    }
                    try {
                        figure.dodaj(k);
                        minF = null;
                        synchronized (nit) {
                            pauza = false;
                            nit.notifyAll();
                        }
                    } catch (GPostoji e1) {
                        // Handle exception
                    }
                }
                repaint();
            }
        });
        try {
            figure.dodaj(u);
        } catch (GPostoji e) {
            // Handle exception
        }
        addKeyListener(new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                synchronized(nit) {
	                if (!pauza) {
	                	
	                        pause();
	                    
	                } else if(pauza) {
	                	
	                        resume();
	                   
	                	
	                }
                }
			}
			else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				simulator.dispose();
				stop();
			}
		}
        });
        
        repaint();
    }

    @Override
    public void run() {
        try {
            while (!nit.isInterrupted()) {
                synchronized (nit) {
                    while (pauza) {
                        nit.wait();
                    }
                }
                Thread.sleep(50);

                figure.pointFirst();
                Usisivac u = (Usisivac) figure.getCurrent();

                if (minF == null) {
                    try {
                        double min = 1000;
                        figure.moveToNext();

                        while (figure.hasNext()) {
                            Figura f = figure.getCurrent();
                            if (min > u.findDistance(f)) {
                                min = u.findDistance(f);
                                minF = f;
                            }
                            figure.moveToNext();
                        }
                    } catch (GNemaSledbenik e1) {
                        break;
                    }
                }

                int distanceX = minF.getX() - u.getX();
                if (Math.abs(distanceX) > u.pomeraj()) {
                    u.setX(u.getX() + (int) Math.signum(distanceX) * u.pomeraj());
                } else {
                    int distanceY = minF.getY() - u.getY();
                    u.setY(u.getY() + (int) Math.signum(distanceY) * u.pomeraj());
                }

                if (u.isIntersected(minF)) {
                    figure.removeItem(minF);
                    minF = null;
                }

                if (figure.getSize() == 1) {
                    pauza = true;
                }

                repaint();
            }
            System.out.println("Kraj niti");
        } catch (InterruptedException e) {
            System.out.println("uhvaćen exception");
        }
    }

    public synchronized void stop() {
        nit.interrupt();
        
    }

    public synchronized void pause() {
    	pauza = true;
    	repaint();
    }
    
    public synchronized void resume() {
    	if (figure.getSize() > 1) {
    		pauza = false;
    		nit.notifyAll();
    	}
    }
    
    @Override
    public void paint(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = createImage(getWidth(), getHeight());
            offScreenGraphics = offScreenImage.getGraphics();
        }
        offScreenGraphics.setColor(getBackground());
        offScreenGraphics.fillRect(0, 0, getWidth(), getHeight());

        synchronized (figure) {
            figure.pointFirst();
            while (figure.hasNext()) {
                figure.getCurrent().paint(offScreenGraphics);
                try {
                    figure.moveToNext();
                } catch (GNemaSledbenik e) {
                    break;
                }
            }
        }
        if(pauza) {
        	offScreenGraphics.setColor(Color.BLACK);
        	offScreenGraphics.drawString("Pauza", 200, 200);
        }
        g.drawImage(offScreenImage, 0, 0, this);
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }
}
