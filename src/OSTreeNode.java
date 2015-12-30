
public class OSTreeNode extends SearchTreeNode {
	int size;

	public OSTreeNode(int key, Object data) {
		super(key, data);
		this.size = 1;
	}

	public OSTreeNode(int key) {
		super(key, null);
		this.size = 1;
	}

	public void setFather(OSTreeNode node) { 
	      super.setFather(node); 
	}
	
	public OSTreeNode getFather() { 
	      return (OSTreeNode)super.getFather(); 
	} 
	 
	public OSTreeNode getLeft() { 
		return (OSTreeNode)super.getLeft(); 
	} 
	 
	public void setLeft (OSTreeNode left) { 
		super.setLeftChild(left); 
   	} 
 
   	public OSTreeNode getRight() { 
   		return (OSTreeNode)super.getRight(); 
   	} 
 
   	public void setRight (OSTreeNode right) { 
   		super.setRightChild(right); 
   	}
	   
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}

	

