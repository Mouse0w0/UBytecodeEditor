package me.mouse.ube.handler;

import javafx.scene.control.TreeItem;

public interface BytecodeHandler<T> {
	
	@SuppressWarnings("unchecked")
	default String impl_getText(Object item){
		return getText((T)item);
	}
	
	String getText(T item);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	default TreeItem impl_getNode(Object node){
		return getNode((T) node);
	}
	
	TreeItem<?> getNode(T node);
}
