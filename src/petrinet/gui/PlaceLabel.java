package petrinet.gui;

import javax.swing.JLabel;

import petrinet.logic.Place;

/**
 * Extends JLabel and provides the name of a place as a GUI Label
 * @return Place label
 */
@SuppressWarnings("serial")
public class PlaceLabel extends JLabel{
	private Place place;
	
	/**
	 * Default constructor for PlaceLabel
	 * @param place
	 */
	public PlaceLabel(Place place){
		super(place.toString());
		this.place = place;
	}	
	/**
	 * Gets the name of the place
	 * @return Name of the current place, else returns null
	 */
	public String getText(){
		if(place == null){
			return null;
		}
		return place.toString();
	}
	/**
	 * Gets the number of tokens on the place
	 * @return number of tokens
	 */
	public int getTokens(){
		return place.getTokens();
	}
}
