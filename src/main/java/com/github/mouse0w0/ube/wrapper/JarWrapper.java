package com.github.mouse0w0.ube.wrapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.github.mouse0w0.ube.UBytecodeEditor;

import javafx.scene.control.TreeItem;

public class JarWrapper extends NodeWrapperBase<Path>{

	public JarWrapper(Path source) {
		super(source, null);
	}

	@Override
	public String getText() {
		return getSource().toString();
	}

	@Override
	protected TreeItem<NodeWrapper<?>> createTreeItem() {
		TreeItem<NodeWrapper<?>> item = new TreeItem<>(this);
		try {
			Files.walk(getSource())
					.filter(path -> {
						try {
							return Files.isDirectory(path) && Files.walk(path,1).anyMatch(subPath -> subPath.toString().endsWith(".class"));
						} catch (IOException e) {
							UBytecodeEditor.LOGGER.error(e.getMessage(), e);
							return false;
						}
					}).map(path -> new PackageWrapper(path, this).getTreeItem())
					.forEach(subItem -> item.getChildren().add(subItem));
		} catch (IOException e) {
			UBytecodeEditor.LOGGER.error(e.getMessage(), e);
		}
		return item;
	}

}
