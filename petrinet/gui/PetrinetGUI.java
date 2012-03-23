package petrinet.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import petrinet.logic.Petrinet;
import petrinet.logic.Place;
import petrinet.logic.Transition;

@SuppressWarnings("serial")
public class PetrinetGUI extends JFrame implements ActionListener{
	
	/**
	 * Variables and Constants for the class
	 */
	private final static Color CAN_FIRE = Color.GREEN;
	private final static Color NORMAL = Color.ORANGE;
	private final static Color UNCONNECTED = Color.GRAY;
	private final static Color BORDERS = Color.BLACK;
	private Petrinet pn;
	private List<Transition> tList = null;
	private List<Place> pList = null;
	private TransitionButton[] buttons = null;
	private PlaceLabel[] labels = null;
	private JFrame confirm = null;
	private JButton confirmYes = null;
 	private JButton confirmNo = null;
 	private JLabel confirmLabel = null;
	
	/**
	 * Extends JButton to manage transitions when the user clicks to fire a transition
	 */

	@SuppressWarnings("serial")
	public class TransitionButton extends JButton{
		private Transition transition;
		private PetrinetGUI parentNet;
		
		/**
		 * Default constructor for Transition Buttons
		 * @param transition
		 */
		public TransitionButton(final Transition transition){
			super(transition.getName());
			this.transition = transition;	
			
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(transition.canFire()){
						transition.fire();
						fireStateChanged();
						refreshScreen();
					}			
				}
			});
		}
		
		/**
		 * This methods returns whether or not the button is enabled for firing
		 * @return boolean
		 */
		public boolean isEnabled(){
			if(transition == null){
				return false;
			}
			return transition.canFire();
		}
		
		/**
		 * This method gets the title of the transition to be put on the buttons
		 * @return String
		 */
		public String getText(){
			if(transition == null){
				return null;
			}
			return transition.toString();
		}
	}

	/**
	 * This method repaint the items on the screen after each firing
	 */
	public void refreshScreen(){
		for(int i = 0; i < buttons.length; i++){
			if(tList.get(i).canFire()){
				buttons[i].setBackground(CAN_FIRE);
			}else if(tList.get(i).isNotConnected()){
				buttons[i].setBackground(UNCONNECTED);
			}else{
				buttons[i].setBackground(NORMAL);
			}
			buttons[i].repaint();
		}
		for(int i = 0; i < labels.length; i++){
			labels[i].repaint();
		}
	}
	
	/**
	 * Default constructor for the petrinet gui
	 * @param pn
	 */
	public PetrinetGUI(Petrinet pn){
		super(pn.getName());
		this.pn = pn;
		
		tList = pn.getTransitions();
		pList = pn.getPlaces();
		
		Container contentPane = getContentPane();
		JPanel transitionsPanel = new JPanel();
		JPanel placesPanel = new JPanel(new GridBagLayout());
		placesPanel.setBorder(BorderFactory.createLineBorder(BORDERS));
		JLabel placesTitle = new JLabel("Places");
		
		JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem newP = new JMenuItem("New");
        JMenuItem importP = new JMenuItem("Import");
        JMenuItem quit = new JMenuItem("Quit");
        
        //Menu items with acction listeners
        menuBar.add(file);
        file.add(newP);
        file.add(importP);
        file.add(quit);
        setJMenuBar(menuBar);
        newP.addActionListener(this);
        importP.addActionListener(this);
        quit.addActionListener(this);
        
		
		//Create a GridBagConstraints object for laying out the title and used later for places
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.ipadx = 10;
		c.ipady = 10;
		
		placesPanel.add(placesTitle, c);
		
		buttons = new TransitionButton[tList.size()];
		labels = new PlaceLabel[pList.size()];
		
		for(int i = 0; i < tList.size(); i++){
			buttons[i] = new TransitionButton(tList.get(i));
			transitionsPanel.add(buttons[i]);
			
			if(tList.get(i).canFire()){
				buttons[i].setBackground(CAN_FIRE);
			}else if(tList.get(i).isNotConnected()){
				buttons[i].setBackground(UNCONNECTED);
			}else{
				buttons[i].setBackground(NORMAL);
			}
		}
		contentPane.add("North", transitionsPanel);
		
		int lineNumber = 0;
		// Add all of the PlaceLabels to the placesPanel for dispaly
		for(int i = 0; i < pList.size(); i++){
			labels[i] = new PlaceLabel(pList.get(i));
			c = new GridBagConstraints();
			c.gridx = 1;
			c.gridy = lineNumber;
			c.ipadx = 10;
			c.ipady = 10;
			
			placesPanel.add(labels[i], c);
			//c.ipadx = 5;
			//c.ipady = 5;
			lineNumber++;
			c.gridy = lineNumber; //moves to next line to draw tokens
			
			for(int n = 0; n < labels[i].getTokens(); n++){
				c.gridx = 2 + n; 
				//dots in here
			}
			lineNumber++;
		}
		contentPane.add("West", placesPanel);		
	}

	public void actionPerformed(ActionEvent e){
		String buttonString = e.getActionCommand( );
		
		if(buttonString.equals("New")){
			JOptionPane.showMessageDialog(null, "We are still working on implementing the new network feature");
		}else if(buttonString.equals("Import")){
			JOptionPane.showMessageDialog(null, "We are still working on implementing the import feature");
		}else if(buttonString.equals("Quit")){
	    	confirm = new JFrame("Quit?");
	    	confirmYes = new JButton("Yes");
	     	confirmNo = new JButton("No");
	    	confirm.setLayout(new GridBagLayout());
	    	confirm.setSize(200, 150);
	    	confirm.setResizable(false);
	    	confirmLabel = new JLabel("Quit? Are you sure?");
	    	GridBagConstraints c = new GridBagConstraints();
	    	
	    	c.gridx=1;
	    	c.gridy=3;
	    	confirm.add(confirmYes,c);
	    	c.gridx=3;
	    	c.gridy=3;
	    	confirm.add(confirmNo,c);
	    	c.gridx=1;
	    	c.gridy=1;
	    	c.gridwidth=3;
	    	
	    	confirm.add(confirmLabel,c);
	    	confirmLabel.setVisible(true);
	    	confirmYes.addActionListener(this);
	    	confirmNo.addActionListener(this);
	    	confirmYes.setVisible(true);
	    	confirmNo.setVisible(true);
	    	confirm.setVisible(true);
	    	confirm.setLocation(200, 100);
	        	
		}else if(buttonString.equals("Yes")){
			System.exit(0);
		}else if(buttonString.equals("No")){
			confirm.dispose();
		}else{
			System.out.println("Unexpected error.");
	    }
	}
	
	public static void displayPetrinet(final Petrinet pn){
		Runnable guiCreator = new Runnable(){
			public void run(){
				JFrame window = new PetrinetGUI(pn);				
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit the application when the window is closed				
				window.setLocation(50, 50); 				
				window.setSize(800,600);//Set window size
				window.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(guiCreator);
	}
	
	
}
