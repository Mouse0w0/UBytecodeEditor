package me.mouse.ube.handler;

import org.objectweb.asm.tree.ClassNode;

import javafx.scene.control.TreeItem;

public interface BytecodeHandler<T> {
	
	@SuppressWarnings("unchecked")
	default String _getText(Object item, ClassNode root){
		return getText((T)item, root);
	}
	
	String getText(T item, ClassNode root);
	
	TreeItem<T> getNode(T node);
}
