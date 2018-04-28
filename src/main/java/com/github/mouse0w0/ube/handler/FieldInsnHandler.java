package com.github.mouse0w0.ube.handler;

import org.objectweb.asm.util.Printer;

import com.github.mouse0w0.ube.BytecodeUtils;
import com.github.mouse0w0.ube.warpper.FieldInsnNodeWarpper;

import javafx.scene.control.TreeItem;

public class FieldInsnHandler implements BytecodeHandler<FieldInsnNodeWarpper>{

	@Override
	public String getText(FieldInsnNodeWarpper item) {
		StringBuilder sb = new StringBuilder();
        sb.append(Printer.OPCODES[item.node.getOpcode()]).append(' ');
        sb.append(BytecodeUtils.getTraceSignatureVisitor(item.node.owner).getDeclaration());
        sb.append('.').append(item.node.name).append(" : ");
        sb.append(BytecodeUtils.getTraceSignatureVisitor(item.node.desc).getDeclaration());
		return sb.toString();
	}

	@Override
	public TreeItem<?> getNode(FieldInsnNodeWarpper node) {
		return new TreeItem<>(node);
	}

}
