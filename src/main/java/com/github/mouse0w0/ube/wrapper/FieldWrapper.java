package com.github.mouse0w0.ube.wrapper;

import static com.github.mouse0w0.ube.util.BytecodeUtils.getAccess;

import java.util.List;

import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.util.TraceSignatureVisitor;

import javafx.scene.control.TreeItem;

public class FieldWrapper extends NodeWrapperBase<FieldNode>{

	public FieldWrapper(FieldNode source, ClassWrapper parent) {
		super(source, parent);
	}

	@Override
	public String getText() {
		FieldNode node = getSource();
		StringBuilder sb = new StringBuilder();

		sb.append(getAccess(node.access));

		TraceSignatureVisitor sv = new TraceSignatureVisitor(0);
		SignatureReader r = new SignatureReader(node.desc);
		r.acceptType(sv);
		sb.append(sv.getDeclaration()).append(" ");

		sb.append(node.name);
		if (node.value != null) {
			sb.append(" = ");
			if (node.value instanceof String) {
				sb.append('\"').append(node.value).append('\"');
			} else {
				sb.append(node.value);
			}
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected TreeItem<NodeWrapper<?>> createTreeItem() {
		TreeItem<NodeWrapper<?>> item = new TreeItem<NodeWrapper<?>>(this);
		if((List<AnnotationNode>)getSource().visibleAnnotations != null)
			for(AnnotationNode an : (List<AnnotationNode>)getSource().visibleAnnotations) {
				item.getChildren().add(new AnnotationWrapper(an, this).getTreeItem());
			}
		return item;
	}

}
