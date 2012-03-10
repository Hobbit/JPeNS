package petrinet.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Ein Petrinetz kennt alle Elemente.
 * 
 * Deshalb sollten nach Maeglichkeit auch besser diese Factory-Methoden 
 * zum Erstellen von Objekten benutzt werden, anstatt die Konstruktoren 
 * direkt aufzurufen.
 * 
 * @author rmetzler
 *
 */

public class Petrinet
extends PetrinetObject {

    private static final String nl = "\n";
    List<Place> places              = new ArrayList<Place>();
    List<Transition> transitions    = new ArrayList<Transition>();
    List<Arc> arcs                  = new ArrayList<Arc>();
    List<InhibitorArc> inhibitors   = new ArrayList<InhibitorArc>();
    
    public Petrinet(String name) {
        super(name);
    }

    public void add(PetrinetObject o) {
        if (o instanceof InhibitorArc) {
            inhibitors.add((InhibitorArc) o);
        } else if (o instanceof Arc) {
            arcs.add((Arc) o);
        } else if (o instanceof Place) {
            places.add((Place) o);
        } else if (o instanceof Transition) {
            transitions.add((Transition) o);
        }
    }
    
    public List<Transition> getTransitionsAbleToFire() {
        ArrayList<Transition> list = new ArrayList<Transition>();
        for (Transition t : transitions) {
            if (t.canFire()) {
                list.add(t);
            }
        }
        return list;
    }
    
    public Transition transition(String name) {
        Transition t = new Transition(name);
        transitions.add(t);
        return t;
    }
    
    public Place place(String name) {
        Place p = new Place(name);
        places.add(p);
        return p;
    }
    
    public Place place(String name, int initial) {
        Place p = new Place(name, initial);
        places.add(p);
        return p;
    }
    
    public Arc arc(String name, Place p, Transition t) {
        Arc arc = new Arc(name, p, t);
        arcs.add(arc);
        return arc;
    }
    
    public Arc arc(String name, Transition t, Place p) {
        Arc arc = new Arc(name, t, p);
        arcs.add(arc);
        return arc;
    }
    
    public InhibitorArc inhibitor(String name, Place p, Transition t) {
        InhibitorArc i = new InhibitorArc(name, p, t);
        inhibitors.add(i);
        return i;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Petrinet ");
        sb.append(super.toString()).append(nl);
        sb.append("---Transitions---").append(nl);
        for (Transition t : transitions) {
            sb.append(t).append(nl);
        }
        sb.append("---Places---").append(nl);
        for (Place p : places) {
            sb.append(p).append(nl);
        }
        return sb.toString();
    }

	// Searches through all of the Places in this Petrinet and finds one with the matching name
	// Returns a new place if none can be found
	public Place getPlace(String name) {
		for (Place p : places) {
			if (p.getName().equalsIgnoreCase(name)) {
            	return p;
			}
		}
		return this.place(name);
	}
	
    public List<Place> getPlaces() {
        return places;
    }

	// Searches through all of the Transitions in this Petrinet and finds one with the matching name
	// Returns a new Transition if none can be found
	public Transition getTransition(String name) {
		for (Transition t : transitions) {
			if (t.getName().equalsIgnoreCase(name)) {
				return t;
			}
		}
		return this.transition(name);
	}
	
    public List<Transition> getTransitions() {
        return transitions;
    }

	// Searches through all of the Arcs in this Petrinet and finds one with the matching name
	// Returns null if none can be found
	public Arc getArc(String name) {
		for (Arc a : arcs) {
			if (a.getName().equalsIgnoreCase(name)) {
				return a;
			}
		}
		return null;
	}

    public List<Arc> getArcs() {
        return arcs;
    }

    public List<InhibitorArc> getInhibitors() {
        return inhibitors;
    }
}
