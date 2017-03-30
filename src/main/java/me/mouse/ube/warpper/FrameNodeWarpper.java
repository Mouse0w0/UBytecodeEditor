package me.mouse.ube.warpper;

import org.objectweb.asm.tree.FrameNode;

public class FrameNodeWarpper extends AbstractInsnNodeWarpper<FrameNode>{

	public FrameNodeWarpper(FrameNode node, MethodNodeWarpper parent) {
		super(node, parent);
	}

}
