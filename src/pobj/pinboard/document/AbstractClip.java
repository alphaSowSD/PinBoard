package pobj.pinboard.document;

import javafx.scene.paint.Color;

public abstract class AbstractClip implements Clip{
	
	private double left; 
	private double top; 
	private double right; 
	private double bottom;
	private Color color;
	
	public AbstractClip(double l, double t, double r, double b, Color c){
		left = l;
		top = t;
		right = r;
		bottom = b;
		color = c;
	}
	
	@Override
	public void setColor(Color c) {
		color = c;
	}

	@Override
	public Color getColor() {
		return color;
	}
	
	@Override
	public void move(double x, double y) {
		left += x;
		right += x;
		top += y;
		bottom += y;
	}


	@Override
	public void setGeometry(double left, double top, double right, double bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;	
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
	public boolean isSelected(double x, double y) {
		return (x >= getLeft() && x <= getRight() && y >= getTop() && y <= getBottom());	
	}
	
}
