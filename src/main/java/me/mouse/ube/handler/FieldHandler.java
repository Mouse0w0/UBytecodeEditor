package me.mouse.ube.handler;

import static me.mouse.ube.BytecodeUtils.getAccess;

import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.util.TraceSignatureVisitor;

public class FieldHandler implements BytecodeHandler<FieldNode> {

	@Override
	public String getText(FieldNode n, ClassNode root) {
		StringBuilder sb = new StringBuilder();

		sb.append(getAccess(n.access));

		TraceSignatureVisitor sv = new TraceSignatureVisitor(0);
		SignatureReader r = new SignatureReader(n.desc);
		r.acceptType(sv);
		sb.append(sv.getDeclaration()).append(" ");

		sb.append(n.name);
		if (n.value != null) {
			sb.append(" = ");
			if (n.value instanceof String) {
				sb.append('\"').append(n.value).append('\"');
			} else {
				sb.append(n.value);
			}
		}
		return sb.toString();
	}
}
