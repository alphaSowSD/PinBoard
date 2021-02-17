package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipRect extends AbstractClip{
	
	public ClipRect(double l, double t, double r, double b, Color c){
		super(l, t, r, b, c);
	}

	@Override
	public void draw(GraphicsContext ctx) {
		ctx.setFill(getColor());
		ctx.fillRect(getLeft(), getTop(), getRight()-getLeft(), getBottom()-getTop());		
	}


	@Override
	public Clip copy() {
		return new ClipRect(getLeft(), getTop(), getRight(), getBottom(), getColor());
	}

}
