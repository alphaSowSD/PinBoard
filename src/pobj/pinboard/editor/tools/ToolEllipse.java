package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.editor.CurrentColor;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolEllipse implements Tool{

	private String name;
	private ClipEllipse ellipse;
	
	public ToolEllipse() {
		name = "Ellipse";
	}
	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		ellipse = new ClipEllipse(e.getX(), e.getY(), e.getX(), e.getY(), CurrentColor.getInstance().getColor());
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		ellipse.setGeometry(ellipse.getLeft(), ellipse.getTop(), e.getX(), e.getY());
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		if(e.getX() < ellipse.getLeft() && e.getY() < ellipse.getTop())
			ellipse.setGeometry(e.getX(), e.getY(), ellipse.getLeft(), ellipse.getTop());
			
		CommandAdd ca = new CommandAdd(i,ellipse);
		ca.execute();
		i.getUndoStack().addCommand(ca);
		i.activeUndoRedo();
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		i.getBoard().draw(gc);
		gc.setStroke(Color.RED);
		
		if((ellipse.getRight()-ellipse.getLeft()) > 0)
			gc.strokeOval(ellipse.getLeft(), ellipse.getTop(), ellipse.getRight()-ellipse.getLeft(), ellipse.getBottom()-ellipse.getTop());
		else
			gc.strokeOval(ellipse.getRight(), ellipse.getBottom(), ellipse.getLeft()-ellipse.getRight(), ellipse.getTop()-ellipse.getBottom());
		
	}

	@Override
	public String getName(EditorInterface editor) {
		return name;
	}

}
