package com.github.mouse0w0.ube.handler;

import com.github.mouse0w0.ube.warpper.LabelNodeWarpper;

import javafx.scene.control.TreeItem;

public class LabelHandler implements BytecodeHandler<LabelNodeWarpper>{

	@Override
	public String getText(LabelNodeWarpper item) {
        String name = item.parent.labelNames.get(item.node.getLabel());
        if (name == null) {
            name = "L" + item.parent.labelNames.size();
            item.parent.labelNames.put(item.node.getLabel(), name);
        }
		return name;
	}

	@Override
	public TreeItem<?> getNode(LabelNodeWarpper node) {
		return new TreeItem<>(node);
	}
}
