package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandMove;

public class ToolSelection implements Tool{

	private double x;
	private double y;
	private String name;
	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		x = e.getX();
		y = e.getY();
		name = "Selection";
		
		if(!e.isShiftDown())
			i.getSelection().select(i.getBoard(), e.getX(), e.getY());
		else
			i.getSelection().toogleSelect(i.getBoard(), e.getX(), e.getY());
		
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		for(Clip c : i.getSelection().getContents()) {
			if(x+(c.getRight()-x) < 700 && y+(c.getBottom()-y) < 600){
				CommandMove cm = new CommandMove(i,c,e.getX()-x,e.getY()-y);
				cm.execute();
				i.getUndoStack().addCommand(cm);
				i.activeUndoRedo();
			}			
		}
		
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		for(Clip c : i.getSelection().getContents()) {
			if(c instanceof ClipGroup) {
				CommandMove cm = new CommandMove(i,c,e.getX()-x,e.getY()-y);
				cm.execute();
				i.getUndoStack().addCommand(cm);
				i.activeUndoRedo();
			}else {
				c.setGeometry(c.getLeft(), c.getTop(), c.getRight(), c.getBottom());
			}
		}
		
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		i.getBoard().draw(gc);
		i.getSelection().drawFeedback(gc);
	}

	@Override
	public String getName(EditorInterface editor) {
		return name;
	}

}
