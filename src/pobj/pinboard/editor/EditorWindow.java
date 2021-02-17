package pobj.pinboard.editor;

import java.io.File;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.commands.CommandAdd;
import pobj.pinboard.editor.commands.CommandGroup;
import pobj.pinboard.editor.commands.CommandMove;
import pobj.pinboard.editor.commands.CommandUngroup;
import pobj.pinboard.editor.tools.Tool;
import pobj.pinboard.editor.tools.ToolEllipse;
import pobj.pinboard.editor.tools.ToolImage;
import pobj.pinboard.editor.tools.ToolRect;
import pobj.pinboard.editor.tools.ToolSelection;

public class EditorWindow implements EditorInterface, ClipboardListener {
	
	private Board board;
	private Tool currentTool;
	private ToolBar toolbar,colorbar;
	private Menu file, edit, tools;
	private MenuItem news, close, copy, paste, delete,rectangleItem, ellipseItem, group, ungroup, undo, redo;
	private MenuBar menu;
	private Button ellipse, box, img, select;
	private Canvas canvas;
	private Label statut;
	private Selection selection;
	private Separator sep;
	private VBox vbox;
	private CommandStack stack;
	
	public EditorWindow(Stage stage){
		
		Clipboard.getInstance().addListener(this);
		CurrentColor.getInstance().setColor(Color.BLACK);
		stack = new CommandStack();
		
		board = new Board();
		
		stage.setTitle("PinBoard");
		
		selection = new Selection();
		
		// Menu file et ses items.
		
		file = new Menu("File");
		
		news = new MenuItem("New");
		news.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) { 
					new EditorWindow(new Stage());
				}
			});
		
		close = new MenuItem("Close");
		close.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				remove();
				stage.close();
			}
		});
		
		file.getItems().addAll(news,close);
		
		// Menu edit et ses items.
		
		edit = new Menu("Edit");
		
		copy = new MenuItem("Copy");
		copy.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				Clipboard.getInstance().copyToClipboard(selection.getContents());
			}
		});
		
		paste = new MenuItem("Paste");
		paste.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				board.addClip(Clipboard.getInstance().copyFromClipboard());
				board.draw(canvas.getGraphicsContext2D());
			}
		});
		
		
		delete = new MenuItem("Delete");
		delete.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				board.removeClip(selection.getContents());
				board.draw(canvas.getGraphicsContext2D());
			}
		});
		
		group = new MenuItem("Group");
		group.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				executeGroup(selection.getContents());
				board.draw(canvas.getGraphicsContext2D());
			}
		});
		
		ungroup = new MenuItem("Ungroup");
		ungroup.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				executeUngroup(selection.getContents());
				board.draw(canvas.getGraphicsContext2D());
			}
		});
		
		undo = new MenuItem("Undo");
		undo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				stack.undo();
				board.draw(canvas.getGraphicsContext2D());
				activeUndoRedo();
			}
		});
		
		
		redo = new MenuItem("Redo");
		redo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				stack.redo();
				board.draw(canvas.getGraphicsContext2D());
				activeUndoRedo();
			}
		});
		
		activeUndoRedo();
		
		edit.getItems().addAll(copy,paste,delete,group,ungroup,undo,redo);
		
		// Menu tools et ses items.
		
		tools = new Menu("Tools");
		
		rectangleItem = new MenuItem("Rectangle");
		rectangleItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				currentTool = new ToolRect();
			}
		});
		
		ellipseItem = new MenuItem("Ellipse");
		ellipseItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) { 
				currentTool = new ToolEllipse();
			}
		});
		
		tools.getItems().addAll(rectangleItem,ellipseItem);
		
		menu = new MenuBar(file,edit,tools);
		
		box = new Button("Box");
		box.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				currentTool = new ToolRect();
			}
		});
		
		ellipse = new Button("Ellipse");
		ellipse.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				currentTool = new ToolEllipse();
			}
		});
		
		img = new Button("Img...");
		img.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choisissez votre image.");
				fileChooser.getExtensionFilters().addAll(
				         new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
	
				File selectedFile = fileChooser.showOpenDialog(stage);
				
				currentTool = new ToolImage(selectedFile);
			}
		});
		
		select = new Button("Select");
		select.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				currentTool = new ToolSelection();
			}
		});
				
		
		Rectangle rouge = new Rectangle(20,20,Color.RED);
		Button b_rouge = new Button("",rouge);
		b_rouge.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				CurrentColor.getInstance().setColor(Color.RED);
				
				if(!selection.getContents().isEmpty()) 
					for(Clip c : selection.getContents()) 
						c.setColor(Color.RED);
				
				board.draw(canvas.getGraphicsContext2D());
				
				
			}
		});
		
		Rectangle jaune = new Rectangle(20,20,Color.YELLOW);
		Button b_jaune = new Button("",jaune);
		b_jaune.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				CurrentColor.getInstance().setColor(Color.YELLOW);
				
				if(!selection.getContents().isEmpty()) 
					for(Clip c : selection.getContents()) 
						c.setColor(Color.YELLOW);
				
				board.draw(canvas.getGraphicsContext2D());
			}
		});
		
		Rectangle orange = new Rectangle(20,20,Color.ORANGE);
		Button b_orange = new Button("",orange);
		b_orange.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				CurrentColor.getInstance().setColor(Color.ORANGE);
				
				if(!selection.getContents().isEmpty()) 
					for(Clip c : selection.getContents()) 
						c.setColor(Color.ORANGE);
				
				board.draw(canvas.getGraphicsContext2D());
			}
		});
		
		Rectangle vert = new Rectangle(20,20,Color.GREEN);
		Button b_vert = new Button("",vert);
		b_vert.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				CurrentColor.getInstance().setColor(Color.GREEN);
				
				if(!selection.getContents().isEmpty()) 
					for(Clip c : selection.getContents()) 
						c.setColor(Color.GREEN);
				
				board.draw(canvas.getGraphicsContext2D());
			}
		});
		
		Rectangle bleu = new Rectangle(20,20,Color.BLUE);
		Button b_bleu = new Button("",bleu);
		b_bleu.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				CurrentColor.getInstance().setColor(Color.BLUE);
				
				if(!selection.getContents().isEmpty()) 
					for(Clip c : selection.getContents()) 
						c.setColor(Color.BLUE);
				
				board.draw(canvas.getGraphicsContext2D());
			}
		});
		
		Rectangle bleu_ciel = new Rectangle(20,20,Color.SKYBLUE);
		Button b_bleu_ciel = new Button("",bleu_ciel);
		b_bleu_ciel.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				CurrentColor.getInstance().setColor(Color.SKYBLUE);
				
				if(!selection.getContents().isEmpty()) 
					for(Clip c : selection.getContents()) 
						c.setColor(Color.SKYBLUE);
				
				board.draw(canvas.getGraphicsContext2D());
			}
		});

		Rectangle noir = new Rectangle(20,20,Color.BLACK);
		Button b_noir = new Button("",noir);
		b_noir.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				CurrentColor.getInstance().setColor(Color.BLACK);
				
				if(!selection.getContents().isEmpty()) 
					for(Clip c : selection.getContents()) 
						c.setColor(Color.BLACK);
				
				board.draw(canvas.getGraphicsContext2D());
			}
		});
		
		Rectangle gris = new Rectangle(20,20,Color.GREY);
		Button b_gris = new Button("",gris);
		b_gris.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				CurrentColor.getInstance().setColor(Color.GREY);
				
				if(!selection.getContents().isEmpty()) 
					for(Clip c : selection.getContents()) 
						c.setColor(Color.GREY);
				
				board.draw(canvas.getGraphicsContext2D());
			}
		});
		
		Rectangle maron = new Rectangle(20,20,Color.MAROON);
		Button b_maron = new Button("",maron);
		b_maron.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				CurrentColor.getInstance().setColor(Color.MAROON);
				
				if(!selection.getContents().isEmpty()) 
					for(Clip c : selection.getContents()) 
						c.setColor(Color.MAROON);
				
				board.draw(canvas.getGraphicsContext2D());
			}
		});
		
		Rectangle violet = new Rectangle(20,20,Color.PURPLE);
		Button b_violet = new Button("",violet);
		b_violet.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				CurrentColor.getInstance().setColor(Color.PURPLE);
				
				if(!selection.getContents().isEmpty()) 
					for(Clip c : selection.getContents()) 
						c.setColor(Color.PURPLE);
				
				board.draw(canvas.getGraphicsContext2D());
			}
		});
		
		Rectangle rose = new Rectangle(20,20,Color.PINK);
		Button b_rose = new Button("",rose);
		b_rose.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				CurrentColor.getInstance().setColor(Color.PINK);
				
				if(!selection.getContents().isEmpty()) 
					for(Clip c : selection.getContents()) 
						c.setColor(Color.PINK);
				
				board.draw(canvas.getGraphicsContext2D());
			}
		});
		
		Rectangle beige = new Rectangle(20,20,Color.WHEAT);
		Button b_beige = new Button("",beige);
		b_beige.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				CurrentColor.getInstance().setColor(Color.WHEAT);
				
				if(!selection.getContents().isEmpty()) 
					for(Clip c : selection.getContents()) 
						c.setColor(Color.WHEAT);
				
				board.draw(canvas.getGraphicsContext2D());
					
			}
		});
		
		toolbar = new ToolBar(box,ellipse,img,select);
		
		colorbar = new ToolBar(b_rouge,b_jaune,b_orange,b_beige,b_maron,b_vert,b_bleu,b_bleu_ciel,b_violet,b_rose,b_noir,b_gris);

	    statut = new Label("Tool");
		
		canvas = new Canvas(700,600);
		
		canvas.setOnMousePressed(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
					press(e);
			}
		});
		
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
				if(currentTool instanceof ToolSelection)
					drag2(e);
				else
					drag(e);
			}
		});
		
		canvas.setOnMouseReleased(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
				release(e);
			}
		});
		
		clipboardChanged();
		
		sep = new Separator();
		
		vbox = new VBox(menu,toolbar,colorbar,canvas,sep,statut);
		
		stage.setScene(new javafx.scene.Scene(vbox));
		
		stage.show();
	}

	@Override
	public Board getBoard() {
		return board;
	}
	
	public void press(MouseEvent e) {
		if(currentTool != null) {
			currentTool.press(this, e);
			draw();
		}		
	}
	
	public void drag(MouseEvent e) {
		if(currentTool != null) {
			currentTool.drag(this, e);
			draw();
		}
	}
	
	public void drag2(MouseEvent e) {
		if(currentTool != null) {
			currentTool.drag(this, e);
		}
	}
	
	public void release(MouseEvent e) {
		if(currentTool != null) {
			currentTool.release(this, e);
			draw();
			statut.setText(currentTool.getName(this));
		}
	}
	
	public void draw() {
		if(currentTool != null)
			currentTool.drawFeedback(this,canvas.getGraphicsContext2D());
	}
	
	public Selection getSelection(){
		return selection;
	}

	public void remove(){
		Clipboard.getInstance().removeListener(this);
	}
	
	@Override
	public void clipboardChanged() {
		if(Clipboard.getInstance().isEmpty())
			paste.setDisable(true);
		else
			paste.setDisable(false);
	}
	
	public void activeUndoRedo() {
		if(stack.isUndoEmpty())
			undo.setDisable(true);
		else
			undo.setDisable(false);
		
		
		if(stack.isRedoEmpty())
			redo.setDisable(true);
		else
			redo.setDisable(false);

	}
	
	public void executeGroup(List<Clip> toGroup) {
		CommandGroup gc = new CommandGroup(this, toGroup);
		gc.execute();
		stack.addCommand(gc);
	}
	
	public void executeUngroup(List<Clip> toUngroup) {
		CommandUngroup cug = new CommandUngroup(this, toUngroup);
		cug.execute();
		stack.addCommand(cug);
	}
	
	public CommandStack getUndoStack() {
		return stack;
	}
	
}
