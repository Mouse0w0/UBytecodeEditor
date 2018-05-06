package com.github.mouse0w0.ube.wrapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.github.mouse0w0.ube.UBytecodeEditor;

import javafx.scene.control.TreeItem;

public class PackageWrapper extends NodeWrapperBase<Path>{
	
	private String packagePath;

	public PackageWrapper(Path source, JarWrapper parent) {
		super(source, parent);
		packagePath = parent.getSource().relativize(source).toString().replace('/', '.').replace('\\', '.');
	}

	@Override
	public String getText() {
		return packagePath;
	}

	@Override
	protected TreeItem<NodeWrapper<?>> createTreeItem() {
		TreeItem<NodeWrapper<?>> item = new TreeItem<NodeWrapper<?>>(this);
		try {
			Files.walk(getSource(),1)
					.filter(path -> !Files.isDirectory(path))
					.map(path -> new ClassWrapper(path, this).getTreeItem())
					.forEachOrdered(subItem -> item.getChildren().add(subItem));
		} catch (IOException e) {
			UBytecodeEditor.LOGGER.error(e.getMessage(), e);
		}
		return item;
	}
	
}
