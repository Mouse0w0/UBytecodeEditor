package me.mouse.ube.handler;

import static me.mouse.ube.BytecodeUtils.getAccess;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.TraceSignatureVisitor;

import me.mouse.ube.warpper.MethodNodeWarpper;

public class MethodHandler implements BytecodeHandler<MethodNodeWarpper> {

	@Override
	public String getText(MethodNodeWarpper item, ClassNode root) {
		item.labelNames.clear();
		
		StringBuilder sb = new StringBuilder();

		sb.append(getAccess(item.node.access & ~Opcodes.ACC_VOLATILE));
		if ((item.node.access & Opcodes.ACC_NATIVE) != 0) {
			sb.append("native ");
		}
		if ((item.node.access & Opcodes.ACC_VARARGS) != 0) {
			sb.append("varargs ");
		}
		if ((item.node.access & Opcodes.ACC_BRIDGE) != 0) {
			sb.append("bridge ");
		}
		if ((root.access & Opcodes.ACC_INTERFACE) != 0 && (item.node.access & Opcodes.ACC_ABSTRACT) == 0
				&& (item.node.access & Opcodes.ACC_STATIC) == 0) {
			sb.append("default ");
		}

		TraceSignatureVisitor v = new TraceSignatureVisitor(0);
		SignatureReader r = new SignatureReader(item.node.desc);
		r.accept(v);
		String genericDecl = v.getDeclaration();
		String genericReturn = v.getReturnType();
		String genericExceptions = v.getExceptions();

		sb.append(genericReturn).append(' ').append(item.node.name).append(genericDecl);
		if (genericExceptions != null)
			sb.append(" throws ").append(genericExceptions);

		return sb.toString();
	}
}
