package me.mouse.ube.warpper;

public class NodeWarpper<T1,T2> {

	public T2 parent;
	public T1 node;
	
	public NodeWarpper(T1 node,T2 parent) {
		this.node = node;
		this.parent = parent;
	}
}
