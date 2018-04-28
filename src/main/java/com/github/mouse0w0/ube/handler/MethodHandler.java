package com.github.mouse0w0.ube.handler;

import static com.github.mouse0w0.ube.BytecodeUtils.getAccess;

import java.util.ListIterator;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.TraceSignatureVisitor;

import com.github.mouse0w0.ube.BytecodeUtils;
import com.github.mouse0w0.ube.warpper.*;

import javafx.scene.control.TreeItem;

public class MethodHandler implements BytecodeHandler<MethodNodeWarpper> {

	@Override
	public String getText(MethodNodeWarpper item) {
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
		if ((item.parent.access & Opcodes.ACC_INTERFACE) != 0 && (item.node.access & Opcodes.ACC_ABSTRACT) == 0
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

	@SuppressWarnings("unchecked")
	@Override
	public TreeItem<?> getNode(MethodNodeWarpper node) {
		TreeItem<?> item = new TreeItem<>(node);
		if(node.node.visibleAnnotations!=null)
			node.node.visibleAnnotations.stream().forEach(i->item.getChildren().add(
					BytecodeUtils.getBytecodeHandler(i.getClass()).impl_getNode(i)));
		if(node.node.visibleTypeAnnotations!=null)
			node.node.visibleTypeAnnotations.stream().forEach(i->item.getChildren().add(
					BytecodeUtils.getBytecodeHandler(i.getClass()).impl_getNode(i)));
		
		ListIterator<AbstractInsnNode> iterator = node.node.instructions.iterator();
		while(iterator.hasNext()){
			AbstractInsnNode i = iterator.next();
			if(i instanceof LdcInsnNode)
				item.getChildren().add(BytecodeUtils.getBytecodeHandler(LdcInsnNodeWarpper.class).impl_getNode(new LdcInsnNodeWarpper((LdcInsnNode) i, node)));
			else if(i instanceof LabelNode)
				item.getChildren().add(BytecodeUtils.getBytecodeHandler(LabelNodeWarpper.class).impl_getNode(new LabelNodeWarpper((LabelNode) i, node)));
		}
		
		return item;
	}
}
