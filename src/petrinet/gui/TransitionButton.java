package petrinet.gui;

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
		
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(transition.canFire()){
				    pnGui.pn.graph.getModel().beginUpdate();
					transition.fire();
					fireStateChanged();
					pnGui.pn.graph.getModel().endUpdate();
					pnGui.refreshScreen();
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
