package com.github.mouse0w0.ube.warpper;

import org.objectweb.asm.tree.LabelNode;

public class LabelNodeWarpper extends AbstractInsnNodeWarpper<LabelNode>{

	public LabelNodeWarpper(LabelNode node, MethodNodeWarpper parent) {
		super(node, parent);
	}

}
