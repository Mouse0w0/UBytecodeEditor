package me.mouse.ube.handler;

import static me.mouse.ube.BytecodeUtils.getAccess;

import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

public class ClassHandler implements BytecodeHandler<ClassNode> {

	@SuppressWarnings("unchecked")
	@Override
	public String getText(ClassNode n, ClassNode root) {
		StringBuilder sb = new StringBuilder();

		sb.append(getAccess(n.access & ~Opcodes.ACC_SUPER));
		if ((n.access & Opcodes.ACC_ANNOTATION) != 0)
			sb.append("@interface ");
		else if ((n.access & Opcodes.ACC_INTERFACE) != 0)
			sb.append("interface ");
		else if ((n.access & Opcodes.ACC_ENUM) == 0)
			sb.append("class ");

		sb.append(n.name.replaceAll("/", "."));

		if (n.superName != null && !"java/lang/Object".equals(n.superName))
			sb.append(" extends ").append(n.superName.replaceAll("/", ".")).append(' ');

		if (n.interfaces != null && n.interfaces.size() > 0) {
			sb.append(" implements ");
			for (String s : (List<String>) n.interfaces) {
				sb.append(s.replaceAll("/", ".")).append(',');
			}
		}
		return sb.toString();
	}
}

