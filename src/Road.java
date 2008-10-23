/**
 * This class is used to store as edges in a graph.
 *
 * @author Anton Johansson <anton.johansson@gmail.com>
 * @version 1.0
 */
public class Road {
   private int speed;
   private double distance;
   private double travelTime;

   /**
    * Creates a new Road instance.
    *
    * @param speed The speed of this Road.
    * @param distance The distance of this Road.
    */
   public Road(int speed, double distance) {
      this.speed = speed;
      this.distance = distance;
      this.travelTime = distance / (speed / 3.6);
   }

   /**
    * Gets the value of speed
    *
    * @return the value of speed
    */
   public final int getSpeed() {
      return this.speed;
   }

   /**
    * Gets the value of distance
    *
    * @return the value of distance
    */
   public final double getDistance() {
      return this.distance;
   }

   /**
    * Gets the value of travelTime
    *
    * @return the value of travelTime
    */
   public final double getTravelTime() {
      return this.travelTime;
   }

   /**
    * Returns a String-representation of this Road. The String value
    * of travelTime is returned.
    *
    * @return The String value of this Road.
    */
   public String toString() {
      return new Double(this.travelTime).toString();
   }
}