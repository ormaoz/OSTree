
public class SearchTreeNode {
	int key;
	Object data;
	SearchTreeNode rightChild;
	SearchTreeNode leftChild;
	SearchTreeNode father;
	
	public SearchTreeNode(int key, Object data) {
		this.key = key;
		this.data = data;
	}
	
	/**
	 * Checks if a node is a leaf with no sons.
	 * @return true if node is a leaf
	 */
	public boolean isLeaf() {
		return this.getLeft() == null && this.getRight() == null;
	}
	
	/**
	 * Checks if a node has a only one child.
	 * @return true if it has only one child
	 */
	public boolean hasOneChild() {
		return (this.getLeft() == null && this.getRight() != null) || 
				(this.getLeft() != null && this.getRight() == null);
	}
	
	/**
	 * Checks if a node is right or left son.
	 * @return true if the node is a left son or false if the node is right son
	 */
	public boolean isLeftSon() {
		return this.getFather().getLeft() == this;
	}
	
	
	
	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public SearchTreeNode getRight() {
		return rightChild;
	}

	public void setRightChild(SearchTreeNode rightChild) {
		this.rightChild = rightChild;
	}

	public SearchTreeNode getLeft() {
		return leftChild;
	}

	public void setLeftChild(SearchTreeNode leftChild) {
		this.leftChild = leftChild;
	}

	public SearchTreeNode getFather() {
		return father;
	}

	public void setFather(SearchTreeNode father) {
		this.father = father;
	}
	
}
