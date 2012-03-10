package petrinet.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import petrinet.logic.Petrinet;
import petrinet.logic.Place;
import petrinet.logic.Transition;

public class PetrinetGUI
extends JFrame {

    public class PlaceLabel
    extends JLabel {

        private Place place;

        public PlaceLabel(Place p) {
            super(p.toString());
            this.place = p;
        }

        @Override
        public String getText() {
            if (place == null) {
                return null;
            }
            return place.toString();
        }

    }


    public class TransitionButton extends JButton {

        private Transition transition;

        public TransitionButton(final Transition t) {
            super(t.getName());
            this.transition = t;
            
            this.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (transition.canFire()) {
                        transition.fire();
                        System.out.println(pn);
                        fireStateChanged();
                    }
                    if(transition.canFire()){
                		setBackground(Color.GREEN);
                	}else if(transition.isNotConnected()){
                		setBackground(Color.GRAY);
                	}else{
                		setBackground(Color.ORANGE);
                	}
                    repaint();
                }});

        }

        public boolean isEnabled() {
            if (transition == null) {
                 return false;
            }
            return transition.canFire();
        }

        public String getText() {
            if (null==transition) {
                return null;
            }
            return transition.toString();
        }

    }


    Petrinet pn;

 /*   public PetrinetGUI(Petrinet pn) {
        super(pn.getName());
        this.pn = pn;
//        this.setLayout(new FlowLayout());
        this.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        for (Transition t : pn.getTransitions()) {
            TransitionButton button = new TransitionButton(t);
            add(button);
            button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    repaint();
                }});
        }
        for (Place p : pn.getPlaces()) {
            add(new PlaceLabel(p));
        }
    }*/
    
    public PetrinetGUI(Petrinet pn) {
        super(pn.getName());
        this.pn = pn;
        List<Transition> t = pn.getTransitions();
        List<Place> p = pn.getPlaces();
        Container contentPane = getContentPane();
        JPanel transPan = new JPanel(); 
        JPanel places = new JPanel(new GridBagLayout());;
        GridBagConstraints c = new GridBagConstraints();
        places.setBorder(BorderFactory.createLineBorder(Color.black));

       // this.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        
        final TransitionButton[] buttons = new TransitionButton[t.size()]; 
        PlaceLabel[] labels = new PlaceLabel[p.size()];
        for (int i = 0; i < t.size(); i++) {
        	buttons[i] = new TransitionButton(t.get(i));
        	transPan.add(buttons[i]);
        	Transition tTemp = t.get(i);
        	TransitionButton bTemp = buttons[i];
        	if(tTemp.canFire()){
        		bTemp.setBackground(Color.GREEN);
        	}else if(tTemp.isNotConnected()){
        		bTemp.setBackground(Color.GRAY);
        	}else{
        		bTemp.setBackground(Color.ORANGE);
        	}
        	
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                		buttons[0].repaint();
                		buttons[1].repaint();
                		buttons[2].repaint();
                	
                    repaint();
                }});
        }
        contentPane.add("North", transPan );
        for (int n = 0; n < p.size(); n++ ) {
       		labels[n] = new PlaceLabel(p.get(n));       		
       		c.gridx = 1;
       		c.gridy = (n+1);
       		
       		places.add(labels[n],c);
        }
        contentPane.add("West", places);
    }



    public static void displayPetrinet(final Petrinet pn) {

        Runnable guiCreator = new Runnable() {
            public void run() {
                JFrame fenster = new PetrinetGUI(pn);

                // Swing anweisen, das Programm zu beenden, wenn das Fenster
                // geschlossen wird
                fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Zeigt das Fenster an
                fenster.setSize(800, 600);
                fenster.setVisible(true);
            }
        };

        // Fahre den obigen Quellcode im Event-Dispatch-Thread aus
        SwingUtilities.invokeLater(guiCreator);
    }
}