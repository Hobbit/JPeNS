package petrinet.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.util.Random;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.util.Random;

public class CirclePanel extends JPanel
{
   private Circle circle;
   private javax.swing.Timer timer;

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
 
