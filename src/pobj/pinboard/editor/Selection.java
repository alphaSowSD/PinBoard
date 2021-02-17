package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipEllipse;
import pobj.pinboard.document.ClipRect;

public class Selection {

	private List<Clip> contents;
	
	public Selection(){
		contents = new ArrayList<>();
	}
	
	public void select(Board board, double x, double y){
		clear();
		
		int i = 0;
		
		while(contents.isEmpty() && i < board.getContents().size()){
			if(board.getContents().get(i).isSelected(x, y)){
				if(!contents.contains(board.getContents().get(i)))
					contents.add(board.getContents().get(i));
				else
					contents.remove(board.getContents().get(i));
			}else{
				i++;
			}
		}
	}
	
	public void toogleSelect(Board board, double x, double y){
		for(Clip c : board.getContents()){
			if(c.isSelected(x, y))
				if(!contents.contains(c))
					contents.add(c);
				else
					contents.remove(c);
		}
	}
	
	public void clear(){
		contents.clear();
	}
	
	public List<Clip> getContents(){
		return contents;
	}
	
	public void drawFeedback(GraphicsContext gc){
		gc.setStroke(Color.BLUE);
		
		for(Clip c : contents){
			gc.strokeRect(c.getLeft()-3, c.getTop()-3, (c.getRight()+5)-c.getLeft(), (c.getBottom()+5)-c.getTop());
			c.draw(gc);
		}		
	}
	
}
