package petrinet.gui;

import master.Config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import petrinet.logic.Transition;

/**
 * Extends JButton to manage transitions when the user clicks to fire a transition
 */
@SuppressWarnings("serial")
public class TransitionButton extends JButton{
	private Transition transition;
	private PetrinetGUI pnGui;
	
	/**
	 * Default constructor for Transition Buttons
	 * @param transition
	 */
	public TransitionButton(final Transition transition, PetrinetGUI view){
		super(transition.getName());
		this.transition = transition;
		this.pnGui = view;
		this.Refresh();
		
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(transition.canFire()){
					transition.fire();
					fireStateChanged();
					pnGui.refreshScreen();
				}			
			} 
		});
	}
	
	/*
	 * Updates the background color of the button based on the transition's status
	 */
	public void Refresh() {
		if(transition.canFire()){
			this.setBackground(Config.CAN_FIRE);
		}else if(transition.isNotConnected()){
			this.setBackground(Config.UNCONNECTED);
		}else{
			this.setBackground(Config.NORMAL);
		}
		this.repaint();
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
