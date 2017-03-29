package me.mouse.ube;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.TraceSignatureVisitor;

public final class BytecodeUtils {

	private BytecodeUtils() {}

	private static final Map<Class<?>, BytecodeHandler<?>> BYTECODE_HANDLERS = new HashMap<>();

	static {
		BYTECODE_HANDLERS.put(ClassNode.class, new BytecodeHandler.ClassHandler());
		BYTECODE_HANDLERS.put(FieldNode.class, new BytecodeHandler.FieldHandler());
		BYTECODE_HANDLERS.put(MethodNode.class, new BytecodeHandler.MethodHandler());
		BYTECODE_HANDLERS.put(AnnotationNode.class, new BytecodeHandler.AnnotationHandler());
	}
	
	@SuppressWarnings("unchecked")
	public static <T> BytecodeHandler<T> getBytecodeHandler(Class<T> clazz){
		return (BytecodeHandler<T>) BYTECODE_HANDLERS.get(clazz);
	}

	public static String getAccess(final int access) {
		StringBuilder sb = new StringBuilder();
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			sb.append("public ");
		}
		if ((access & Opcodes.ACC_PRIVATE) != 0) {
			sb.append("private ");
		}
		if ((access & Opcodes.ACC_PROTECTED) != 0) {
			sb.append("protected ");
		}
		if ((access & Opcodes.ACC_FINAL) != 0) {
			sb.append("final ");
		}
		if ((access & Opcodes.ACC_STATIC) != 0) {
			sb.append("static ");
		}
		if ((access & Opcodes.ACC_SYNCHRONIZED) != 0) {
			sb.append("synchronized ");
		}
		if ((access & Opcodes.ACC_VOLATILE) != 0) {
			sb.append("volatile ");
		}
		if ((access & Opcodes.ACC_TRANSIENT) != 0) {
			sb.append("transient ");
		}
		if ((access & Opcodes.ACC_ABSTRACT) != 0) {
			sb.append("abstract ");
		}
		if ((access & Opcodes.ACC_STRICT) != 0) {
			sb.append("strictfp ");
		}
		if ((access & Opcodes.ACC_SYNTHETIC) != 0) {
			sb.append("synthetic ");
		}
		if ((access & Opcodes.ACC_MANDATED) != 0) {
			sb.append("mandated ");
		}
		if ((access & Opcodes.ACC_ENUM) != 0) {
			sb.append("enum ");
		}
		return sb.toString();
	}
	
	public static TraceSignatureVisitor getTraceSignatureVisitor(String desc){
		TraceSignatureVisitor v = new TraceSignatureVisitor(0);
		SignatureReader r = new SignatureReader(desc);
		r.accept(v);
		return v;
	}
}
