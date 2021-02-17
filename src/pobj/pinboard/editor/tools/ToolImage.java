package pobj.pinboard.editor.tools;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolImage implements Tool{

	private String name;
	private ClipImage img;
	private File file;
	
	public ToolImage(File file) {
		name = "Image";
		this.file = file;
	}
	
	@Override
	public void press(EditorInterface i, MouseEvent e) {
			img = new ClipImage(e.getX(), e.getY(), file);
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		img.setGeometry(e.getX(), e.getY(), img.getRight(), img.getBottom());
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		CommandAdd ca = new CommandAdd(i,img);
		ca.execute();
		i.getUndoStack().addCommand(ca);
		i.activeUndoRedo();
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		i.getBoard().draw(gc);
	}

	@Override
	public String getName(EditorInterface editor) {
		return name;
	}

}
