package com.github.mouse0w0.ube.warpper;

import org.objectweb.asm.tree.LdcInsnNode;

public class LdcInsnNodeWarpper extends AbstractInsnNodeWarpper<LdcInsnNode>{

	public LdcInsnNodeWarpper(LdcInsnNode node, MethodNodeWarpper parent) {
		super(node, parent);
	}

}
