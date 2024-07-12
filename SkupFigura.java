package igra;

import java.util.ArrayList;

public class SkupFigura {

	private ArrayList<Figura> figure;
	private int currentIndex;
	
	public SkupFigura() {
		figure = new ArrayList<>();
		currentIndex = -1;
	}
	
	public synchronized void dodaj(Figura f) throws GPostoji{
		if(figure.contains(f)) throw new GPostoji();
			figure.add(f);
	}
	
	public Figura getCurrent() {
		return figure.get(currentIndex);
	}
	
	public synchronized void empty() {
		if(!figure.isEmpty()) {
			figure.clear();
			currentIndex = -1;
		}
	}
	
	public synchronized void pointFirst() {
		currentIndex = 0;
	}
	
	public synchronized boolean hasNext() {
		return currentIndex < figure.size();
    }
	
	public synchronized void moveToNext() throws GNemaSledbenik{
		if (!hasNext()) throw new GNemaSledbenik();
            currentIndex++;
        
    }
	
	public boolean isInList(Figura f) {
		return figure.contains(f);
	}
	
	public synchronized void removeItem(Figura f) {
		currentIndex=figure.indexOf(f) <= currentIndex ? currentIndex == figure.indexOf(f) ? currentIndex : currentIndex-- : currentIndex;
		figure.remove(f);
	}
	
	public int getSize() {
		return figure.size();
	}
}
