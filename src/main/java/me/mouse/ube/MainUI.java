package me.mouse.ube;

import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainUI extends VBox{
	
	private final TabPane tabPane;
	
	public MainUI(){
		setPrefSize(900, 600);
		setFillWidth(true);
		
		tabPane = new TabPane();
		tabPane.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		
		Button open = new Button("Open");
		open.setOnAction(event->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open .class");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Class", "*.class"));
			
			File file = fileChooser.showOpenDialog(getScene().getWindow());
			if(file==null) return;
			
			tabPane.getTabs().add(new Tab(file.getName(),new ClassEditor(file)));
		});
		Button save = new Button("Save");
		save.setOnAction(event->{
			((ClassEditor)tabPane.getSelectionModel().getSelectedItem().getContent()).save();
		});
		Button asm = new Button("ASM");
		asm.setOnAction(event->{
			((ClassEditor)tabPane.getSelectionModel().getSelectedItem().getContent()).showASM();
		});
		Button source = new Button("ASMSource");
		source.setOnAction(event->{
			((ClassEditor)tabPane.getSelectionModel().getSelectedItem().getContent()).showASMSource();
		});
		
		ToolBar toolBar = new ToolBar(open,save,asm,source);
		toolBar.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		
		getChildren().addAll(toolBar,tabPane);
	}
}
