package pobj.pinboard.editor.tools.test;

import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Board;
import pobj.pinboard.editor.CommandStack;
//import pobj.pinboard.editor.CommandStack;
import pobj.pinboard.editor.EditorInterface;
//import pobj.pinboard.editor.Selection;
import pobj.pinboard.editor.Selection;

// Helper class for ToolRectTest & ToolEllipseTest

public class ToolTest {

	class MockEditor implements EditorInterface {
           
		private Board board = new Board();
		public Board getBoard() { return board; }

                // à décommenter quand Selection est définie et que getSelection est ajoutée à EditorInterface
        private Selection selection = new Selection();
	    public Selection getSelection() { return selection; }
           
                // à décommenter quand CommandStack est définie et que getUndoStack est ajoutée à EditorInterface
		private CommandStack stack = new CommandStack();
		public CommandStack getUndoStack() { return stack; }

		public Color getCurrentColor() { return Color.BLACK; }

		@Override
		public void activeUndoRedo() {
			
		}
	};
	
	MouseEvent makeMouseEvent(EventType<MouseEvent> eventType, double x, double y, boolean shift) {
			return new MouseEvent(eventType, x, y, y, y, null, 0, shift, false, false, false, false, false, false, false, false, false, null);
	}
}
