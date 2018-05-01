package com.github.mouse0w0.ube;

import com.github.mouse0w0.ube.ui.MainUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class Main extends Application {

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
