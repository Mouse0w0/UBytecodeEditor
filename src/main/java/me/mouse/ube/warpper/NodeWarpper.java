package me.mouse.ube.warpper;

public class NodeWarpper<N,P> {

	public P parent;
	public N node;
	
	public NodeWarpper(N node,P parent) {
		this.node = node;
		this.parent = parent;
	}
}
