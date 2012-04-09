package petrinet.gui;

import master.Importer;
import master.Config;

import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URI;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
	static JFrame window = null;
	public Petrinet pn;
	private List<Transition> tList = null;
	private List<Place> pList = null;
	private TransitionButton[] buttons = null;
	private PlaceLabel[] labels = null; 
	private JGraphPanel graphPan = null;
	private JFrame confirm = null;
	private JButton confirmYes = null;
 	private JButton confirmNo = null;
 	private JLabel confirmLabel = null;

	/**
	 * Default constructor for the petrinet gui
	 * @param pn
	 * @throws IOException 
	 */
	public PetrinetGUI(Petrinet pn) throws IOException{
		super(pn.getName());
		this.pn = pn;
		
		//Loads lists with the petrinet's transitions and places
		tList = pn.getTransitions();
		pList = pn.getPlaces();
		
		//Main container and panels
		Container contentPane = getContentPane();
		JPanel transitionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel placesPanel = new JPanel(new GridBagLayout());
		placesPanel.setBorder(BorderFactory.createLineBorder(Config.BORDERS));
		JLabel placesTitle = new JLabel("Places");
		
	
		//Top menu bar
		JMenuBar menuBar = new JMenuBar();
		
		//File menu
        JMenu file = new JMenu("File");
        JMenuItem newP = new JMenuItem("New");
        JMenuItem importP = new JMenuItem("Import");
        JMenuItem quit = new JMenuItem("Quit");
        
        //Help menu
        JMenu help = new JMenu("Help");
        JMenuItem hFire = new JMenuItem("Can Fire");
        hFire.setBackground(Config.CAN_FIRE);
        hFire.setEnabled(false);
        JMenuItem hEmpty = new JMenuItem("Cannot Fire");
        hEmpty.setBackground(Config.NORMAL);
        hEmpty.setEnabled(false);
        JMenuItem hUnconnect = new JMenuItem("Unconnected");
        hUnconnect.setBackground(Config.UNCONNECTED);
        hUnconnect.setEnabled(false);
        JMenuItem hNoTokens = new JMenuItem("No Tokens");
        hNoTokens.setBackground(Color.GRAY);
        hNoTokens.setEnabled(false);
        JMenuItem hHasTokens = new JMenuItem("Has Tokens");
        hHasTokens.setBackground(Color.BLUE);
        hHasTokens.setEnabled(false);
        JMenuItem homepage = new JMenuItem("Homepage");
        
       
        
        //File menu items with action listeners
        menuBar.add(file);
        file.add(newP);
        file.add(importP);
        file.add(quit);
        setJMenuBar(menuBar);
        newP.addActionListener(this);
        importP.addActionListener(this);
        quit.addActionListener(this);
        
        //Help menu
        menuBar.add(help);
        help.add(hFire);
        help.add(hEmpty);
        help.add(hUnconnect);
        help.add(hNoTokens);
        help.add(hHasTokens);
        help.add(homepage);
        homepage.addActionListener(this);
        
		//Create a GridBagConstraints object for laying out the title and used later for places
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.ipadx = 10;
		c.ipady = 10;
		
		placesPanel.add(placesTitle, c);
		
		//Initialize buttons and labels arrays
		buttons = new TransitionButton[tList.size()];
		labels = new PlaceLabel[pList.size()];
				
		//Steps through each transition and creates a new button and initializes
		//it to the appropriate status color.
		for(int i = 0; i < tList.size(); i++){
			buttons[i] = new TransitionButton(tList.get(i), this);
			transitionsPanel.add(buttons[i]);
		}
		contentPane.add("North", transitionsPanel);
		
		// Add all of the PlaceLabels to the placesPanel for display
		for(int i = 0; i < pList.size(); i++){
			labels[i] = new PlaceLabel(pList.get(i));
			c = new GridBagConstraints();
			c.gridx = 1;
			c.gridy = i + 1;
			c.ipadx = 10;
			c.ipady = 10;
			
			placesPanel.add(labels[i], c);
		}
		contentPane.add("West", placesPanel);				
		
		/*
		 * Add the Graph layout frame here
		 */    	    
	    graphPan = new JGraphPanel(pn.graph);
	    contentPane.add("Center", graphPan.graphComponent);
	}
	
	/**
	 * This method catches the action listeners and performs the specified actions
	 */
	public void actionPerformed(ActionEvent e){
		String buttonString = e.getActionCommand( );
		
		if(buttonString.equals("New")){
			//To be implemented
			JOptionPane.showMessageDialog(PetrinetGUI.this, "We are still working on implementing the new network feature");
		}else if(buttonString.equals("Import")){
			//To be implemented fully
			JFileChooser fileBrowser = new JFileChooser();
			int returnVal = fileBrowser.showOpenDialog(PetrinetGUI.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File newFile = fileBrowser.getSelectedFile();
				pn.clear();
				pn.setFilepath(newFile.toString());
				Importer.Import(pn.getFilepath(), pn);
				window.dispose();
				petrinet.gui.PetrinetGUI.displayPetrinet(pn);
		    }
			
		}else if(buttonString.equals("Quit")){
			//create a new quit confirmation window
	    	confirm = new JFrame("Quit?");
	    	confirmYes = new JButton("Yes");
	     	confirmNo = new JButton("No");
	    	confirm.setLayout(new GridBagLayout());
	    	confirm.setSize(200, 150);
	    	confirm.setResizable(false);
	    	confirmLabel = new JLabel("Quit? Are you sure?");
	    	GridBagConstraints c = new GridBagConstraints();
	    	
	    	//Confirmation window constraints
	    	c.gridx=1;
	    	c.gridy=3;
	    	confirm.add(confirmYes,c);
	    	c.gridx=3;
	    	c.gridy=3;
	    	confirm.add(confirmNo,c);
	    	c.gridx=1;
	    	c.gridy=1;
	    	c.gridwidth=3;
	    	
	    	//Initialize the window
	    	confirm.add(confirmLabel,c);
	    	confirmLabel.setVisible(true);
	    	confirmYes.addActionListener(this);
	    	confirmNo.addActionListener(this);
	    	confirmYes.setVisible(true);
	    	confirmNo.setVisible(true);
	    	confirm.setVisible(true);
	    	confirm.setLocation(200, 100);
	        	
		}else if(buttonString.equals("Yes")){
			//Confirm yes, quit
			System.exit(0);
		}else if(buttonString.equals("No")){
			//Confirm no, destroy confirmation window and continue with program
			confirm.dispose();
		}else if(buttonString.equals("Homepage")){
			try {
				Desktop.getDesktop().browse(URI.create("https://github.com/Hobbit/JPeNS"));
			} catch (IOException e1) {
				System.out.println("**ERROR: Could not open homepage");
			}
		}else{
			//catch-all
			System.out.println("Unexpected error.");
	    }
	}
	
	public static void displayPetrinet(final Petrinet pn){
		Runnable guiCreator = new Runnable(){
			public void run(){
				//JFrame window = new JFrame();
				try {
					window = new PetrinetGUI(pn);
				} catch (IOException e) {
					e.printStackTrace();
				} //Creates new PetrinetGUI object				
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit the application when the window is closed				
				window.setLocation(50, 50); 				
				window.setSize(800,600);//Set window size
				window.setMinimumSize(new Dimension(500, 500));
				window.setVisible(true); //Set window to visible
			}
		};
		SwingUtilities.invokeLater(guiCreator);
	}	
	
	/**
	 * This method repaints the items on the screen after each firing
	 */
	public void refreshScreen(){
		for (TransitionButton button : buttons) {
			button.Refresh();
		}
		
		for(PlaceLabel label : labels){
			label.repaint();
		}
		
		// Refresh the graph
		graphPan.RefreshGraph();
	}	
}
