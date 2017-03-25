package me.mouse.ube;

import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public final class MainUI extends BorderPane {

	private final TabPane tabPane;

	public MainUI() {
		setPrefSize(900, 600);

		tabPane = new TabPane();
		tabPane.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);

		Button open = new Button("Open");
		open.setOnAction(event -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open .class");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Class", "*.class"));

			File file = fileChooser.showOpenDialog(getScene().getWindow());
			if (file == null)
				return;

			final ClassEditor ce = new ClassEditor(file);
			tabPane.widthProperty().addListener((observable, oldValue, newValue) -> {
				ce.setPrefWidth((double) newValue);
			});
			tabPane.heightProperty().addListener((observable, oldValue, newValue) -> {
				ce.setPrefHeight((double) newValue - tabPane.getTabMaxHeight() - 5);
			});
			ce.setPrefSize(tabPane.getWidth(), tabPane.getHeight() - tabPane.getTabMaxHeight() - 5);
			tabPane.getTabs().add(new Tab(file.getName(), ce));
		});
		Button save = new Button("Save");
		save.setOnAction(event -> {
			if (tabPane.getSelectionModel().getSelectedItem() == null)
				return;
			((ClassEditor) tabPane.getSelectionModel().getSelectedItem().getContent()).saveBytecode();
		});
		Button asm = new Button("ASM");
		asm.setOnAction(event -> {
			if (tabPane.getSelectionModel().getSelectedItem() == null)
				return;
			((ClassEditor) tabPane.getSelectionModel().getSelectedItem().getContent()).showASM();
		});
		Button source = new Button("ASMSource");
		source.setOnAction(event -> {
			if (tabPane.getSelectionModel().getSelectedItem() == null)
				return;
			((ClassEditor) tabPane.getSelectionModel().getSelectedItem().getContent()).showASMSource();
		});

		ToolBar toolBar = new ToolBar(open, save, asm, source);
		toolBar.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);

		setTop(toolBar);
		setCenter(tabPane);
	}
}
