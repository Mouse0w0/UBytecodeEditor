package me.mouse.ube.handler;

import static me.mouse.ube.BytecodeUtils.getAccess;

import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import javafx.scene.control.TreeItem;
import me.mouse.ube.warpper.ClassNodeWarpper;

public class ClassHandler implements BytecodeHandler<ClassNodeWarpper> {

	@SuppressWarnings("unchecked")
	@Override
	public String getText(ClassNodeWarpper n, ClassNode root) {
		StringBuilder sb = new StringBuilder();

		sb.append(getAccess(n.node.access & ~Opcodes.ACC_SUPER));
		if ((n.node.access & Opcodes.ACC_ANNOTATION) != 0)
			sb.append("@interface ");
		else if ((n.node.access & Opcodes.ACC_INTERFACE) != 0)
			sb.append("interface ");
		else if ((n.node.access & Opcodes.ACC_ENUM) == 0)
			sb.append("class ");

		sb.append(n.node.name.replaceAll("/", "."));

		if (n.node.superName != null && !"java/lang/Object".equals(n.node.superName))
			sb.append(" extends ").append(n.node.superName.replaceAll("/", ".")).append(' ');

		if (n.node.interfaces != null && n.node.interfaces.size() > 0) {
			sb.append(" implements ");
			for (String s : (List<String>) n.node.interfaces) {
				sb.append(s.replaceAll("/", ".")).append(',');
			}
		}
		return sb.toString();
	}

	@Override
	public TreeItem<ClassNodeWarpper> getNode(ClassNodeWarpper node) {
		
		return;
	}
}

