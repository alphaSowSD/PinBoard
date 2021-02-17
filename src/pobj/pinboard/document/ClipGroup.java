package pobj.pinboard.document;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipGroup implements Composite {

	private double left, top, right, bottom;
	private List<Clip> clips;
	
	public ClipGroup() {
		left = 0;
		top = 0;
		right = 0;
		bottom = 0;
		
		clips = new ArrayList<>();
	}
	
	public ClipGroup(double l, double t, double r, double b, List<Clip> list) {
		left = l;
		top = t;
		right = r;
		bottom = b;
		clips = list;
	}
	
	@Override
	public void draw(GraphicsContext ctx) {
		for(Clip c : clips) {
			c.draw(ctx);
		}
	}

	@Override
	public double getTop() {
		return top;
	}

	@Override
	public double getLeft() {
		return left;
	}

	@Override
	public double getBottom() {
		return bottom;
	}

	@Override
	public double getRight() {
		return right;
	}

	@Override
	public void setGeometry(double left, double top, double right, double bottom) {
		move(left,top);
	}

	@Override
	public void move(double x, double y) {
		left += x;
		right += x;
		top += y;
		bottom += y;
		
		for(Clip c : clips) {
			c.move(x, y);
		}
	}

	@Override
	public boolean isSelected(double x, double y) {
		return (x >= getLeft() && x <= getRight() && y >= getTop() && y <= getBottom());	
	}

	@Override
	public void setColor(Color c) {
		
	}

	@Override
	public Color getColor() {
		return null;
	}

	@Override
	public Clip copy() {	
		List<Clip> copyList = new ArrayList<>();
		
		for(Clip c : clips) {
			copyList.add(c.copy());
		}
		
		return new ClipGroup(left, top, right, bottom, copyList);
	}

	@Override
	public List<Clip> getClips() {
		return clips;
	}

	public void refreshSize() {
		for(Clip c : clips) {
			if(c.getLeft() < left)
				left = c.getLeft();
			
			if(c.getRight() > right)
				right = c.getRight();
			
			if(c.getBottom() > bottom)
				bottom = c.getBottom();
			
			if(c.getTop() < top)
				top = c.getTop();
		}
	}
	
	
	@Override
	public void addClip(Clip toAdd) {		
		if(clips.isEmpty()) {
			clips.add(toAdd);
			left = toAdd.getLeft();
			right = toAdd.getRight();
			top = toAdd.getTop();
			bottom = toAdd.getBottom();
		}else {
			clips.add(toAdd);
			refreshSize();
		}
				
	}

	@Override
	public void removeClip(Clip toRemove) {
		clips.remove(toRemove);
		
		// Pour redimensionner le groupe.
		left = 700;
		right = 0;
		top = 600;
		bottom = 0;
		
		refreshSize();
	}

}
