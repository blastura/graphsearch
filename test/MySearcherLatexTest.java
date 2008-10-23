import java.io.File;
import junit.framework.*;

public class MySearcherLatexTest extends TestCase {
   String[] from = {"Tegsbron" , "Flygplatsen" , "MIT"         , "Nydala" , "Teg"};
   String[] to   = {"Nydala"   , "Begbilar"    , "Flygplatsen" , "Gamlia" , "Foa"};

   public MySearcherLatexTest(String name) {
      super(name);
   }

   public void testBreadthFirst() {
      System.out.println("\\subsection{Test Bredden-först}");
      for (int i = 0; i < from.length; i++) {
         MySearcher my = new MySearcher();
         my.setMap(new File("maps/umea_map.xml"));
         System.out.println("\\subsubsection{From: " + from[i] + " to " + to[i] + "}");
         System.out.println(my.breadthFirst(from[i], to[i]));
      }
   }

   public void testDepthFirst() {
      System.out.println("\\subsection{Test Djupet-först}");
      for (int i = 0; i < from.length; i++) {
         MySearcher my = new MySearcher();
         my.setMap(new File("maps/umea_map.xml"));
         System.out.println("\\subsubsection{From: " + from[i] + " to " + to[i] + "}");
         System.out.println(my.depthFirst(from[i], to[i]));
      }
   }

   public void testAStar() {
      System.out.println("\\subsection{Test A* snabbast}");
      for (int i = 0; i < from.length; i++) {
         MySearcher my = new MySearcher();
         my.setMap(new File("maps/umea_map.xml"));
         System.out.println("\\subsubsection{From: " + from[i] + " to " + to[i] + "}");
         System.out.println(my.aStar(from[i], to[i], true));
      }

      System.out.println("\\subsection{Test A* kortast}");
      for (int i = 0; i < from.length; i++) {
         MySearcher my = new MySearcher();
         my.setMap(new File("maps/umea_map.xml"));
         System.out.println("\\subsubsection{From: " + from[i] + " to " + to[i] + "}");
         System.out.println(my.aStar(from[i], to[i], false));
      }
   }

   public void testGreedy() {
      System.out.println("\\subsection{Test Girig sökning}");
      for (int i = 0; i < from.length; i++) {
         MySearcher my = new MySearcher();
         my.setMap(new File("maps/umea_map.xml"));
         System.out.println("\\subsubsection{From: " + from[i] + " to " + to[i] + "}");
         System.out.println(my.greedySearch(from[i], to[i]));
      }
   }
}