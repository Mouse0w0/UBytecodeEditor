package com.github.mouse0w0.ube.wrapper;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;

public interface NodeWrapper<S> {
	
	String getText();

	default Node getGraphic() {
		return null;
	}
	
	S getSource();
	
	NodeWrapper<?> getParent();
	
	TreeItem<NodeWrapper<?>> getTreeItem();
}
