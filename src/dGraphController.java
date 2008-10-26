import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * TODO, fix this description. 
 *
 * @author anton.johansson@gmail.com
 * @version 1.0
 */
public class dGraphController {
   private dGraphWriter p;
   private dRadioWriter radioWriter;


   /**
    * Creates a new dGraphController instance.
    *
    */
   public dGraphController() {
      try {
         // Make look and feel, system specific
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
      catch(Exception e) {
         e.printStackTrace();
      }

      //GraphWriter
      int fringe = 40;
      int windowSize = 600;
      p = new dGraphWriter(new File("maps/umea_map.xml"), windowSize, windowSize, fringe);
      p.setSize(windowSize, windowSize+fringe);
      // Mouseclick, Continue algorithm
      p.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
               if (!p.isRunning()) {
                  Logger.global.info("Algoritm is not running");
                  p.terminateSearch();
                  p.setAlgorithm(radioWriter.getSelection());
                  p.restartSearch();
               }
               else {
                  Logger.global.info("@ => mousePressed at ("
                                     + e.getX() + ", "
                                     + e.getY()
                                     + ")------------------------------");
                  p.step();
               }
            }
         });

      // Panel
      JPanel graphPanel = new JPanel(new BorderLayout());
      graphPanel.add(p, BorderLayout.CENTER);

      // Radio buttons
      radioWriter = new dRadioWriter();
      graphPanel.add(radioWriter, BorderLayout.EAST);
      graphPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

      Logger.global.info("algorithm = " + radioWriter.getSelection());

      // Cancel button
      JButton btClear = new JButton("Clear");
      btClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               p.terminateSearch();
               // p.startSearch("breadth", "On", "Begbilar");
            }
         });
      graphPanel.add(btClear, BorderLayout.SOUTH);
      
      // Frame
      JFrame graphFrame = new JFrame("GraphSearcher");
      graphFrame.getContentPane().add(graphPanel);
      graphFrame.setSize(windowSize + 2*fringe + 200,
                         windowSize + 2*fringe + 50);
      graphFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //graphFrame.pack(); // Finalize the layout
      graphFrame.setVisible(true);

      // FIX hardcoded source dest node
      p.startSearch(radioWriter.getSelection(),
                    "Tegsbron", "Nydala");
   }

   /**
    * Starts the UI.
    *
    * @param args no parameter is used.
    */
   public static void main(String[] args) {
      Logger.global.setLevel(Level.ALL);
      new dGraphController();
   }
}
