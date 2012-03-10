package petrinet.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * This class creates a petrinet object, containing places, transitions, arcs, and inhibitors
 *
 */

public class Petrinet
extends PetrinetObject {

    List<Place> places              = new ArrayList<Place>();
    List<Transition> transitions    = new ArrayList<Transition>();
    List<Arc> arcs                  = new ArrayList<Arc>();
    List<InhibitorArc> inhibitors   = new ArrayList<InhibitorArc>();
    
    // Default constructor
    public Petrinet(String name) {
        super(name);
    }

    // Adds an item to the Petrinet
    public void add(PetrinetObject item) {
        if (item instanceof InhibitorArc) {
            inhibitors.add((InhibitorArc) item);
        } else if (item instanceof Arc) {
            arcs.add((Arc) item);
        } else if (item instanceof Place) {
            places.add((Place) item);
        } else if (item instanceof Transition) {
            transitions.add((Transition) item);
        }
    }
    
    // Retrieves any transitions that are ready to fire
    public List<Transition> getTransitionsAbleToFire() {
        ArrayList<Transition> list = new ArrayList<Transition>();
        for (Transition t : transitions) {
            if (t.canFire()) {
                list.add(t);
            }
        }
        return list;
    }
    
    // Adds a transition to the petrinet
    public Transition addTransition(String name) {
        Transition t = new Transition(name);
        transitions.add(t);
        return t;
    }
    
    // Adds a place to the petrinet with 0 initial tokens
    public Place addPlace(String name) {
        return addPlace(name, 0);
    }
    
    // Adds a place to the petrinet, you set how many initial tokens it has
    public Place addPlace(String name, int tokens) {
        Place p = new Place(name, tokens);
        places.add(p);
        return p;
    }
    
    // Adds an arc to the petrinet, coming from a place to a transition
    public Arc addArc(String name, Place place, Transition tran) {
        Arc arc = new Arc(name, place, tran);
        arcs.add(arc);
        return arc;
    }
    
    // Adds an arc to the petrinet, coming from a transition to a place
    public Arc addArc(String name, Transition tran, Place place) {
        Arc arc = new Arc(name, tran, place);
        arcs.add(arc);
        return arc;
    }
    
    // Adds a guard to the petrinet between a place and a transition
    public InhibitorArc addGuard(String name, Place place, Transition tran) {
        InhibitorArc guard = new InhibitorArc(name, place, tran);
        inhibitors.add(guard);
        return guard;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Petrinet ");
        sb.append(super.toString() + "\n");
        sb.append("---Transitions---\n");
        for (Transition t : transitions) {
            sb.append(t).append("\n");
        }
        sb.append("---Places---\n");
        for (Place p : places) {
            sb.append(p).append("\n");
        }
        return sb.toString();
    }

	// Searches through all of the Places in this Petrinet and finds one with the matching name
	// Returns null if none can be found
	public Place getPlace(String name) {
		for (Place p : places) {
			if (p.getName().equalsIgnoreCase(name)) {
            	return p;
			}
		}
		return null;
	}
	
    public List<Place> getPlaces() {
        return places;
    }

	// Searches through all of the Transitions in this Petrinet and finds one with the matching name
	// Returns null if none can be found
	public Transition getTransition(String name) {
		for (Transition t : transitions) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}
	
    public List<Transition> getTransitions() {
        return transitions;
    }

	// Searches through all of the Arcs in this Petrinet and finds one with the matching name
	// Returns null if none can be found
	public Arc getArc(String name) {
		for (Arc a : arcs) {
			if (a.getName().equals(name)) {
				return a;
			}
		}
		return null;
	}

    public List<Arc> getArcs() {
        return arcs;
    }

	// Searches through all of the InhibitorArcs in this Petrinet and finds one with the matching name
	// Returns null if none can be found
    public InhibitorArc getGuard(String name) {
    	for (InhibitorArc g : inhibitors) {
    		if (g.getName().equals(name)) {
    			return g;
    		}
    	}
    	return null;
    }
    public List<InhibitorArc> getInhibitorArcs() {
        return inhibitors;
    }
}
