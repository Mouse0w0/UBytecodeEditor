package com.github.mouse0w0.ube.wrapper;

import static com.github.mouse0w0.ube.util.BytecodeUtils.getAccess;

import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.TraceSignatureVisitor;

import javafx.scene.control.TreeItem;

public class MethodWrapper extends NodeWrapperBase<MethodNode>{
	
	public MethodWrapper(MethodNode source, ClassWrapper parent) {
		super(source, parent);
	}

	@Override
	public String getText() {
		MethodNode node = getSource();
		StringBuilder sb = new StringBuilder();

		sb.append(getAccess(node.access & ~Opcodes.ACC_VOLATILE));
		if ((node.access & Opcodes.ACC_NATIVE) != 0) {
			sb.append("native ");
		}
		if ((node.access & Opcodes.ACC_VARARGS) != 0) {
			sb.append("varargs ");
		}
		if ((node.access & Opcodes.ACC_BRIDGE) != 0) {
			sb.append("bridge ");
		}
		if ((((ClassWrapper)getParent()).getClassNode().access & Opcodes.ACC_INTERFACE) != 0 && (node.access & Opcodes.ACC_ABSTRACT) == 0
				&& (node.access & Opcodes.ACC_STATIC) == 0) {
			sb.append("default ");
		}

		TraceSignatureVisitor v = new TraceSignatureVisitor(0);
		SignatureReader r = new SignatureReader(node.desc);
		r.accept(v);
		String genericDecl = v.getDeclaration();
		String genericReturn = v.getReturnType();
		String genericExceptions = v.getExceptions();

		sb.append(genericReturn).append(' ').append(node.name).append(genericDecl);
		if (genericExceptions != null)
			sb.append(" throws ").append(genericExceptions);

		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected TreeItem<NodeWrapper<?>> createTreeItem() {
		TreeItem<NodeWrapper<?>> item = new TreeItem<NodeWrapper<?>>(this);
		if(getSource().visibleAnnotations != null)
			for(AnnotationNode an : (List<AnnotationNode>)getSource().visibleAnnotations) {
				item.getChildren().add(new AnnotationWrapper(an, this).getTreeItem());
			}
		return item;
	}

}
