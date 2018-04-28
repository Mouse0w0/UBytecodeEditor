package com.github.mouse0w0.ube.warpper;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Label;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class MethodNodeWarpper extends NodeWarpper<MethodNode, ClassNode>{
	
	public Map<Label, String> labelNames = new HashMap<>();
	
	public MethodNodeWarpper(MethodNode node,ClassNode parent){
		super(node, parent);
	}

}
