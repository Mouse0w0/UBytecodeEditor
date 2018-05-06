package com.github.mouse0w0.ube.ui;

import com.github.mouse0w0.ube.wrapper.NodeWrapper;
import javafx.scene.control.TreeCell;

public final class EditorTreeCell extends TreeCell<NodeWrapper<?>> {
	
	public EditorTreeCell() {}

	@Override
	public void updateItem(NodeWrapper<?> item, boolean empty) {
		super.updateItem(item, empty);

		if (!empty) {
			setText(item.getText());
			setGraphic(item.getGraphic());
		} else {
			setText(null);
			setGraphic(null);
		}
	}
}
