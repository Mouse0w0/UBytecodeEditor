package me.mouse.ube;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypeReference;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.TraceSignatureVisitor;

import me.mouse.ube.handler.*;
import me.mouse.ube.warpper.ClassNodeWarpper;
import me.mouse.ube.warpper.FieldInsnNodeWarpper;
import me.mouse.ube.warpper.FieldNodeWarpper;
import me.mouse.ube.warpper.LabelNodeWarpper;
import me.mouse.ube.warpper.LdcInsnNodeWarpper;
import me.mouse.ube.warpper.MethodNodeWarpper;
import me.mouse.ube.warpper.NodeWarpper;

public final class BytecodeUtils {

	private BytecodeUtils() {}

	private static final Map<Class<?>, BytecodeHandler<?>> BYTECODE_HANDLERS = new HashMap<>();

	static {
		BYTECODE_HANDLERS.put(ClassNodeWarpper.class, new ClassHandler());
		BYTECODE_HANDLERS.put(FieldNodeWarpper.class, new FieldHandler());
		BYTECODE_HANDLERS.put(MethodNodeWarpper.class, new MethodHandler());
		BYTECODE_HANDLERS.put(AnnotationNode.class, new AnnotationHandler());
		BYTECODE_HANDLERS.put(TypeAnnotationNode.class, new TypeAnnotationHandler());
		BYTECODE_HANDLERS.put(LabelNodeWarpper.class, new LabelHandler());
		BYTECODE_HANDLERS.put(LdcInsnNodeWarpper.class, new LdcInsnHandler());
		BYTECODE_HANDLERS.put(FieldInsnNodeWarpper.class, new FieldInsnHandler());
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
    
    public static String getFrameTypes(final int n, final Object[] o) {
    	StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                sb.append(' ');
            }
            if (o[i] instanceof String) {
                String desc = (String) o[i];
                if (desc.startsWith("[")) {
                	sb.append(getTraceSignatureVisitor(desc).getDeclaration());
                } else {
                	sb.append(getTraceSignatureVisitor(desc).getDeclaration());
                }
            } else if (o[i] instanceof Integer) {
                switch (((Integer) o[i]).intValue()) {
                case 0:
                    sb.append("T");
                    break;
                case 1:
                	sb.append("I");
                    break;
                case 2:
                	sb.append("F");
                    break;
                case 3:
                	sb.append("D");
                    break;
                case 4:
                	sb.append("J");
                    break;
                case 5:
                	sb.append("N");
                    break;
                case 6:
                	sb.append("U");
                    break;
                }
            } else {
                appendLabel((Label) o[i]);
            }
        }
    }
	
	public static TraceSignatureVisitor getTraceSignatureVisitor(String desc){
		TraceSignatureVisitor v = new TraceSignatureVisitor(0);
		SignatureReader r = new SignatureReader(desc);
		r.accept(v);
		return v;
	}
}
