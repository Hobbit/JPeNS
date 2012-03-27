package petrinet.logic;

import java.util.ArrayList;
import java.util.List;

public class Transition
extends PetrinetObject{

    public Transition(String name) {
        super(name);

    }

    private List<Arc> incoming = new ArrayList<Arc>();
    private List<Arc> outgoing = new ArrayList<Arc>();

	
    
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
