package me.mouse.ube;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;
import org.objectweb.asm.util.TraceSignatureVisitor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

public class ClassEditor extends AnchorPane{
	
	@FXML
	protected TreeView<Object> treeView;
	@FXML
	protected TextArea textArea;
	
	private File classFile;
	private ClassNode classNode;
	
	public ClassEditor(){
		//载入界面
		FXMLLoader loader = new FXMLLoader(ClassEditor.class.getResource("ClassEditor.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		loader.setCharset(StandardCharsets.UTF_8);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		treeView.setCellFactory(param->new ClassNodeTreeCell());
	}
	
	public ClassEditor(File classFile) {
		this();
		
		//载入Class
		this.classFile = classFile;
		try{
			InputStream is = new FileInputStream(classFile);
			ClassReader cr = new ClassReader(is);
			is.close();
			classNode = new ClassNode();
			cr.accept(classNode, 0);
			
			showASM();
			updateTree(classNode);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public boolean save(){
		try {
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			classNode.accept(cw);
			FileOutputStream output = new FileOutputStream(classFile);
			output.write(cw.toByteArray());
			output.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void showASM(){
		TraceClassVisitor cv = new TraceClassVisitor(null, new Textifier(), null);
		classNode.accept(cv);
		textArea.clear();
		textArea.setText(asmtext(cv.p.text));
	}
	
	public void showASMSource(){
		TraceClassVisitor cv = new TraceClassVisitor(null, new ASMifier(), null);
		classNode.accept(cv);
		textArea.clear();
		textArea.setText(asmtext(cv.p.text));
	}

	private String asmtext(List<?> text){
		StringBuilder sb = new StringBuilder();
		for(Object i:text){
			if(i instanceof String) sb.append(i); 
			else if(i instanceof List) sb.append(asmtext((List<?>)i));
		}
		return sb.toString();
	}
	
	public void updateTree(ClassNode classNode){
		TreeItem<Object> clazz = new TreeItem<Object>(classNode);
		treeView.setRoot(clazz);
		for(Object o:classNode.fields) clazz.getChildren().add(new TreeItem<Object>(o));
		for(Object o:classNode.methods) clazz.getChildren().add(new TreeItem<Object>(o));
	}
	
	private class ClassNodeTreeCell extends TreeCell<Object>{
		
        @SuppressWarnings("unchecked")
		@Override
        public void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
            	StringBuilder sb = new StringBuilder();
            	switch(item.getClass().getSimpleName()){
            		case "ClassNode":{
            			ClassNode n = (ClassNode) item;
            			
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
            	            for (String s:(List<String>)n.interfaces) {
            	                sb.append(s.replaceAll("/", ".")).append(',');
            	            }
            	        }
            	        
            			break;
            		}
            		case "FieldNode":{
            			FieldNode n = (FieldNode) item;
            			
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
            	        
            			break;
            		}
            		case "MethodNode":{
            			MethodNode n = (MethodNode) item;
            			
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
            	        if ((classNode.access & Opcodes.ACC_INTERFACE) != 0
            	                && (n.access & Opcodes.ACC_ABSTRACT) == 0
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
            	        
            	        break;
            		}
            		case "AnnotationNode":{
            			
            		}
            	}
            	setText(sb.toString());
                setGraphic(getTreeItem().getGraphic());
            }
        }
        
        private String getAccess(final int access) {
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
	}
}
