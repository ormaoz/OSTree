
public class SearchTree {
	
	private SearchTreeNode root;
	
	/**
	 * Constructor for a search tree
	 */
	public SearchTree() {
		root = null;
	}
	
	/**
	 * Checks if a tree is empty
	 * @return true if tree is empty
	 */
	public boolean isEmpty() {
		return root == null;
	}
	
	/**
	 * Insert a new node to this search tree
	 * @param node
	 */
	public void insert (SearchTreeNode node) {
		
		if (isEmpty()) {
			root = node;
			return;
		}
		
		// Save a temporary node and his father (Pointers) for the search
		SearchTreeNode tempNode = getRoot();
		SearchTreeNode tempFather = tempNode;
		
		// As long as we didn't reach the end of the tree
		while (tempNode != null) {
			
			tempFather = tempNode;
			
			// Check if the given node is bigger or smaller than the current one
			// and change the pointers accordingly
			if (tempNode.getKey() < node.getKey()) {	
				tempNode = tempNode.getRight();
			} else {
				tempNode = tempNode.getLeft();
			}
		}
		
		// Set father for new node. 
	    node.setFather (tempFather); 
		
		// After we reached the end of the tree, insert the node in its right place
		if (node.getKey() > tempFather.getKey()) {
			tempFather.setRightChild(node);
		} else {
			tempFather.setLeftChild(node);
		}
	}
	
	/**
	 * Delete a certain node from the tree
	 * @param node
	 */
	public void delete (SearchTreeNode node) {
		
		// Case 1: node is a leaf or has one child
		if (node.isLeaf() || node.hasOneChild()) {
			
			SearchTreeNode tempNode = node;
			SearchTreeNode tempFather = tempNode.getFather();
			
			// Find the child
			if (node.getLeft() != null) {
				tempNode = node.getLeft();
			} else {
				if (node.getRight() != null) {
					tempNode = node.getRight();
				} else {
					tempNode = null;
				}
			}
			
			// Parent of child's parent becomes its new parent
			if (tempNode != null) {
				tempNode.setFather(tempFather);
			}
			
			// The child replaces the deleted node
			if (node.equals(tempFather.getLeft())) {
				tempFather.setLeftChild(tempNode);
			} else {
				tempFather.setRightChild(tempNode);
			}
		
		// Case 2: node has two children
		} else {
			SearchTreeNode successor = successor(node);
			node.setKey(successor.getKey());
			node.setData(successor.getData());
			delete(successor);
		}
	}
	
	/**
	 * Find and return a node in the tree with the given key
	 * @param key
	 * @return the node with the given key or null if key wasn't found
	 */
	public SearchTreeNode find (int key) {
		 SearchTreeNode tempNode = getRoot();
		
		 while (tempNode != null && tempNode.getKey() != key) {
			 if (key < tempNode.getKey()) {
				tempNode = tempNode.getLeft();
			} else {
				tempNode = tempNode.getRight();
			}
		 }
		 return tempNode;
	}
	
	/**
	 * Find and return the smallest node in the tree
	 * @return the min node
	 */
	public SearchTreeNode findMin() {
		
		// Set Pointers for node and father
		SearchTreeNode tempNode = getRoot();
		SearchTreeNode tempFather = null;
		
		// As long as it didn't reach the end, keep going left, looking for the smallest node
		while (tempNode != null) {
			tempFather = tempNode;
			tempNode = tempNode.getLeft();
		}
		return tempFather;
	}
	
	/**
	 * Find and return the biggest node in the tree
	 * @return the max node
	 */
	public SearchTreeNode findMax() {
		
		// Set Pointers for node and father
		SearchTreeNode tempNode = getRoot();
		SearchTreeNode tempFather = null;
		
		// As long as it didn't reach the end, keep going right, looking for the biggest node
		while (tempNode != null) {
			tempFather = tempNode;
			tempNode = tempNode.getRight();
		}
		return tempFather;
	}
	
	/**
	 * Find and return the successor of a given node
	 * @param node
	 * @return the successor
	 */
	public SearchTreeNode successor(SearchTreeNode node) {
		
		// If the node has a right child
		if (node.getRight() != null){
		
			SearchTreeNode tempNode = node.getRight();
			SearchTreeNode tempFather = null;
			
			// Goes down in the tree until reaches the most left leaf
			while (tempNode != null) {
				tempFather = tempNode;
				tempNode = tempNode.getLeft();
			}
			return tempFather;
		}
		
		// If the node does not have a right child, and the node is a left child
		if (node.isLeftSon()) {
			return node.getFather();
	
		// If the node does not have a right child, and the node itself is a right child
		} else if (node.getFather() != null){
			
			SearchTreeNode tempNode = node;
			SearchTreeNode tempFather = tempNode.getFather();
			
			while (tempFather != getRoot() && tempNode == tempFather.getRight()) {
				tempNode = tempFather;
				tempFather = tempFather.getFather();	
			}
			return tempFather;
		
		// In case that there was no parent - the given node was the maximum node 
		} else {
			return null;
		}
	}
	
	/**
	 * Return the predecessor of a given node
	 * @param node
	 * @return the predecessor
	 */
	public SearchTreeNode predecessor (SearchTreeNode node) {

		// If node has left child
		if (node.getLeft() != null) {
			SearchTreeNode tempNode = node.getLeft();
			SearchTreeNode tempFather = tempNode.getFather();
			
			// Take left and go all the way to the right
			while (tempNode != null) {
				tempFather = tempNode;
				tempNode = tempNode.getRight();
			}
			return tempFather;
		
		// If node has no left child
		} else {
			SearchTreeNode tempNode = node.getFather(); 
			while (tempNode != null && node.isLeftSon()) { 
				node = tempNode; 
				tempNode = tempNode.getFather(); 
			} 
		    return tempNode; 
		}
	}
	
	/**
	 * Calculate the number of nodes on the longest path from the node to a leaf
	 * @param node
	 * @return
	 */
	public int height (SearchTreeNode node) {
		
		// If node is null or a leaf
		if (node == null || node.isLeaf()) {
			return 0;
		}

		// Get left and right heights
		int leftHeight = height(node.getLeft());
		int rightHeight = height(node.getRight());

		if (leftHeight < rightHeight) {
			return 1 + rightHeight;
		} else {
			return 1 + leftHeight;
		}
	}
	
	/**
	 * Calculate the number of edges from the node to the tree's root node
	 * @param node
	 * @return depth of the given node
	 */
	public int depth (SearchTreeNode node) {
		
		// Set Pointers for node and father
		SearchTreeNode tempNode = node;
		SearchTreeNode tempFather = tempNode;
		int counter = 0;
		
		// Keep going up until reaching to the root node and count every time
		while (tempFather != null) {
			tempNode = tempFather;
			tempFather = tempNode.getFather();
			counter++;
		}
		return counter;
		
	}

	public SearchTreeNode getRoot() {
		return this.root;
	} 
}
