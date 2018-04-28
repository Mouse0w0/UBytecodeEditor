package com.github.mouse0w0.ube.warpper;

import org.objectweb.asm.tree.FieldInsnNode;

public class FieldInsnNodeWarpper extends AbstractInsnNodeWarpper<FieldInsnNode>{

	public FieldInsnNodeWarpper(FieldInsnNode node, MethodNodeWarpper parent) {
		super(node, parent);
	}

}
