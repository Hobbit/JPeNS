package master;

import java.awt.Color;
import java.awt.GridBagConstraints;

public class Config {
	public static final boolean PRINT_DIAG_INFO = false;
	
	/*
	 * mxGraph styles for the graph layout
	 */
	public static final int PLACE_WIDTH = 80;
	public static final int PLACE_HEIGHT = 30;
	public static final int TRANSITION_WIDTH = 80;
	public static final int TRANSITION_HEIGHT = 30;
	public static final String PLACE_STYLE = "shape=ellipse;perimter=ellipsePerimeter;strokeColor=black;fillColor=gray";
	public static final String HAS_TOKENS_STYLE = "shape=ellipse;perimter=ellipsePerimeter;strokeColor=black;fillColor=blue;fontColor=white";
	public static final String TRANSITION_STYLE = "strokeColor=black;fillColor=orange";
	public static final String CAN_FIRE_STYLE = "strokeColor=black;fillColor=green";

	/*
	 * Button styles
	 */
	public static final Color CAN_FIRE = Color.GREEN;
	public static final Color NORMAL = Color.ORANGE;
	public static final Color UNCONNECTED = Color.RED;
	public static final Color BORDERS = Color.BLACK;

	/*
	 * Exporter styles
	 */
	public static GridBagConstraints constraints() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.ipadx = 2;
		c.ipady = 2;
		
		return c;
	}
}
