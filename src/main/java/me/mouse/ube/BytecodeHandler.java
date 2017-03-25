package me.mouse.ube;

import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceSignatureVisitor;

import static me.mouse.ube.BytecodeUtils.*;

public interface BytecodeHandler {
	
	String getText(Object item, ClassNode root);

	public static final class ClassHandler implements BytecodeHandler {

		@SuppressWarnings("unchecked")
		@Override
		public String getText(Object item, ClassNode root) {
			ClassNode n = (ClassNode) item;
			
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

	public static final class FieldHandler implements BytecodeHandler {

		@Override
		public String getText(Object item, ClassNode root) {
			FieldNode n = (FieldNode) item;
			
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

	public static final class MethodHandler implements BytecodeHandler {

		@Override
		public String getText(Object item, ClassNode root) {
			MethodNode n = (MethodNode) item;
			
			StringBuilder sb = new StringBuilder();

			sb.append(getAccess(n.access & ~Opcodes.ACC_VOLATILE));
			if ((n.access & Opcodes.ACC_NATIVE) != 0) {
				sb.append("native ");
			}
			if ((n.access & Opcodes.ACC_VARARGS) != 0) {
				sb.append("varargs ");
			}
			if ((n.access & Opcodes.ACC_BRIDGE) != 0) {
				sb.append("bridge ");
			}
			if ((root.access & Opcodes.ACC_INTERFACE) != 0 && (n.access & Opcodes.ACC_ABSTRACT) == 0
					&& (n.access & Opcodes.ACC_STATIC) == 0) {
				sb.append("default ");
			}

			TraceSignatureVisitor v = new TraceSignatureVisitor(0);
			SignatureReader r = new SignatureReader(n.desc);
			r.accept(v);
			String genericDecl = v.getDeclaration();
			String genericReturn = v.getReturnType();
			String genericExceptions = v.getExceptions();

			sb.append(genericReturn).append(' ').append(n.name).append(genericDecl);
			if (genericExceptions != null)
				sb.append(" throws ").append(genericExceptions);

			return sb.toString();
		}
	}
	
	public static final class AnnotationHandler implements BytecodeHandler{

		@Override
		public String getText(Object item, ClassNode root) {
			AnnotationNode n = (AnnotationNode) item;
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
		
	}
}
