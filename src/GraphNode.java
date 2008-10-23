import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * GraphNode, used by Graph to keep track of the nodes
 * in a network and to make a routing table.
 *
 * @author "Anton Johansson" <anton.johansson@gmail.com>
 * @author "Victor Zamanian" <victor.zamanian@gmail.com>
 * @version 1.0
 */
public class GraphNode implements Comparable {
   private int maxNeighbours;
   private Hashtable neighbours;
   private String name;
   private int x;
   private int y;
   private Double distanceToGoal;
   private boolean visited;
   private Double distanceTraveled;
   private GraphNode parent;
   private Double evalFuncVal;

   /**
    * Creates a new GraphNode instance.
    *
    * @param name The name of the node.
    * @param x the x-coordinate of this node.
    * @param y the y-coordinate of this node.
    * @param maxNeighbours The maximum amount of
    * neighbours the node can have.
    */
   public GraphNode(String name, int x, int y, int maxNeighbours) {
      this.name = name;
      this.x = x;
      this.y = y;
      this.visited = false;
      this.maxNeighbours = maxNeighbours;
      this.evalFuncVal = Double.MIN_VALUE;
      neighbours = new Hashtable(maxNeighbours);
   }

   /**
    * Clean this node from all variables set when used in search
    * algoritms.
    */
   public void clean() {
      this.distanceToGoal = null;
      this.distanceTraveled = null;
      this.evalFuncVal = null;
      this.parent = null;
      this.visited = false;
   }

   /**
    * Adds a neighbour to this node.
    *
    * @param node The neighbour node to be added.
    * @param edge The edge between this node and the new neighbour.
    */
   public void addNeighbour(GraphNode node, Object edge) {
      neighbours.put(node, edge);
   }

   /**
    * Removes the specified neighbour of this node.
    *
    * @param node The neighbour to remove.
    */
   public void deleteNeighbour(GraphNode node) {
      neighbours.remove(node);
   }

   /**
    * Inspects the weight of the potential edge between this
    * node and one of its neighbours.
    *
    * @param node The neighbour of this node.
    * @return The weight of the potential edge.
    */
   public Object getEdge(GraphNode node) {
      return neighbours.get(node);
   }

   /**
    * Visists this node--sets its visited attribute to true.
    */
   public void visit() {
      this.visited = true;
   }

   /**
    * Inspects whether this node has been visited or not.
    *
    * @return true if this node has been visited, else false.
    */
   public boolean isVisited() {
      return this.visited;
   }

   /**
    * Inspects all the neighbours (nodes) of this node.
    *
    * @return An Enumeration of all neighbours of this node..
    */
   public Enumeration getNeighbours() {
      return neighbours.keys();
   }

   /**
    * Inspects the name of this node.
    *
    * @return The name of this node.
    */
   public String getName() {
      return this.name;
   }

   /**
    * Sets the distance to this node from the root node. This is
    * usefull in algorithms.
    *
    * @param distanceTraveled The distanceTraveled by this node.
    */
   public void setDistanceTraveled(Double distanceTraveled) {
      this.distanceTraveled = distanceTraveled;
   }

   /**
    * Inspects the distance from this node to the root node. See "setDistance."
    *
    * @return The distance from this node to the root node.
    */
   public Double getDistanceTraveled() {
      return this.distanceTraveled;
   }

   /**
    * Sets this node's parent node. Used in algorithms.
    *
    * @param parent The parent to be set.
    */
   public void setParent(GraphNode parent) {
      this.parent = parent;
   }

   /**
    * Inspects this node's parent.
    *
    * @return The parent node of this parent.
    */
   public GraphNode getParent() {
      return this.parent;
   }

   /**
    * Access to this nodes x-coordinate.
    *
    * @return the x-coordinate of this node.
    */
   public int getX() {
      return this.x;
   }

   /**
    * Access to this nodes y-coordinate.
    *
    * @return the y-coordinate of this node.
    */
   public int getY() {
      return this.y;
   }

   /**
    * Access to this nodes x-coordinate.
    * @param the new y-coordinate.
    */
   public void setX(int x) {
      this.x = x;
   }

   /**
    * Access to this nodes y-coordinate.
    * @param the new y-coordinate.
    */
   public void setY(int y) {
      this.y = y;
   }

   /**
    * Calculate and return the distance to x- y-coordinates
    *
    * @param goal the Node to calculate distance to.
    */
   public void setDistanceToGoal(GraphNode goal) {
      this.distanceToGoal = Math.hypot((this.x - goal.getX()),
                                       (this.y - goal.getY()));
   }

   /**
    * Retun the distance to Goal.
    *
    * @return the distance to goal.
    */
   public Double getDistanceToGoal() {
      return this.distanceToGoal;
   }

   /**
    * Sets the value evalFuncVal, this is used in search algorithms.
    *
    * @param evalFuncVal the evalFuncVal value.
    */
   public void setEvalFuncVal(Double evalFuncVal) {
      this.evalFuncVal = evalFuncVal;
   }

   /**
    * Returns the value evalFuncVal, this is used in search
    * algorithms.
    *
    * @return The evalFuncVal value.
    */
   public Double getEvalFuncVal() {
      return this.evalFuncVal;
   }

   /**
    * Compares this node to another graph node. The comparison uses
    * the nodes evalFuncVal values.
    *
    * @param node The node to compare this node to.
    * @return 0 if the addresses match, a positive integer if
    * this node has a higher NAME address than the other node,
    * and a negative integer if this node has a lower NAME address
    * than the other node.
    */
   public int compareTo(Object node) {
      return this.evalFuncVal.compareTo(((GraphNode) node).getEvalFuncVal());
   }

   /**
    * Returns a String representation of this GraphNode.
    *
    * @return a a String representation of this GraphNode.
    */
   public String toString() {
      String returnString = name + "\n";
      for (Enumeration e = getNeighbours(); e.hasMoreElements();) {
         GraphNode neighbour = (GraphNode) e.nextElement();
         returnString += "      " + neighbour.getName()
            + ", traveltime: " + this.getEdge(neighbour) +"\n";
      }
      return returnString;
   }
}
