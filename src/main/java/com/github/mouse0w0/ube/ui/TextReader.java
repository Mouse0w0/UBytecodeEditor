package com.github.mouse0w0.ube.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.BorderPane;

public class TextReader extends BorderPane implements Controller {

	@FXML
	private Button copy;
	@FXML
	private TextArea textarea;
	
	public TextReader(String text) {
		try {
			loadFxml("TextReader.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		textarea.setText(text);
		copy.setOnAction(event -> {
			Clipboard clipboard = Clipboard.getSystemClipboard();
			Map<DataFormat, Object> data = new HashMap<>();
			data.put(DataFormat.PLAIN_TEXT, textarea.getText());
			clipboard.setContent(data);
		});
	}
}
