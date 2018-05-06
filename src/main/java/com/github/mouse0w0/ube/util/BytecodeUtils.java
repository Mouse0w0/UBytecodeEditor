package com.github.mouse0w0.ube.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypeReference;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.TraceSignatureVisitor;

public final class BytecodeUtils {

	private BytecodeUtils() {} 
	
	public static ClassNode loadClassNode(Path path) throws IOException {
		try(InputStream is = Files.newInputStream(path)){
			ClassReader cr = new ClassReader(is);
			ClassNode classNode = new ClassNode();
			cr.accept(classNode, 0);
			return classNode;
		}
	}
	
	public static String getRealName(String internalName) {
		return internalName.replace('/', '.');
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
	
    public static String getTypeReference(final int typeRef) {
        TypeReference ref = new TypeReference(typeRef);
        switch (ref.getSort()) {
        case TypeReference.CLASS_TYPE_PARAMETER:
            return "CLASS_TYPE_PARAMETER "+ ref.getTypeParameterIndex();
        case TypeReference.METHOD_TYPE_PARAMETER:
        	return "METHOD_TYPE_PARAMETER "+ref.getTypeParameterIndex();
        case TypeReference.CLASS_EXTENDS:
        	return "CLASS_EXTENDS "+ref.getSuperTypeIndex();
        case TypeReference.CLASS_TYPE_PARAMETER_BOUND:
        	return new StringBuilder("CLASS_TYPE_PARAMETER_BOUND ")
        			.append(ref.getTypeParameterIndex()).append(", ")
        			.append(ref.getTypeParameterBoundIndex()).toString();
        case TypeReference.METHOD_TYPE_PARAMETER_BOUND:
        	return new StringBuilder("METHOD_TYPE_PARAMETER_BOUND ")
                    .append(ref.getTypeParameterIndex()).append(", ")
                    .append(ref.getTypeParameterBoundIndex()).toString();
        case TypeReference.FIELD:
            return "FIELD";
        case TypeReference.METHOD_RETURN:
            return "METHOD_RETURN";
        case TypeReference.METHOD_RECEIVER:
            return "METHOD_RECEIVER";
        case TypeReference.METHOD_FORMAL_PARAMETER:
            return "METHOD_FORMAL_PARAMETER "+ref.getFormalParameterIndex();
        case TypeReference.THROWS:
            return "THROWS "+ref.getExceptionIndex();
        case TypeReference.LOCAL_VARIABLE:
            return "LOCAL_VARIABLE";
        case TypeReference.RESOURCE_VARIABLE:
            return "RESOURCE_VARIABLE";
        case TypeReference.EXCEPTION_PARAMETER:
            return "EXCEPTION_PARAMETER "+ref.getTryCatchBlockIndex();
        case TypeReference.INSTANCEOF:
            return "INSTANCEOF";
        case TypeReference.NEW:
            return "NEW";
        case TypeReference.CONSTRUCTOR_REFERENCE:
            return "CONSTRUCTOR_REFERENCE";
        case TypeReference.METHOD_REFERENCE:
            return "METHOD_REFERENCE";
        case TypeReference.CAST:
            return "CAST "+ref.getTypeArgumentIndex();
        case TypeReference.CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT:
            return "CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT "+ref.getTypeArgumentIndex();
        case TypeReference.METHOD_INVOCATION_TYPE_ARGUMENT:
            return "METHOD_INVOCATION_TYPE_ARGUMENT "+ref.getTypeArgumentIndex();
        case TypeReference.CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT:
            return "CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT "+ref.getTypeArgumentIndex();
        case TypeReference.METHOD_REFERENCE_TYPE_ARGUMENT:
            return "METHOD_REFERENCE_TYPE_ARGUMENT "+ref.getTypeArgumentIndex();
        default :
        	return "";
        }
    }
	
	public static TraceSignatureVisitor getTraceSignatureVisitor(String desc){
		TraceSignatureVisitor v = new TraceSignatureVisitor(0);
		SignatureReader r = new SignatureReader(desc);
		r.accept(v);
		return v;
	}
}
