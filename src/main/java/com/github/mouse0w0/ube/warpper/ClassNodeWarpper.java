package com.github.mouse0w0.ube.warpper;

import org.objectweb.asm.tree.ClassNode;

public class ClassNodeWarpper extends NodeWarpper<ClassNode, ClassNode>{

	public ClassNodeWarpper(ClassNode node) {
		super(node, node);
	}

}
