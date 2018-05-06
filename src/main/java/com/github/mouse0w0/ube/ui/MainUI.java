package com.github.mouse0w0.ube.ui;

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
			fileChooser.setTitle("Open file");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Jar(.jar) or Class(.class)", "*.class", "*.jar"));

			File file = fileChooser.showOpenDialog(getScene().getWindow());
			if (file == null)
				return;

			Editor editor = new Editor(file.toPath());
			tabPane.widthProperty().addListener((observable, oldValue, newValue) -> {
				editor.setPrefWidth((double) newValue);
			});
			tabPane.heightProperty().addListener((observable, oldValue, newValue) -> {
				editor.setPrefHeight((double) newValue - tabPane.getTabMaxHeight() - 5);
			});
			editor.setPrefSize(tabPane.getWidth(), tabPane.getHeight() - tabPane.getTabMaxHeight() - 5);
			tabPane.getTabs().add(new Tab(file.getName(), editor));
		});
		Button save = new Button("Save");
		save.setOnAction(event -> {
			if (tabPane.getSelectionModel().getSelectedItem() == null)
				return;
			((Editor) tabPane.getSelectionModel().getSelectedItem().getContent()).save();
		});
		Button bytecode = new Button("Bytecode");
		bytecode.setOnAction(event -> {
			if (tabPane.getSelectionModel().getSelectedItem() == null)
				return;
			((Editor) tabPane.getSelectionModel().getSelectedItem().getContent()).showBytecode();
		});
		Button asmcode = new Button("ASM code");
		asmcode.setOnAction(event -> {
			if (tabPane.getSelectionModel().getSelectedItem() == null)
				return;
			((Editor) tabPane.getSelectionModel().getSelectedItem().getContent()).showAsmSource();
		});

		ToolBar toolBar = new ToolBar(open, save, bytecode, asmcode);
		toolBar.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);

		setTop(toolBar);
		setCenter(tabPane);
	}
}
