package com.github.mouse0w0.ube.handler;

import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.util.TraceSignatureVisitor;

import javafx.scene.control.TreeItem;

public class AnnotationHandler implements BytecodeHandler<AnnotationNode>{

	@SuppressWarnings("unchecked")
	@Override
	public String getText(AnnotationNode n) {
		StringBuilder sb = new StringBuilder();
		
        sb.append('@');
		TraceSignatureVisitor sv = new TraceSignatureVisitor(0);
		SignatureReader r = new SignatureReader(n.desc);
		r.acceptType(sv);
		sb.append(sv.getDeclaration());
        sb.append('(');
        if(n.values!=null)
        	n.values.stream().forEach(i->sb.append(i.toString()));
        sb.append(")");
		
		return sb.toString();
	}

	@Override
	public TreeItem<?> getNode(AnnotationNode node) {
		return new TreeItem<>(node);
	}
}
