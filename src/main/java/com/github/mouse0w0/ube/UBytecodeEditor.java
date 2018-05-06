package com.github.mouse0w0.ube;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mouse0w0.ube.ui.MainUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class UBytecodeEditor extends Application {
	
	public static final Logger LOGGER = LoggerFactory.getLogger("UBytecodeEditor");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("UBytecodeEditor");

		Scene scene = new Scene(new MainUI());
		primaryStage.setScene(scene);

		primaryStage.show();
	}

}
