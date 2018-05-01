package com.github.mouse0w0.ube.handler;

import static com.github.mouse0w0.ube.wrapper.util.BytecodeUtils.getAccess;

import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.github.mouse0w0.ube.warpper.ClassNodeWarpper;
import com.github.mouse0w0.ube.warpper.FieldNodeWarpper;
import com.github.mouse0w0.ube.warpper.MethodNodeWarpper;
import com.github.mouse0w0.ube.wrapper.util.BytecodeUtils;

import javafx.scene.control.TreeItem;

public class ClassHandler implements BytecodeHandler<ClassNodeWarpper> {

	@SuppressWarnings("unchecked")
	@Override
	public String getText(ClassNodeWarpper n) {
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

	@SuppressWarnings("unchecked")
	@Override
	public TreeItem<?> getNode(ClassNodeWarpper node) {
		TreeItem<?> item = new TreeItem<>(node);
		
		if(node.node.visibleAnnotations!=null)
			node.node.visibleAnnotations.stream().forEach(i->item.getChildren().add(
					BytecodeUtils.getBytecodeHandler(i.getClass()).impl_getNode(i)));
		if(node.node.visibleTypeAnnotations!=null)
			node.node.visibleTypeAnnotations.stream().forEach(i->item.getChildren().add(
					BytecodeUtils.getBytecodeHandler(i.getClass()).impl_getNode(i)));
		
		((List<FieldNode>)node.node.fields).stream().forEach(i ->item.getChildren().add(
		BytecodeUtils.getBytecodeHandler(FieldNodeWarpper.class).impl_getNode(new FieldNodeWarpper(i, node.node))));
		
		((List<MethodNode>)node.node.methods).stream().forEach(i ->item.getChildren().add(
		BytecodeUtils.getBytecodeHandler(MethodNodeWarpper.class).impl_getNode(new MethodNodeWarpper(i, node.node))));
		
		return item;
	}
}

