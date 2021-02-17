package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;

public class Clipboard {
	
	private List<Clip> contenu = new ArrayList<>();
	private List<ClipboardListener> listeners = new ArrayList<>();
	private static Clipboard clipboard;
	
	private Clipboard() {
		
	}
	
	public void	copyToClipboard(List<Clip> clips) {
		for(Clip c : clips) 
			contenu.add(c.copy());
		
		for(ClipboardListener c : listeners)
			c.clipboardChanged();
	}
	
	public List<Clip> copyFromClipboard(){
		
		List<Clip> copy = new ArrayList<>();
		
		for(Clip c : contenu)
			copy.add(c.copy());
		
		for(ClipboardListener c : listeners)
			c.clipboardChanged();
		
		return copy;
	}
	
	public void clear() {
		contenu.clear();
		
		for(ClipboardListener c : listeners)
			c.clipboardChanged();
	}
	
	public boolean isEmpty() {
		return contenu.isEmpty();
	}
	
	public static Clipboard getInstance() {
		if(clipboard==null) {
			clipboard = new Clipboard();
		}
		
		return clipboard;
	}
	
	public void addListener(ClipboardListener listener){
		listeners.add(listener);
	}
	
	public void removeListener(ClipboardListener listener){
		listeners.remove(listener);
	}
}
