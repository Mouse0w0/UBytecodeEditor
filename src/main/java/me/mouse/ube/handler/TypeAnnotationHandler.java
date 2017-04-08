package me.mouse.ube.handler;

import static me.mouse.ube.BytecodeUtils.getTypeReference;

import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.tree.TypeAnnotationNode;
import org.objectweb.asm.util.TraceSignatureVisitor;

import javafx.scene.control.TreeItem;

public class TypeAnnotationHandler implements BytecodeHandler<TypeAnnotationNode>{

	@SuppressWarnings("unchecked")
	@Override
	public String getText(TypeAnnotationNode n) {
		StringBuilder sb = new StringBuilder();
		
        sb.append('@');
		TraceSignatureVisitor sv = new TraceSignatureVisitor(0);
		SignatureReader r = new SignatureReader(n.desc);
		r.acceptType(sv);
		sb.append(sv.getDeclaration());
        sb.append('(');
        if(n.values!=null)
        	n.values.stream().forEach(i->sb.append(i.toString()));
        sb.append(") : ");
        sb.append(getTypeReference(n.typeRef));
        sb.append(", ");
        sb.append(n.typePath);
		
		return sb.toString();
	}

	@Override
	public TreeItem<?> getNode(TypeAnnotationNode node) {
		return new TreeItem<>(node);
	}
}
