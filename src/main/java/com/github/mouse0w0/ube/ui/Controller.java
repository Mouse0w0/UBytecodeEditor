package com.github.mouse0w0.ube.ui;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javafx.fxml.FXMLLoader;

public interface Controller {
	
	default void loadFxml(String url) throws IOException {
		this.loadFxml(this.getClass().getResource(url));
	}

	default void loadFxml(URL url) throws IOException {
		FXMLLoader loader = new FXMLLoader(url);
		loader.setRoot(this);
		loader.setController(this);
		loader.setCharset(StandardCharsets.UTF_8);
		loader.load();
	}
}
