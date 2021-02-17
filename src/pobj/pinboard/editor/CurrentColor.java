package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import pobj.pinboard.document.Clip;

public class CurrentColor {
	
	private static CurrentColor currentColor;
	private static Color color;
	
	private CurrentColor() {
		
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public Color getColor() {
		return color;
	}
	
	public static CurrentColor getInstance() {
		if(currentColor==null) {
			currentColor = new CurrentColor();
		}
		
		return currentColor;
	}
	
}
