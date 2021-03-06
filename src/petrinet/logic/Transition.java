package petrinet.logic;

import master.Config;

import java.util.ArrayList;
import java.util.List;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

public class Transition extends PetrinetObject{
    private List<Arc> incoming = new ArrayList<Arc>();
    private List<Arc> outgoing = new ArrayList<Arc>();
    private mxGraph graph;
    
    public Transition(String name, mxGraph graph) {
        super(name);
        this.graph = graph;
    	String style = this.canFire() ? Config.CAN_FIRE_STYLE : Config.TRANSITION_STYLE;
   	
        // Add a vertex to the graph
	    graph.getModel().beginUpdate();            
	    this.mxcell = (mxCell)graph.insertVertex(graph.getDefaultParent(), null, name, 0, 0,
    			Config.TRANSITION_WIDTH, Config.TRANSITION_HEIGHT, style);
	    graph.getModel().endUpdate();
    }    
    
    /**
     * Checks if the transition is able to fire
     */
    public boolean canFire() {
        boolean canFire = true;
        
        //If the transition is not connected, sets canFire to false 
        canFire = ! this.isNotConnected();
        
        for (Arc arc : incoming) {
            canFire = canFire & arc.canFire();
        }
        
        for (Arc arc : outgoing) {
            canFire = canFire & arc.canFire();
        }
        return canFire;
    }
    
    // Update the style of the mxCell based on whether we can still fire or not
    public void Refresh() {
    	String style = this.canFire() ? Config.CAN_FIRE_STYLE : Config.TRANSITION_STYLE;  
	    graph.getModel().beginUpdate();
    	mxcell.setStyle(style);
		graph.getModel().endUpdate();
    }
    
    /**
     * Fire the transition
     */
    public void fire() {
        for (Arc arc : incoming) {
            arc.fire();
        }
        
        for (Arc arc : outgoing) {
            arc.fire();
        }
        
        Refresh();
    }
    
    /**
     * Add incoming arc
     */
    public void addIncoming(Arc arc) {
        this.incoming.add(arc);
    }
    
    /**
     * Add outgoing arc
     */
    public void addOutgoing(Arc arc) {
        this.outgoing.add(arc);
    }

    /**
     * Checks if it is connected, returns boolean
     */
    public boolean isNotConnected() {
        return incoming.isEmpty() && outgoing.isEmpty();
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
}
