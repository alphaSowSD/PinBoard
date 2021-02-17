package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandGroup implements Command{

	
	private EditorInterface editor;
	private List<Clip> clips = new ArrayList<>();
	private ClipGroup cg;
	
	public CommandGroup(EditorInterface editor, List<Clip> toGroup) {
		this.editor = editor;
		clips.addAll(toGroup);
		cg = new ClipGroup();
		
		for(Clip c : clips)
			cg.addClip(c);
	}
	
	@Override
	public void execute() {
		editor.getBoard().removeClip(clips);
		editor.getBoard().addClip(cg);
		editor.activeUndoRedo();
	}

	@Override
	public void undo() {
		editor.getBoard().removeClip(cg);
		editor.getBoard().addClip(clips);
		editor.activeUndoRedo();
	}

}
