
public class OSTree extends SearchTree {
	
	/**
	 * Constructor for OS Tree
	 */
	 public OSTree() { 
	      super(); 
	   } 
	
	/**
	 * Insert a given OS node to the tree
	 * @param node
	 */
	 public void insert (OSTreeNode node) {
		 
		 // Increase size for all the nodes above the new node
		 OSTreeNode tempNode = getRoot(); 
		 while (tempNode != null) { 
			 tempNode.size++;
			 if (node.getKey() < tempNode.getKey()) {
				 tempNode = tempNode.getLeft();
			 } else {
				 tempNode = tempNode.getRight(); 
			 }
	    }
		// Make in the insert
		super.insert(node);
	}
	
	/**
	 * Delete a given node from the tree
	 * @param node
	 */
	public void delete (OSTreeNode node) {
		
		OSTreeNode deletedNode = node;
		if (!deletedNode.hasOneChild() && !deletedNode.isLeaf()) {
			deletedNode = (OSTreeNode) successor(deletedNode);
		}
		
		// Decrease size of all the nodes above the given node
		OSTreeNode tempNode = getRoot();
		while (tempNode != deletedNode) {
			tempNode.size--;
			if (deletedNode.getKey() < tempNode.getKey()) {
				tempNode = tempNode.getLeft();
			} else {
				tempNode = tempNode.getRight(); 
			} 	 
		}
		
		// Delete the node
		super.delete(node);
	}
	
	/**
	 * return the i-th node in the list
	 * @param i
	 * @return return the i-th node in the list
	 */
	   public OSTreeNode select (int i) { 
		  
		  // Start from the min node
		  OSTreeNode tempNode = (OSTreeNode) this.findMin();
		   
		  // the the successor i times to find th i-th node in the list
		  for (int j = 1; j < i; j++) {
			   tempNode = (OSTreeNode) this.successor(tempNode);
		   }
		   return tempNode;
	   } 
	
	/**
	 * return the rank of a node
	 * @param x
	 * @return return the rank of a node
	 */
	   public int rank (OSTreeNode node) { 		   
		   
		   int counter = 1;
		   OSTreeNode tempNode = node;
		   
		   // Keep going to the predecessor until reaching null and count the times
		   while (this.predecessor(tempNode) != null) {
			   counter++;
			   tempNode = (OSTreeNode) super.predecessor(tempNode);
		   }
		   return counter;
	   } 

	   /**
	    * return root
	    */
	   public OSTreeNode getRoot() { 
	      return (OSTreeNode)super.getRoot(); 
	   } 
	
	
	   public static void main (String[] args) {
			OSTree tree = new OSTree();
			int vals[] = {50, 30, 70, 20, 40, 60, 80,
						15, 23, 32, 47, 56, 69, 71, 85,
						29, 36, 51, 74, 58, 22};
			for (int i: vals)
				tree.insert (new OSTreeNode (i));
			try {
				OSTreeDisplay.draw (tree);
			} catch (OSTreeDisplayException e) {
				System.out.println (e.getMessage());
			}
			System.out.println("Max Node is: " + tree.findMax().getKey());
			System.out.println("Min Node is: " + tree.findMin().getKey());
			tree.delete ((OSTreeNode)tree.find (85));
			tree.delete ((OSTreeNode)tree.find (15));
			tree.delete ((OSTreeNode)tree.find (23));
			tree.delete ((OSTreeNode)tree.find (30));
			try {
				OSTreeDisplay.draw (tree);
			} catch (OSTreeDisplayException e) {
				System.out.println (e.getMessage());
			}
			System.out.println(System.lineSeparator() + "After delete:");
			System.out.println("Max Node is: " + tree.findMax().getKey());
			System.out.println("Min Node is: " + tree.findMin().getKey());
			System.out.println("Successor of 36 is: " + tree.successor(tree.find(36)).getKey());
			System.out.println("Predecessor of 32 is: " + tree.predecessor(tree.find(32)).getKey());
			System.out.println("Predecessor of 40 is: " + tree.predecessor(tree.find(40)).getKey());
			System.out.println("Predecessor of 47 is: " + tree.predecessor(tree.find(47)).getKey());
			System.out.println("Predecessor of 50 is: " + tree.predecessor(tree.find(50)).getKey());
			System.out.println("Successor of 50 is: " + tree.successor(tree.find(50)).getKey());
			System.out.println("Successor of 70 is: " + tree.successor(tree.find(70)).getKey());
			System.out.println("Successor of 71 is: " + tree.successor(tree.find(71)).getKey());
			System.out.println("Successor of 69 is: " + tree.successor(tree.find(69)).getKey());
			System.out.println("Successor of 32 is: " + tree.successor(tree.find(32)).getKey());
			System.out.println("Predecessor of 69 is: " + tree.predecessor(tree.find(69)).getKey());
			System.out.println("height of 20 is: " + tree.height((OSTreeNode)tree.find (20)));
			System.out.println("height of 50 is: " + tree.height((OSTreeNode)tree.find (50)));
			System.out.println("height of 60 is: " + tree.height((OSTreeNode)tree.find (60)));
			System.out.println("height of 70 is: " + tree.height((OSTreeNode)tree.find (70)));
			System.out.println("depth of 20 is: " + tree.depth((OSTreeNode)tree.find (20)));
			System.out.println("depth of 50 is: " + tree.depth((OSTreeNode)tree.find (50)));
			System.out.println("depth of 60 is: " + tree.depth((OSTreeNode)tree.find (60)));
			System.out.println("depth of 70 is: " + tree.depth((OSTreeNode)tree.find (70)));
			System.out.println("Rank of 20 is: " + tree.rank((OSTreeNode) tree.find(20)));
			System.out.println("Rank of 22 is: " + tree.rank((OSTreeNode) tree.find(22)));
			System.out.println("Rank of 29 is: " + tree.rank((OSTreeNode) tree.find(29)));
			System.out.println("Rank of 32 is: " + tree.rank((OSTreeNode) tree.find(32)));
			System.out.println("Rank of 36 is: " + tree.rank((OSTreeNode) tree.find(36)));
			System.out.println("Rank of 40 is: " + tree.rank((OSTreeNode) tree.find(40)));
			System.out.println("Rank of 47 is: " + tree.rank((OSTreeNode) tree.find(47)));
			System.out.println("Rank of 50 is: " + tree.rank((OSTreeNode) tree.find(50)));
			System.out.println("Select node number 6: " + tree.select(6).getKey());
			System.out.println("Select node number 9: " + tree.select(9).getKey());
			System.out.println("Select node number 13: " + tree.select(13).getKey());
			System.out.println("Select node number 21: " + tree.select(21).getKey());
			System.out.println("Select node number 15: " + tree.select(15).getKey());
			System.out.println("Select node number 19: " + tree.select(19).getKey());
	   }
}
