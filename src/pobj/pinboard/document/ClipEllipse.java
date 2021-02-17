package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipEllipse extends AbstractClip{

	public ClipEllipse(double l, double t, double r, double b, Color c) {
		super(l, t, r, b, c);
	}

	@Override
	public void draw(GraphicsContext ctx) {
		ctx.setFill(getColor());
		ctx.fillOval(getLeft(), getTop(), getRight()-getLeft(), getBottom()-getTop());
	}

	@Override
	public Clip copy() {
		return new ClipEllipse(getLeft(), getTop(), getRight(), getBottom(), getColor());
	}
	
	@Override
	public boolean isSelected(double x, double y){
		double cx = (getLeft()+getRight())/2;
		double cy = (getTop()+getBottom())/2;
		double rx = (getRight()-getLeft())/2;
		double ry = (getBottom()-getTop())/2;
		
		double tmp1 = Math.pow(((x - cx) / rx),2);
		double tmp2 = Math.pow(((y - cy) / ry),2);
		
		return ( tmp1 + tmp2  <= 1);
		
	}

}
