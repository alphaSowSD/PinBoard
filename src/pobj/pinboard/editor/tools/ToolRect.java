package pobj.pinboard.editor.tools;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.CurrentColor;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolRect implements Tool{

	private String name;
	private ClipRect rectangle;

	
	public ToolRect() {
		name = "Rectangle";
	}
	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
		rectangle = new ClipRect(e.getX(), e.getY(), e.getX(), e.getY(), CurrentColor.getInstance().getColor());
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		rectangle.setGeometry(rectangle.getLeft(), rectangle.getTop(), e.getX(), e.getY());
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		if(e.getX() < rectangle.getLeft() && e.getY() < rectangle.getTop())
			rectangle.setGeometry(e.getX(), e.getY(), rectangle.getLeft(), rectangle.getTop());
			
		CommandAdd ca = new CommandAdd(i,rectangle);
		ca.execute();
		i.getUndoStack().addCommand(ca);
		i.activeUndoRedo();
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		i.getBoard().draw(gc);
		gc.setStroke(Color.BLACK);
		
		if((rectangle.getRight()-rectangle.getLeft()) > 0)
			gc.strokeRect(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight()-rectangle.getLeft(), rectangle.getBottom()-rectangle.getTop());
		else
			gc.strokeRect(rectangle.getRight(), rectangle.getBottom(), rectangle.getLeft()-rectangle.getRight(), rectangle.getTop()-rectangle.getBottom());
	}

	@Override
	public String getName(EditorInterface editor) {
		return name;
	}

}
