
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for drawing trees.
 *
 * Date: May 19, 2012
 *
 * @author Ran Eshel.
 */
public class OSTreeDisplay extends Frame {

   /**
    * Holds data for a node: x, y position and id to display.
    */
   private class Node {

      private int x;
      private int y;
      private int id;
      private int size;

      public Node (int x, int y, int id, int size) {
         this.x     = x;
         this.y     = y;
         this.id    = id;
         this.size  = size;
      }
   }


   /**
    * Holds data for a connecting line: ids of the two nodes to connect.
    */
   private class Connector {

      private int id1;
      private int id2;

      public Connector (int id1, int id2) {
         this.id1 = id1;
         this.id2 = id2;
      }
   }


   // Define colors for different application elements.
   private static final Color BACKGROUND_COLOR = Color.white;
   private static final Color NODE_COLOR       = new Color (130, 230, 170);
   private static final Color TEXT_COLOR       = Color.black;
   private static final Color LINE_COLOR       = Color.black;

   // Default node size.
   private static final int DEFAULT_X_SIZE    = 800;
   private static final int DEFAULT_Y_SIZE    = 600;
   private static final int DEFAULT_NODE_SIZE = 35;
   private static final int LINE_WIDTH        = 2;

   // Value which denotes and empty size field (used for RBTree, not OSTree).
   private static final int SIZE_NONE         = -1;

   // Lists of nodes and connectors to display.
   private List<Node> nodes;
   private List<Connector> connectors;

   // Radius of the circle drawn for each node.
   private int nodeSize;


   /**
    * Default constructor.
    * Creates a TreeDisplay with default window size (800 X 600)
    * and node size (35).
    */
   public OSTreeDisplay () {
      this (DEFAULT_X_SIZE, DEFAULT_Y_SIZE, DEFAULT_NODE_SIZE);
   }


   /**
    * Constructor.
    *
    * @param xSize x dimension of window.
    * @param ySize y dimension of window.
    * @param nodeSize radius of the circle drawn for each node.
    */
   public OSTreeDisplay (int xSize, int ySize, int nodeSize) {

      super ("Tree Display");
      setSize (xSize, ySize);
      setBackground (BACKGROUND_COLOR);

      this.nodeSize = nodeSize;
      nodes = new ArrayList<Node>();
      connectors = new ArrayList<Connector>();

      // Add a listener to make 'X' close the window.
      addWindowListener (new WindowAdapter() {
          public void windowClosing (WindowEvent event) {
              System.exit(0);
          }
      });
   }


   /**
    * Draws a single node, at the given position, and displays its id.
    * Possibly displays the size field if one is given.
    *
    * @param x x-position of center of node.
    * @param y y-position of center of node.
    * @param id the number to display inside the node.
    * @param size an additional size field, found in OSTree, to display at bottom of node.
    * @throws OSTreeDisplayException if a node with the given id already exists.
    */
   public void drawNode (int x, int y, int id, int size)
         throws OSTreeDisplayException {

      if (getNode (id) != null)
         throw new OSTreeDisplayException ("Duplicate node id: " + id + ".");

      nodes.add (new Node (x, y, id, size));
   }


   /**
    * Draws a single node, at the given position, and displays its id.
    *
    * @param x x-position of center of node.
    * @param y y-position of center of node.
    * @param id the number to display inside the node.
    * @throws OSTreeDisplayException if a node with the given id already exists.
    */
   public void drawNode (int x, int y, int id)
         throws OSTreeDisplayException {

      drawNode (x, y, id, SIZE_NONE);
   }


   /**
    * Draws a connecting line between two nodes.
    * The ids should be unique ids of nodes that have been drawn
    * using the drawNode() method.
    *
    * @param id1 id of first node to connect.
    * @param id2 id of second node to connect.
    * @throws OSTreeDisplayException if one of the ids does not exist.
    */
   public void connectNodes (int id1, int id2) throws OSTreeDisplayException {

      if (getNode (id1) == null)
         throw new OSTreeDisplayException ("Node: " + id1 + "does not exist.");
      if (getNode (id2) == null)
         throw new OSTreeDisplayException ("Node: " + id2 + "does not exist.");

      connectors.add (new Connector (id1, id2));
   }


   /**
    * Find the node with the given id and return it.
    *
    * @param id the id to search for.
    * @return the node with the given id, or null if it could not be found.
    */
   private Node getNode (int id) {

      Node foundNode = null;

      for (Node node: nodes)
         if (node.id == id)
            foundNode = node;

      return foundNode;
   }


