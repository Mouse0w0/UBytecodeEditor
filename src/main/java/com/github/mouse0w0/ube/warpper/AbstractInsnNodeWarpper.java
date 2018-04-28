package com.github.mouse0w0.ube.warpper;

import org.objectweb.asm.tree.AbstractInsnNode;

public class AbstractInsnNodeWarpper<T extends AbstractInsnNode> extends NodeWarpper<T, MethodNodeWarpper>{
	
	public AbstractInsnNodeWarpper(T node,MethodNodeWarpper parent) {
		super(node, parent);
	}
}
