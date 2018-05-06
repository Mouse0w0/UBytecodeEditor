package com.github.mouse0w0.ube.wrapper;

import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.util.TraceSignatureVisitor;

import javafx.scene.control.TreeItem;

public class AnnotationWrapper extends NodeWrapperBase<AnnotationNode>{

	public AnnotationWrapper(AnnotationNode source, NodeWrapper<?> parent) {
		super(source, parent);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getText() {
		StringBuilder sb = new StringBuilder();
		
        sb.append('@');
		TraceSignatureVisitor sv = new TraceSignatureVisitor(0);
		SignatureReader r = new SignatureReader(getSource().desc);
		r.acceptType(sv);
		sb.append(sv.getDeclaration());
        sb.append('(');
        if(getSource().values!=null)
        	getSource().values.forEach(i->sb.append(i));
        sb.append(")");
        
		return sb.toString();
	}

	@Override
	protected TreeItem<NodeWrapper<?>> createTreeItem() {
		TreeItem<NodeWrapper<?>> item = new TreeItem<NodeWrapper<?>>(this);
		return item;
	}

}
