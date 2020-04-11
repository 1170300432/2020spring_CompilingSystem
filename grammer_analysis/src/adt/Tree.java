package adt;

import java.util.ArrayList;
import java.util.List;

public class Tree{
	
	private List<Tree> children;
	private Data data;
	private int level;
	
	public Tree(Data data) {
		this.children = null;
		this.data = data;
		this.level = 1;
	}
	
	public void addChild(Tree tree) {
		if(this.children == null) {
			this.children = new ArrayList<Tree>();
			this.data.setLine(tree.data.getLine());
		}
		this.children.add(tree);
		tree.level = this.level + 1;
		this.data.setLine(Math.min(this.data.getLine(), tree.data.getLine()));
	}
	
	public void traversal() {
		if(this.childIsEmpty()) {
			return;
		}
		int i = 1;
		while(i < this.level) {
			System.out.print(" ");
			System.out.print(" ");
			i++;
		}
		System.out.println(this.data.getData() + " (" + this.data.getLine() + ")");
		if(this.children != null) {
			int length = this.children.size();
			for(i = 0; i < length; i++) {
				this.children.get(i).traversal();
			}			
		}
	}
	
	private boolean childIsEmpty() {
		boolean flag = false;
		if(this.children != null) {
			int length = this.children.size();
			for(int i = 0; i < length; i++) {
				Tree childTree = this.children.get(i);
				if(childTree.children == null && !childTree.data.getData().equalsIgnoreCase("epy")) {
					flag = false;
				} else if (childTree.children == null && childTree.data.getData().equalsIgnoreCase("epy")) {
					flag = true;
				} else {
					flag = childTree.childIsEmpty();
				}
				if(flag == false) {
					return flag;
				}
			}			
		}
		return flag;
	}
	
}