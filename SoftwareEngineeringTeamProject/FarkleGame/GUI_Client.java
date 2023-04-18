package FarkleGame;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class GUI_Client {    // Your class name

   public static void main(String[] args) {
      
      JFrame f = new JFrame("Client");
      f.setSize(250, 250);
      f.setLocation(300,200);
      f.getContentPane().add(BorderLayout.CENTER, new JTextArea(10, 40));
      f.setVisible(true);
      
    }
}