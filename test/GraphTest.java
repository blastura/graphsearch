import junit.framework.*;

/**
 * GraphTest
 *
 * @author "Anton Johansson" <anton.johansson@gmail.com>
 */
public class GraphTest extends TestCase {
   public GraphTest(String name) {
      super(name);
   }

   public void testEmpty() {
      Graph g = new Graph(11, 24);
      assertTrue(g.isEmpty());
      g.insertNode("lund", 0, 0);
      assertFalse(g.isEmpty());
      g.insertNode("malmö", 3, 4);
      g.deleteNode(g.getNode("lund"));
      assertFalse(g.isEmpty());
      g.deleteNode(g.getNode("malmö"));
      assertTrue(g.isEmpty());
   }


   public void testEdges() {
      Graph g = new Graph(11, 24);
      g.insertNode("lund", 1, 2);
      g.insertNode("malmö", 1, 2);
      g.insertNode("eslöv", 1, 2);
      g.insertNode("umeå", 1, 2);
      g.insertNode("stockholm", 2, 4);
      g.insertEdge("lund", "malmö", new Double(100));
      g.insertEdge("lund", "eslöv", new Double(200));
      g.insertEdge("eslöv", "umeå", new Double(300));
      g.insertEdge("lund", "stockholm", new Double(400));
      assertEquals("malmö", g.getNode("malmö").getName());
      assertEquals("umeå", g.getNode("umeå").getName());
      assertFalse("malmö" != g.getNode("malmö").getName());
      assertEquals(new Double(100.0), g.getEdge("lund", "malmö"));
      assertEquals(new Double(200.0), g.getEdge("lund", "eslöv"));
      assertEquals(new Double(300.0), g.getEdge("eslöv", "umeå"));
      //assertEquals(300.0, g.getEdge("umeå", "eslöv"));
      assertEquals(new Double(400.0), g.getEdge("lund", "stockholm"));
   }

   public void testSetGetDistance() {
      Graph g = new Graph(11, 24);
      GraphNode lund = g.insertNode("lund", 0, 0);
      GraphNode malmo = g.insertNode("malmö", 0, 2);
      GraphNode eslov = g.insertNode("eslöv", 5, 0);
      GraphNode umea = g.insertNode("umeå", 4, 7);
      GraphNode kiruna = g.insertNode("kiruna", 9, 15);

      assertEquals(0, lund.getX());
      assertEquals(0, lund.getY());

      assertEquals(0, malmo.getX());
      assertEquals(2, malmo.getY());

      lund.setDistanceToGoal(malmo);
      assertEquals(2.0, lund.getDistanceToGoal());

      lund.setDistanceToGoal(eslov);
      assertEquals(5.0, lund.getDistanceToGoal());

      lund.setDistanceToGoal(umea);
      // (sqrt (+ (expt 4 2) (expt 7 2)))
      assertEquals(8.06225774829855, lund.getDistanceToGoal(), 0.0000000001);

      umea.setDistanceToGoal(kiruna);
      // (sqrt (+ (expt (- 4 9) 2) (expt (- 7 15) 2)))
      assertEquals(9.433981132056603, umea.getDistanceToGoal(), 0.0000000001);
   }
}