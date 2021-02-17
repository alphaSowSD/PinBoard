package pobj.pinboard.editor;

import java.util.Stack;

import pobj.pinboard.editor.commands.Command;

public class CommandStack {

	private Stack<Command> undo = new Stack<>();
	private Stack<Command> redo = new Stack<>();
	
	public void	addCommand(Command cmd) {
		redo.removeAllElements();
		undo.add(cmd);
	}
	
	public void undo(){
		Command c = undo.pop();
		c.undo();
		redo.push(c);
	}
	
	public void redo(){
		Command c = redo.pop();
		c.execute();
		undo.push(c);
	}
	
	public boolean isUndoEmpty(){
		return undo.isEmpty();
	}
	
	public boolean isRedoEmpty(){
		return redo.isEmpty();
	}
}
