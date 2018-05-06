package com.github.mouse0w0.ube.wrapper;

import javafx.scene.control.TreeItem;

public abstract class NodeWrapperBase<S> implements NodeWrapper<S> {

	private final S source;
	private final NodeWrapper<?> parent;
	
	private TreeItem<NodeWrapper<?>> item;

	public NodeWrapperBase(S source, NodeWrapper<?> parent) {
		this.source = source;
		this.parent = parent;
	}
	
	@Override
	public S getSource() {
		return source;
	}
	
	@Override
	public NodeWrapper<?> getParent() {
		return parent;
	}
	
	@Override
	public TreeItem<NodeWrapper<?>> getTreeItem() {
		if(item == null)
			item = createTreeItem();
		return item;
	}
	
	protected abstract TreeItem<NodeWrapper<?>> createTreeItem();
}
