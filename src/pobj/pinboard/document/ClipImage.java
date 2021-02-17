package pobj.pinboard.document;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ClipImage implements Clip{

	private Image img;
	private double left;
	private double top;
	private double right;
	private double bottom;
	private File filename; 
	
	public ClipImage(double l, double t, File filename) {
		img = new Image("file://" + filename.getAbsolutePath());
		left = l;
		top = t;
		right = img.getWidth();
		bottom = img.getHeight();
		this.filename = filename;
	}


	public void draw(GraphicsContext ctx) {
		ctx.drawImage(img, left, top);
	}

	
	public ClipImage copy() {
		return new ClipImage(left,top,filename);
	}


	public double getTop() {
		return top;
	}


	public double getLeft() {
		return left;
	}


	public double getBottom() {
		return bottom;
	}


	public double getRight() {
		return right;
	}


	public void setGeometry(double left, double top, double right, double bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}


	public void move(double x, double y) {
		this.left = x;
		this.top = y;
	}


	public boolean isSelected(double x, double y) {
		return (x >= left && x <= right);
	}


	@Override
	public void setColor(Color c) {
		
	}


	@Override
	public Color getColor() {
		return null;
	}

}
