package com.github.mouse0w0.ube.wrapper.util;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXUtils {
	
	public static void show(Parent root) {
		Stage stage = new Stage();
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		stage.show();
	}

}
