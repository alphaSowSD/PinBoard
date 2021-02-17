package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandUngroup implements Command {

	private EditorInterface editor;
	private List<Clip> clips = new ArrayList<>();
	private ClipGroup cg;
	
	public CommandUngroup(EditorInterface editor, ClipGroup toUngroup) {
		this.editor = editor;
		cg = toUngroup;
		clips = cg.getClips();
	}
	
	public CommandUngroup(EditorInterface editor, List<Clip> toUngroup) {
		this.editor = editor;
		clips.addAll(toUngroup);
		cg = new ClipGroup();
		
		for(Clip c : clips)
			cg.addClip(c);
	}
	
	@Override
	public void execute() {
		editor.getBoard().removeClip(cg);
		editor.getBoard().addClip(clips);
		editor.activeUndoRedo();
	}

	@Override
	public void undo() {
		editor.getBoard().removeClip(clips);
		editor.getBoard().addClip(cg);
		editor.activeUndoRedo();
	}

}
