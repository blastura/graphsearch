
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JComponent;

/**
 * @author anton.johansson@gmail.com
 */
public class dGraphWriter extends JComponent {
   int width;
   int height;
   int fringe;

   double nodeDiamter = 20;
   Graph map;
   File mapFile;
   int maxNodes;
   int xMax = Integer.MIN_VALUE;
   int yMax = Integer.MIN_VALUE;
   int xMin = Integer.MAX_VALUE;
   int yMin = Integer.MAX_VALUE;
   double scaleFactor;
   Collection<GraphNode> nodes;
   Graphics2D g2;
   Vector<Ellipse2D.Double> visitedNodes;
   Vector<Arrow2D> parentArrows;
   MySearcher my;

   String algo;
   String from;
   String to;

   public dGraphWriter(File mapFile, int width, int height, int fringe) {
      this.mapFile = mapFile;
      this.width =  width - (fringe*2);
      this.height = height - (fringe*2);
      this.fringe = fringe;

      this.my = new MySearcher();
      this.my.setMap(mapFile);
      this.map = my.getMap();

      this.maxNodes = map.size();
      this.visitedNodes = new Vector<Ellipse2D.Double>();
      this.parentArrows = new Vector<Arrow2D>();
      this.nodes = map.getNodes();
      this.setNodes();
   }

   public void step() {
      my.step();
   }

   public void setAlgorithm(String algo) {
      this.algo = algo;
   }

   public void restartSearch() {
      startSearch(algo, from, to);
   }

   public boolean isRunning() {
      return my.isRunning();
   }

   public void startSearch(final String algo, final String from,
                           final String to) {
      Logger.global.info("@ => startSearch(), Entering");
      this.algo = algo;
      this.from = from;
      this.to = to;

      new Thread(new Runnable() {
            public void run() {
               my.setGraphics(dGraphWriter.this);
               if (algo.equals("breadth")) {
                  my.breadthFirst(from, to);
               }
               else if (algo.equals("depth")) {
                  my.depthFirst(from, to);
               }
               else if (algo.equals("greedy")) {
                  my.greedySearch(from, to);
               }
               else if (algo.equals("aStarFast")) {
                  my.aStar(from, to, true);
               }
               else if (algo.equals("aStarShort")) {
                  my.aStar(from, to, false);
               }
            }
         }).start();
   }

   public void drawArrow(double x1, double y1, double x2, double y2) {
      g2.draw(new Line2D.Double(x1, y1, x2, y2));
      double angle = calculateAngle(x1, y1, x2, y2);

      double arcWidth = 45.0;
      double arcWidthHeight = 15.0;
      double initialAngle = 180-45.0/2.0;
      Arc2D.Double arc = new Arc2D.Double();
      arc.setArcByCenter(x2, y2, arcWidthHeight, initialAngle,
                         arcWidth, Arc2D.PIE);

      AffineTransform tx = AffineTransform.getRotateInstance(angle, x2, y2);
      Shape trArc = tx.createTransformedShape((Arc2D.Double) arc);
      g2.fill(trArc);
   }

   /**
    * Caculate angle between start point and endpoint.
    *
    * @param x1 x coordinate of start-point
    * @param y1 y coordinate of start-point
    * @param x2 x coordinate of end-point
    * @param y2 y coordinate of end point
    * @return angle form the poisitive/right horisontal axis to end
    * point.
    */
   public static double calculateAngle(double x1, double y1,
                                       double x2, double y2) {
      double dx = x2 - x1;
      double dy = y2 - y1;
      double angle = 0.0; // Horisontal to the right

      if (dx == 0.0) { // Vertical
         if (dy == 0.0) { // No angle
            angle = 0.0;
         }
         else if (dy > 0.0) { // Points vertical up
            angle = Math.PI/2.0;
         }
         else {// dy < 0, Points down
            angle = Math.PI/2.0*3.0;
         }
      }
      else if (dy == 0.0) { // Horisontal
         if (dx > 0.0) { // Points right
            angle = 0.0;
         }
         else { // dx < 0.0 Points left
            angle = Math.PI;
         }
      }
      else {
         if (dx < 0.0) { // Points (up and left) or (down and left)
            angle = Math.atan(dy/dx) + Math.PI;
         }
         else if (dy < 0.0) { // Points down and right
            angle = Math.atan(dy/dx) + 2 * Math.PI;
         }
         else {
            angle = Math.atan(dy/dx);
         }
      }
      return angle;
   }

