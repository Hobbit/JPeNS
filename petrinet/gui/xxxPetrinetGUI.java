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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import petrinet.logic.Petrinet;
import petrinet.logic.Place;
import petrinet.logic.Transition;

public class xxxPetrinetGUI extends JFrame {

    Petrinet pn;
    List<Transition> tList = null;
    TransitionButton[] buttons = null;
    
	final Color CAN_FIRE = Color.green;
	final Color NORMAL = Color.orange;
	final Color UNCONNECTED = Color.gray;

	/**
	 * Extends JLabel and provides the name of a place as a label
	 */
	public class PlaceLabel extends JLabel {
		
        private Place place;

        public PlaceLabel(Place place) {
            super(place.toString());
            this.place = place;
        }

        /**
         * @return the name of the place, if the place is null, returns null
         */
        @Override
        public String getText() {
            if (place == null) {
                return null;
            }
            return place.toString();
        }
        
        /**
         * @return The number of tokens at this place
         */
        public int getTokens() {
        	return place.getTokens();
        }
    }

	/**
	 * Extends JButton to manage transitions when the user clicks to fire a transition
	 */
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
                        refreshButtons(buttons, tList);
                    }
                    if(transition.canFire()){
                		setBackground(CAN_FIRE);
                	}else if(transition.isNotConnected()){
                		setBackground(UNCONNECTED);
                	}else{
                		setBackground(NORMAL);
                	}
                    repaint();
                }
            });
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


    public void refreshButtons(TransitionButton[] buttonArr, List<Transition> trans){
    	for(int i = 0; i < buttonArr.length; i++){
    		Transition tTemp = trans.get(i);
    		if(tTemp.canFire()){
    			buttonArr[i].setBackground(CAN_FIRE);
        	}else if(tTemp.isNotConnected()){
        		buttonArr[i].setBackground(UNCONNECTED);
        	}else{
        		buttonArr[i].setBackground(NORMAL);
        	}
    		buttonArr[i].repaint();
    		System.out.println("Repainted:"+buttonArr[i].toString());
    	}    	
    }
    
    public xxxPetrinetGUI(Petrinet pn) {
    	// Create a new JFrame
        super(pn.getName());
        this.pn = pn;
        tList = pn.getTransitions();
        List<Place> p = pn.getPlaces();
        Container contentPane = getContentPane();
        JPanel transPan = new JPanel(); 
        JPanel placesPan = new JPanel(new GridBagLayout());
        placesPan.setBorder(BorderFactory.createLineBorder(Color.black));
        JLabel placesTitle = new JLabel("Places");
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        menuBar.add(file);
        setJMenuBar(menuBar);
        
        JMenuItem quit = new JMenuItem("Quit");
        file.add(quit);
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	JFrame sure = new JFrame("Quit?");
            	JButton yes = new JButton("Yes");
            	JButton no = new JButton("No");
            	JLabel label = new JLabel("Quit? Are you sure?");
            	
            	sure.setLayout(new GridBagLayout());
            	sure.setSize(200, 150);
            	sure.setResizable(false);
            	GridBagConstraints c = new GridBagConstraints();
            	sure.setVisible(true);
            }
        });
        
        menuBar.setVisible(true);

        
        // Create a GridBagConstraints object for laying out the title and used later for places
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 10;
        c.ipady = 10;
        
        placesPan.add(placesTitle, c);

        buttons = new TransitionButton[tList.size()];
        PlaceLabel[] labels = new PlaceLabel[p.size()];
        for (int i = 0; i < tList.size(); i++) {
        	buttons[i] = new TransitionButton(tList.get(i));
        	transPan.add(buttons[i]);
        	Transition tTemp = tList.get(i);
        	TransitionButton bTemp = buttons[i];
        	if(tTemp.canFire()){
        		bTemp.setBackground(CAN_FIRE);
        	}else if(tTemp.isNotConnected()){
        		bTemp.setBackground(UNCONNECTED);
        	}else{
        		bTemp.setBackground(NORMAL);
        	}
        	
        	// Repaint all of the transition buttons after an event
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	for (TransitionButton b : buttons) {
                		b.repaint();
                	}

                    repaint();
                }
            });
        }
        contentPane.add("North", transPan );
        
        int lineNum = 0;
        
        // Add all of the placelabels to the placesPan for display
        for (int n = 0; n < p.size(); n++ ) {
       		labels[n] = new PlaceLabel(p.get(n));   
       		c = new GridBagConstraints();
       		c.gridx = 1;
       		c.gridy = lineNum;
       		c.ipadx = 10;
       		c.ipady = 10;
       		
       		placesPan.add(labels[n],c);
   			//c.ipadx = 5;
   			//c.ipady = 5;
   			lineNum++;
       		c.gridy = lineNum; // We want to put the dots on the next line
       		
       		for (int m = 0; m < labels[n].getTokens(); m++) {
       			c.gridx = 2 + m;
	       		// Create a dot for each place in this placelabel
	       		
	    
       		}
       		lineNum++;
        }
        contentPane.add("West", placesPan);
    }

    public static void displayPetrinet(final Petrinet pn) {

        Runnable guiCreator = new Runnable() {
            public void run() {
                JFrame window = new xxxPetrinetGUI(pn);

                // Exit the application when the window is closed
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                // Center the window
                window.setLocationRelativeTo(null);
                
                // Set the window size
                window.setSize(800, 600);
                window.setVisible(true);
            }
        };

        // Run this code in the event-dispatch thread 
        SwingUtilities.invokeLater(guiCreator);
    }
}
