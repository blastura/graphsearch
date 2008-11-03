import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.Graphics2D;

public class Arrow2D {
   private Shape line;
   private Shape arc;
   
   public Arrow2D(double x1, double y1, double x2, double y2) {
      this(x1, y1, x2, y2, 0);
   }
   
   public Arrow2D(double x1, double y1, double x2, double y2, double arrowOffsetFromEnd) {
      double arcWidth = 60.0;
      double arcWidthHeight = 10.0;
      double length = Math.hypot(x1-x2, y1-y2);
      
      double angle = calculateAngle(x1, y1, x2, y2);
      
      // Line pointing right
      Line2D tmpLine = new Line2D.Double(x1 + arrowOffsetFromEnd, y1, x1 + length - arrowOffsetFromEnd, y1);
      
      // Arrowhead pointing right
      double initialAngle = 180-arcWidth/2.0;
      
      Arc2D.Double tmpArc = new Arc2D.Double();
      tmpArc.setArcByCenter(x1 + length - arrowOffsetFromEnd, y1, arcWidthHeight,
                            initialAngle, arcWidth, Arc2D.PIE);

      AffineTransform tx = AffineTransform.getRotateInstance(angle, x1, y1);
      this.arc = tx.createTransformedShape(tmpArc);
      this.line = tx.createTransformedShape(tmpLine);
   }
   
   // Implementing Shape with PathIterator seems tough.
   public void draw(Graphics2D g2) {
      g2.draw(line);
      g2.fill(arc);
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
   private static double calculateAngle(double x1, double y1,
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
}

//    // Returns the starting Point2D of this Line2D.
//    public Point2D getP1() {
//       return new Point2D.Double(x1, y1);
//    }

//    // Returns the end Point2D of this Line2D.
//    public Point2D getP2() {}

//    // Returns the X coordinate of the start point in double precision.
//    public double getX1() {}

//    // Returns the X coordinate of the end point in double precision.
//    public double getX2() {}

//    // Returns the Y coordinate of the start point in double precision.
//    public double getY1() {}

//    // Returns the Y coordinate of the end point in double precision.
//    public double getY2() {}
//}