   /**
    * Paints the current frame.
    * Overrides the paint() method of the parent classes.
    * NOTE: To draw the TreeDisplay window, this method should not
    * be called directly. Instead, call repaint(), which will call
    * this method indirectly.
    *
    * @param painter the graphics object used to draw within this frame.
    */
   public void paint (Graphics painter) {

      // Turn on anti-aliasing to make lines and circles look smoother.
      ((Graphics2D)painter).setRenderingHint (RenderingHints.KEY_ANTIALIASING,
                                              RenderingHints.VALUE_ANTIALIAS_ON);

      int fontSize = (int)(nodeSize * .6);
      int yOffset  = (int)(nodeSize * .2);
      int yOffset2 = (int)(nodeSize * 1.2);

      Font font = new Font ("sanserif", Font.PLAIN, fontSize);
      painter.setFont (font);

      painter.setColor (LINE_COLOR);
      for (Connector connect: connectors) {

         Node node1 = getNode (connect.id1);
         Node node2 = getNode (connect.id2);
         if (node1 == null || node2 == null)
            continue;

         painter.drawLine (node1.x, node1.y, node2.x, node2.y);
      }

      for (Node node: nodes) {

         int x = node.x - nodeSize / 2;
         int y = node.y - nodeSize / 2;

         int yStretch = (node.size == SIZE_NONE)? 1: 2;

         Color nodeColor = NODE_COLOR;

         // Start with a larger black circle, to form surrounding lines.
         painter.setColor (LINE_COLOR);
         painter.fillOval (x - LINE_WIDTH, y - LINE_WIDTH, nodeSize + LINE_WIDTH * 2,
                           yStretch * nodeSize + LINE_WIDTH * 2);

         // Draw smaller circle with node color over black circle.
         painter.setColor (nodeColor);
         painter.fillOval (x, y, nodeSize, yStretch * nodeSize);

         painter.setColor (TEXT_COLOR);

         // Draw line to separate between key and size fields, if size exists.
         if (node.size != SIZE_NONE)
            painter.drawLine (x, y + nodeSize, x + nodeSize, y + nodeSize);

         // Print node id - offset is computed based on number of digits.
         int nDigits = (node.id == 0)? 1: (int)Math.log10 (node.id) + 1;
         x = node.x - (int)(nodeSize * nDigits * .17);
         y = node.y + yOffset;
         painter.drawString ("" + node.id, x, y);

         // Check if drawing an OSTree - if so, print size field.
         if (node.size != SIZE_NONE) {
            nDigits = node.id == 0? 1: (int)Math.log10 (node.size) + 1;
            x = node.x - (int)(nodeSize * nDigits * .17);
            y = node.y + yOffset2;
            painter.drawString ("" + node.size, x, y);
         }
      }
   }

   /**
    * Opens a graphics window and displays the heap in tree format.
    *
    * @throws OSTreeDisplayException Thrown if the tree cannot be drawn.
    * @param tree the tree to draw
    */
   public static void draw (SearchTree tree) throws OSTreeDisplayException {

      OSTreeDisplay disp =
            new OSTreeDisplay (DEFAULT_X_SIZE, DEFAULT_Y_SIZE, DEFAULT_NODE_SIZE);

      disp.drawInternal (tree.getRoot(), (int)disp.getSize().getWidth() / 2, 0);

      disp.setVisible (true);
   }


   /**
    * Internal function for recursive draw().
    *
    * @param treeNode the tree node to draw.
    * @param xPos X-position of node to draw.
    * @param level length path from root to this node.
    * @throws OSTreeDisplayException Thrown if the tree cannot be drawn.
    */
   private void drawInternal (SearchTreeNode treeNode, int xPos, int level)
         throws OSTreeDisplayException {

      final int INITIAL_Y_POS = (int)(DEFAULT_Y_SIZE * .1);
      final int Y_STEP_SIZE   = (int)(DEFAULT_Y_SIZE * .2);

      int yPos = INITIAL_Y_POS + level * Y_STEP_SIZE;
      int xOffset = DEFAULT_X_SIZE / 4 / (int)Math.pow (2, level);

      try {

         if (treeNode instanceof OSTreeNode)
            drawNode (xPos, yPos, treeNode.getKey(),
                      ((OSTreeNode)treeNode).getSize());
         else
            drawNode (xPos, yPos, treeNode.getKey());

         if (treeNode.getLeft() != null) {
            drawInternal (treeNode.getLeft(), xPos - xOffset, level + 1);
            connectNodes (treeNode.getKey(), treeNode.getLeft().getKey());
         }

         if (treeNode.getRight() != null) {
            drawInternal (treeNode.getRight(), xPos + xOffset, level + 1);
            connectNodes (treeNode.getKey(), treeNode.getRight().getKey());
         }
      } catch (OSTreeDisplayException e) {
         throw new OSTreeDisplayException ("Tree contains duplicate keys, cannot display.");
      }
   }


   /**
    * Main method - tests the TreeDisplay class by drawing a simple tree.
    *
    * @param args not used.
    */
   public static void main (String[] args) {

      OSTreeDisplay tree = new OSTreeDisplay ();

      try {
         tree.drawNode (300, 60, 12);
         tree.drawNode (166, 180, 8);
         tree.drawNode (432, 180, 9);
         tree.drawNode (60,  300, 6);
         tree.drawNode (220, 300, 5);
         tree.drawNode (380, 300, 2);

         tree.connectNodes (12, 8);
         tree.connectNodes (12, 9);
         tree.connectNodes (8, 6);
         tree.connectNodes (8, 5);
         tree.connectNodes (9, 2);

      } catch (OSTreeDisplayException e) {
         System.out.println (e.getMessage());
      }

      tree.setVisible (true);
   }
}
