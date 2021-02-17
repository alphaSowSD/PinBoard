package pobj.pinboard.editor.commands;

import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandMove implements Command{

	private EditorInterface editor;
	private Clip toMove;
	private double x;
	private double y;
	
	public CommandMove(EditorInterface editor, Clip toMove, double x, double y) {
		this.editor = editor;
		this.toMove = toMove;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void execute() {
		editor.getBoard().removeClip(toMove);
		toMove.move(x, y);
		editor.getBoard().addClip(toMove);
		editor.activeUndoRedo();
	}

	@Override
	public void undo() {
		editor.getBoard().removeClip(toMove);
		toMove.move(-x, -y);
		editor.getBoard().addClip(toMove);
		editor.activeUndoRedo();
	}

}
