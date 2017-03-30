package me.mouse.ube.handler;

import org.objectweb.asm.tree.ClassNode;

public interface BytecodeHandler<T> {
	
	@SuppressWarnings("unchecked")
	default String _getText(Object item, ClassNode root){
		return getText((T)item, root);
	}
	
	String getText(T item, ClassNode root);
}
