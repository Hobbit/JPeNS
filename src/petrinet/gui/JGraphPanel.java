package petrinet.gui;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import petrinet.logic.ArcEndType.EndType;

import petrinet.logic.Arc;
import petrinet.logic.Petrinet;
import petrinet.logic.Place;
import petrinet.logic.Transition;

import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
@SuppressWarnings("serial")

/**
 * Creates an MXGraph which can then be used by a layout mechanism in the calling class
 */
public class JGraphPanel extends JPanel {
 
	final int PLACE_WIDTH = 80;
	final int PLACE_HEIGHT = 30;
	final int TRANSITION_WIDTH = 80;
	final int TRANSITION_HEIGHT = 30;
	final String PLACE_STYLE = "strokeColor=black;fillColor=gray";
	final String TRANSITION_STYLE = "ROUNDED;strokeColor=green;fillColor=orange";
	final String CAN_FIRE_STYLE = "ROUNDED;strokeColor=green;fillColor=green";
	
	public mxGraph graph;
	public mxGraphComponent graphComponent;
	public Petrinet pn = null;
	  
	// A way to store places and transitions that are retrievable by name
	// There are 2 Maps since a transition and place may have the same name, perhaps.
	private Map<String, mxCell> placeVertices = new HashMap<String, mxCell>();
	private Map<String, mxCell> transVertices = new HashMap<String, mxCell>();
    
	private Object defaultParent = null;
	
	public JGraphPanel(Petrinet pn) {
		graph = new mxGraph();
		this.pn = pn;
	    defaultParent = graph.getDefaultParent();
	    
	    DrawGraph();
	}
	
	/**
	 * Draws the graph from the petrinet
	 */
	public void DrawGraph() {
		// Clear out our existing graph data so that we can reconstruct the graph
		placeVertices.clear();
		transVertices.clear();
		graph.removeCells(graph.getChildCells(defaultParent, true, true));
		
	    graph.getModel().beginUpdate();
		
	    // For each place in the petrinet, add a vertex
	    for (Place p : pn.getPlaces()) {
	    	mxCell vertex = (mxCell)graph.insertVertex(defaultParent, null, p.getName(), 0, 0, 
	    			PLACE_WIDTH, PLACE_HEIGHT, PLACE_STYLE);
	    	placeVertices.put(p.getName(), vertex);
	    }
	    
	    // For each transition in the petrinet, add a vertex
	    for (Transition t : pn.getTransitions()) {
	    	String style = t.canFire() ? CAN_FIRE_STYLE : TRANSITION_STYLE;
	    	mxCell vertex = (mxCell)graph.insertVertex(defaultParent, null, t.getName(), 0, 0, 
	    			TRANSITION_WIDTH, TRANSITION_HEIGHT, style);
	    	transVertices.put(t.getName(), vertex);
	    }
	            
	    // For each arc draw the edge to represent it
	    for (Arc a : pn.getArcs()) {
	    	// Create objects to represent the 'from' and 'to' nodes
	    	Object from = null;
	    	Object to = null;
	    	
	    	// Retrieve the appropriate vertex for the from-node of the arc
	    	if (a.fromType == EndType.PLACE) {
	    		from = placeVertices.get(a.getPlace().getName());
	    	}
	    	else if (a.fromType == EndType.TRANSITION) {
	    		from = transVertices.get(a.getTransition().getName());
	    	}
	    		
	    	// Retrieve the appropriate vertex for the to-node of the arc
	    	if (a.toType == EndType.PLACE) {
	    		to = placeVertices.get(a.getPlace().getName());
	    	}
	    	else if (a.toType == EndType.TRANSITION) {
	    		to = transVertices.get(a.getTransition().getName());
	    	}
	    	
	    	graph.insertEdge(defaultParent, null, "Edge", from, to);
	    }
	    
	    // For each arc, find the place it is going to and from, then make edges.
	    
	    graph.getModel().endUpdate();
	    graphComponent = new mxGraphComponent(graph);
	    
	    mxOrganicLayout organic = new mxOrganicLayout(graph);
	    organic.execute(defaultParent);
	}
}