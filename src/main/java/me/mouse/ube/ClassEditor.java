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
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

public class ClassEditor extends AnchorPane{
	
	@FXML
	protected TreeView<String> treeView;
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
}
