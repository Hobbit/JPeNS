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

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
@SuppressWarnings("serial")

/**
 * Creates an MXGraph which can then be used by a layout mechanism in the calling class
 */
public class JGraphPanel  extends JPanel {
 
	public mxGraph graph;
	public mxGraphComponent graphComponent;
  
	final int PLACE_WIDTH = 80;
	final int PLACE_HEIGHT = 30;
	final int TRANSITION_WIDTH = 80;
	final int TRANSITION_HEIGHT = 30;
	final String PLACE_STYLE = "strokeColor=red;fillColor=green";
	final String TRANSITION_STYLE = "ROUNDED;strokeColor=green;fillColor=orange";
  
	// A way to store places and transitions that are retrievable by name
	// There are 2 Maps since a transition and place may have the same name, perhaps.
	private Map<String, Object> placeVertices = new HashMap<String, Object>();
	private Map<String, Object> transVertices = new HashMap<String, Object>();
    
	public JGraphPanel(Petrinet pn) {
		graph = new mxGraph();
	
	    Object defaultParent = graph.getDefaultParent();
	    graph.getModel().beginUpdate();
	
	    // For each place in the petrinet, add a vertex
	    for (Place p : pn.getPlaces()) {
	    	Object vertex = graph.insertVertex(defaultParent, null, p.getName(), 0, 0, 
	    			PLACE_WIDTH, PLACE_HEIGHT, PLACE_STYLE);
	    	placeVertices.put(p.getName(), vertex);
	    }
	    
	    // For each transition in the petrinet, add a vertex
	    for (Transition t : pn.getTransitions()) {
	    	Object vertex = graph.insertVertex(defaultParent, null, t.getName(), 0, 0, 
	    			TRANSITION_WIDTH, TRANSITION_HEIGHT, TRANSITION_STYLE);
	    	transVertices.put(t.getName(), vertex);
	    }
	            
	    // For each arc draw the edge to represent it
	    for (Arc a : pn.getArcs()) {
	    	// Create objects to represent the 'from' and 'to' nodes
	    	Object from = null;
	    	Object to = null;
	    	
	    	// Retrieve the appropriate vertex for the from-node of the arc
	    	if (a.from == EndType.PLACE) {
	    		from = placeVertices.get(a.getPlace().getName());
	    	}
	    	else if (a.from == EndType.TRANSITION) {
	    		from = transVertices.get(a.getTransition().getName());
	    	}
	    		
	    	// Retrieve the appropriate vertex for the to-node of the arc
	    	if (a.to == EndType.PLACE) {
	    		to = placeVertices.get(a.getPlace().getName());
	    	}
	    	else if (a.to == EndType.TRANSITION) {
	    		to = transVertices.get(a.getTransition().getName());
	    	}
	    	
	    	graph.insertEdge(defaultParent, null, "Edge", from, to);
	    }
	    
	    // For each arc, find the place it is going to and from, then make edges.
	    
	    graph.getModel().endUpdate();
	    graphComponent = new mxGraphComponent(graph);
	}
}