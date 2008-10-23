import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Graph is a representing the complex datastructure graph, which
 * stores nodes and connections between them.
 *
 * @author "Anton Johansson" <anton.johansson@gmail.com>
 * @author "Victor Zamanian" <victor.zamanian@gmail.com>
 * @version 1.0
 */
public class Graph {
   private Hashtable<Object, GraphNode> nodes;
   private int initialCapacityNeighbours;
   private int numberOfEdges = 0;

   /**
    * Creates a new instance of a Graph.
    *
    * @param initialSize The number of nodes that need
    * to have memory allocated for them.
    * @param initialCapacityNeighbours The maximum number of neighbours.
    * (The number of nodes in the network.)
    */
   public Graph(int initialSize, int initialCapacityNeighbours) {
      this.initialCapacityNeighbours = initialCapacityNeighbours;
      nodes = new Hashtable(initialSize);
   }

   /**
    * Inserts a node with name address name into the graph.
    *
    * @param name The name address of the node to insert.
    * @param x the x-coordante of the node.
    * @param y the y-coordante of the node.
    * @return the newly created GraphNode
    */
   public GraphNode insertNode(String name, int x, int y) {
      GraphNode node = new GraphNode(name, x, y, initialCapacityNeighbours);
      nodes.put(name, node);
      return node;
   }

   /**
    * Insers an edge between two nodes in the graph.
    * It is assumed that the parameter nodes exist in the graph.
    *
    * @param srcName The source node.
    * @param destName The destination node.
    * @param edge The edge between the nodes.
    * @return true if an edge is inserted.
    */
   public Boolean insertEdge(String srcName, String destName, Object edge) {
      //Om det inte redan finns en kant
      GraphNode srcNode = nodes.get(srcName);
      GraphNode destNode = nodes.get(destName);

      if (srcNode != null && destNode != null) {
         srcNode.addNeighbour(destNode, edge);
         //destNode.addNeighbour(srcNode, weight);
         numberOfEdges++;
         return true;
      }
      else {
         return false;
      }
   }

   /**
    * Inspects the graph to see if it is empty of nodes.
    * @return true if the graph contains no nodes, else false.
    */
   public boolean isEmpty() {
      return nodes.isEmpty();
   }

   /**
    * Clears the Graph from all nodes.
    */
   public void clear() {
      nodes.clear();
   }

   /**
    * Clean the Graph from all set variables, keep all nodes and egdes
    * unchanged.
    */
   public void cleanGraph() {
      for (GraphNode node : nodes.values()) {
         node.clean();
      }
   }

   /**
    * Inspects if the graph has no edges.
    * @return true if the graph contains no edges, else false.
    */
   public boolean hasNoEdges() {
      return (numberOfEdges == 0);
   }

   /**
    * The set of nodes which are neighbours
    * of the node with name address given by parameter.
    *
    * @param name The name address of the node.
    * @return An Enumeration with the set of neighbours of the node.
    */
   public Enumeration neighbours(String name) {
      return nodes.get(name).getNeighbours();
   }

   /**
    * Inspects the number of nodes in this Graph.
    *
    * @return The number of nodes in this Graph.
    */
   public int size() {
      return nodes.size();
   }

   /**
    * Get all nodes in the graph.
    *
    * @return An collection of all nodes in the graph.
    */
   public Collection getNodes() {
      return nodes.values();
   }

   /**
    * Inspects the weight of an edge between two nodes in the grapn.
    *
    * @param srcName The name address of the source node.
    * @param destName The name address of the destination node.
    * @return The weight of the edge.
    */
   public Object getEdge(String srcName, String destName) {
      GraphNode srcNode = nodes.get(srcName);
      GraphNode destNode = nodes.get(destName);
      return srcNode.getEdge(destNode);
   }

   // Modifikatorer

   /**
    * Removes a node from the graph.
    *
    * @param node The node to be removed.
    */
   public void deleteNode(GraphNode node) {
      for (Enumeration e = node.getNeighbours(); e.hasMoreElements();) {
         ((GraphNode) e.nextElement()).deleteNeighbour(node);
      }
      nodes.remove(node.getName());
   }

   /**
    * Removes an edge between two nodes.
    *
    * @param src The source node.
    * @param dest The destination node.
    */
   public void deleteEdge(GraphNode src, GraphNode dest) {
      src.deleteNeighbour(dest);
      dest.deleteNeighbour(src);

      numberOfEdges--;
   }

   /**
    * Alters the weight of an edge between two nodes in the graph.
    *
    * @param srcNode The source node.
    * @param destNode The destination node.
    * @param weight The new weight between the two nodes.
    */
   public void setWeight(GraphNode srcNode, GraphNode destNode, Double weight) {
      deleteEdge(srcNode, destNode);
      insertEdge(srcNode.getName(), destNode.getName(), weight);
   }

   /**
    * Inspects whether a node with a certain name address
    * given by parameter exists in the graph or not.
    *
    * @param name The name address of the node.
    * @return true if the node exists, else false.
    */
   public boolean isInGraph(String name) {
      return (nodes.containsKey(name));
   }

   /**
    * Returns the node with the specified name, if it exists in this
    * Graph.
    *
    * @param name The name of the node to fetch.
    * @return the node with the specified name.
    */
   public GraphNode getNode(String name) {
      return nodes.get(name);
   }

   /**
    * Returns a String representation of this Graph.
    *
    * @return the String representation of this Graph.
    */
   public String toString() {
      String returnString = "";
      for (Enumeration e = nodes.elements(); e.hasMoreElements();) {
         returnString += e.nextElement();
      }
      return returnString;
   }
}
