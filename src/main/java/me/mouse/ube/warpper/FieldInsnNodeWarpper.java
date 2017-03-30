package me.mouse.ube.warpper;

import org.objectweb.asm.tree.FieldInsnNode;

public class FieldInsnNodeWarpper extends AbstractInsnNodeWarpper<FieldInsnNode>{

	public FieldInsnNodeWarpper(FieldInsnNode node, MethodNodeWarpper parent) {
		super(node, parent);
	}

}
