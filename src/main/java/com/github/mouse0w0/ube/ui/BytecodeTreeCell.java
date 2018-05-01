package com.github.mouse0w0.ube.ui;

import com.github.mouse0w0.ube.wrapper.util.BytecodeUtils;

import javafx.scene.control.TreeCell;

public final class BytecodeTreeCell extends TreeCell<Object> {
	
	public BytecodeTreeCell() {}

	@Override
	public void updateItem(Object item, boolean empty) {
		super.updateItem(item, empty);

		if (empty||BytecodeUtils.getBytecodeHandler(item.getClass())==null) {
			setText(null);
			setGraphic(null);
		} else {
			setText(BytecodeUtils.getBytecodeHandler(item.getClass()).impl_getText(item));
			setGraphic(getTreeItem().getGraphic());
		}
	}
}
