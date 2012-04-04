package petrinet.gui;

import java.awt.*;
import javax.swing.*;

public class CirclePanel extends JPanel
{
   private Circle circle;

   public CirclePanel(Color backColor, Color circleColor, int width, int height){
      setBackground(backColor);
      setPreferredSize(new Dimension(width, height));
      circle = new Circle(width / 2, height / 2, 50, circleColor);
      circle.setFilled(true);
   }

   public void paintComponent(Graphics g){
      super.paintComponent(g);
      circle.draw(g);
   }
}
 
