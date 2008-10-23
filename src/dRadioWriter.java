import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import java.awt.event.KeyEvent;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Container;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.util.logging.Logger;

public class dRadioWriter extends JPanel implements ActionListener {
   private String selected;

   public dRadioWriter() {

      // RadioButtons
      // FIX, put em in a Vector
      JRadioButton jrbBreadth = new JRadioButton("Breadth-first search");
      jrbBreadth.setMnemonic(KeyEvent.VK_B);
      jrbBreadth.setActionCommand("breadth");
      jrbBreadth.setSelected(true);
      this.selected = "breadth";

      JRadioButton jrbDepth = new JRadioButton("Depth-first search");
      jrbDepth.setMnemonic(KeyEvent.VK_D);
      jrbDepth.setActionCommand("depth");

      JRadioButton jrbGreedy = new JRadioButton("Greedy search");
      jrbGreedy.setMnemonic(KeyEvent.VK_G);
      jrbGreedy.setActionCommand("greedy");

      JRadioButton jrbAfast = new JRadioButton("A* fastest");
      jrbAfast.setMnemonic(KeyEvent.VK_A);
      jrbAfast.setActionCommand("aStarFast");

      JRadioButton jrbAshort = new JRadioButton("A* shortest");
      jrbAshort.setMnemonic(KeyEvent.VK_S);
      jrbAshort.setActionCommand("aStarShort");

      // Group the radio buttons.
      ButtonGroup group = new ButtonGroup();
      group.add(jrbBreadth);
      group.add(jrbDepth);
      group.add(jrbGreedy);
      group.add(jrbAfast);
      group.add(jrbAshort);

      // Register an action listener for the radio buttons.
      jrbBreadth.addActionListener(this);
      jrbDepth.addActionListener(this);
      jrbGreedy.addActionListener(this);
      jrbAfast.addActionListener(this);
      jrbAshort.addActionListener(this);

      // Put the radio buttons in a column in a panel
      this.setLayout(new GridLayout(0, 1));
      this.add(jrbBreadth);
      this.add(jrbDepth);
      this.add(jrbGreedy);
      this.add(jrbAshort);
      this.add(jrbAfast);

      TitledBorder title =
         BorderFactory.createTitledBorder("Algoritm");
      this.setBorder(title);
   }

   public String getSelection() {
      return this.selected;
   }

   public void actionPerformed(ActionEvent e) {
      this.selected = e.getActionCommand();
      Logger.global.info("@ => actionPerformed, action = " + selected);
   }
}