   public void setNodes() {
      Logger.global.info("@ => setNode, fringe = " + fringe);
      Logger.global.info("@ => setNode, width = " + width);
      Logger.global.info("@ => setNode, height = " + height);

      // Check for xMax and yMax
      for (GraphNode node : nodes) {
         xMax = (node.getX() > xMax ? node.getX() : xMax);
         yMax = (node.getY() > yMax ? node.getY() : yMax);
         xMin = (node.getX() < xMin ? node.getX() : xMin);
         yMin = (node.getY() < yMin ? node.getY() : yMin);
      }
      Logger.global.info("xMin = " + xMin);
      Logger.global.info("xMax = " + xMax);
   }

   public double getXPos(double x) {
      return (double) (x - xMin)
         / (double)(xMax - xMin)
         * width + fringe;
   }

   public double getYPos(double y) {
      return (double) (y - yMax)
         / (double)(xMin - xMax) //flip map (xMax - xMin)
         * height + fringe;
   }

   /**
    * Paint draws the Graph.
    * @param g - the graphics pen that does the drawing
    */
   public void paint(Graphics g) {
      Logger.global.info("@ => paint in dGraphWriter");
      this.g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
      //       AffineTransform transformer =
      //          AffineTransform.getTranslateInstance((double) -xMin, (double) -yMax);
      //       AffineTransform scaleTransformer =
      //          AffineTransform.getScaleInstance(1.0/(double)(xMax - xMin) * width + fringe,
      //                                           1.0/(double)(xMin - yMax) * height + fringe);
      //       transformer.concatenate(scaleTransformer);
      //       Logger.global.info(transformer);
      g2.setColor(Color.white);
      g2.fillRect(fringe, fringe, width, (int) Math.round(getYPos(yMin)) - fringe);

      for (GraphNode node : nodes) {
         double posX = getXPos(node.getX());
         double posY = getYPos(node.getY());
         for (Enumeration<GraphNode> e = node.getNeighbours();
              e.hasMoreElements();) {
            GraphNode neighbour = e.nextElement();
            g2.setColor(Color.black);
            drawArrow(posX, posY,
                      getXPos(neighbour.getX()),
                      getYPos(neighbour.getY()));
         }
         g2.setColor(Color.green); // Node color

         posX -= nodeDiamter/2;
         posY -= nodeDiamter/2;
         g2.fill(new Ellipse2D.Double(posX, posY, nodeDiamter, nodeDiamter));

         g2.setColor(Color.black); // Label color
         g2.drawString(node.getName(), (int) posX, (int) posY);// + " at (" + (int) posX + ", " + (int) posY + ")"
      }
      update(g2);
   }

   public void visitNode(int x, int y) {
      Logger.global.info("@ => visitNode in dGraphWriter");
      double posX = getXPos(x);
      double posY = getYPos(y);
      posX -= nodeDiamter/2;
      posY -= nodeDiamter/2;
      visitedNodes.add(new Ellipse2D.Double(posX, posY,
                                            nodeDiamter, nodeDiamter));
      repaint();
   }
   
   public void addParentArrow(int x1, int y1, int x2, int y2) {
      //parentArrows.add(new Arrow2D(x1, y1, x1, y2));
   }

   public void update(Graphics g) {
      Logger.global.info("@ => update in dGraphWriter");
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(Color.red);
      for (Ellipse2D.Double ellipse : visitedNodes) {
         g2.fill(ellipse);
      }
   }

   public void terminateSearch() {
      Logger.global.info("@ => terminateSearch");
      this.visitedNodes.removeAllElements();
      //this.nodes.clear();
      // FIX restart of excecution
      my.step(); // Release algo from Thread.sleep
      my.kill();
      my.reset();
      paint(this.getGraphics());
   }
}