package me.mouse.ube.handler;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.Printer;

import me.mouse.ube.BytecodeUtils;
import me.mouse.ube.warpper.FieldInsnNodeWarpper;

public class FieldInsnHandler implements BytecodeHandler<FieldInsnNodeWarpper>{

	@Override
	public String getText(FieldInsnNodeWarpper item, ClassNode root) {
		StringBuilder sb = new StringBuilder();
        sb.append(Printer.OPCODES[item.node.getOpcode()]).append(' ');
        sb.append(BytecodeUtils.getTraceSignatureVisitor(item.node.owner).getDeclaration());
        sb.append('.').append(item.node.name).append(" : ");
        sb.append(BytecodeUtils.getTraceSignatureVisitor(item.node.desc).getDeclaration());
		return sb.toString();
	}

}
