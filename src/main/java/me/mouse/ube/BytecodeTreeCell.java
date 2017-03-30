package me.mouse.ube;

import org.objectweb.asm.tree.*;

import javafx.scene.control.TreeCell;

public final class BytecodeTreeCell extends TreeCell<Object> {

	private final ClassNode root;
	
	public BytecodeTreeCell(ClassNode root) {
		this.root = root;
	}

	@Override
	public void updateItem(Object item, boolean empty) {
		super.updateItem(item, empty);

		if (empty&&BytecodeUtils.getBytecodeHandler(item.getClass())!=null) {
			setText(null);
			setGraphic(null);
		} else {
			setText(BytecodeUtils.getBytecodeHandler(item.getClass())._getText(item,root));
			setGraphic(getTreeItem().getGraphic());
		}
	}
}
