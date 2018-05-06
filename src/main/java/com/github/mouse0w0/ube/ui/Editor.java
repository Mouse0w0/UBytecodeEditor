package com.github.mouse0w0.ube.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

import com.github.mouse0w0.ube.UBytecodeEditor;
import com.github.mouse0w0.ube.util.FXUtils;
import com.github.mouse0w0.ube.util.JarUtils;
import com.github.mouse0w0.ube.wrapper.ClassWrapper;
import com.github.mouse0w0.ube.wrapper.JarWrapper;
import com.github.mouse0w0.ube.wrapper.NodeWrapper;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

public final class Editor extends AnchorPane implements Controller{

	private final Path path;
	
	@FXML
	private TreeView<NodeWrapper<?>> treeView;
	@FXML
	private TextArea textArea;
	
	private NodeWrapper<?> root;
	
	private NodeWrapper<?> editingNode;
	
	public Editor(Path path) {
		this.path = path;
		try {
			loadFxml("ClassEditor.fxml");
			treeView.setCellFactory(view -> new EditorTreeCell());
			load(path);
		} catch (IOException e) {
			UBytecodeEditor.LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void load(Path path) throws IOException {
		if (path.toString().endsWith(".class"))
			loadClass(path);
		else
			loadJar(path);
	}
	
	private void loadClass(Path path) throws IOException {
		root = new ClassWrapper(path, null);
		treeView.setRoot(root.getTreeItem());
		treeView.setShowRoot(true);
	}
	
	private void loadJar(Path path) throws IOException {
		Path tempUnjarPath = Files.createTempDirectory(path.getFileName().toString().replace('.', '_'));
		JarUtils.unjar(path, tempUnjarPath);
		root = new JarWrapper(tempUnjarPath);
		treeView.setRoot(root.getTreeItem());
		treeView.setShowRoot(false);
	}

	public void save() {
		
	}

	public void showBytecode() {
		NodeWrapper<?> node = treeView.getSelectionModel().getSelectedItem().getValue();
		while(node != null && !(node instanceof ClassWrapper)) {
			node = node.getParent();
		}
		if(node != null) 
			showBytecode(((ClassWrapper)node).getClassNode());
	}
	
	private void showBytecode(ClassNode node) {
		TraceClassVisitor cv = new TraceClassVisitor(null, new Textifier(), null);
		node.accept(cv);
		FXUtils.show(new TextReader(formatASMText(cv.p.text)));
	}

	public void showAsmSource() {
		NodeWrapper<?> node = treeView.getSelectionModel().getSelectedItem().getValue();
		while(node != null && !(node instanceof ClassWrapper)) {
			node = node.getParent();
		}
		if(node != null) 
			showAsmSource(((ClassWrapper)node).getClassNode());
	}
	
	private void showAsmSource(ClassNode node) {
		TraceClassVisitor cv = new TraceClassVisitor(null, new ASMifier(), null);
		node.accept(cv);
		FXUtils.show(new TextReader(formatASMText(cv.p.text)));
	}

	private String formatASMText(List<?> text) {
		StringBuilder sb = new StringBuilder();
		for (Object obj : text) {
			if (obj instanceof String)
				sb.append(obj);
			else if (obj instanceof List)
				sb.append(formatASMText((List<?>) obj));
		}
		return sb.toString();
	}

	public Path getPath() {
		return path;
	}
}
