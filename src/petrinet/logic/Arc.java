package petrinet.logic;

import petrinet.logic.ArcEndType.EndType;

/**
 * An edge goes from one place to a transition or vice versa.
 * Overloaded constructors determine if it is a 'to' or 'from' arc
 */
public class Arc
extends PetrinetObject {

    Place place;
    Transition transition;
    Direction direction;
    public EndType from;
    public EndType to;
    int weight = 1;
    
    enum Direction {
        
        /**
         * The two directions, which may have such an edge
         */
        
        PLACE_TO_TRANSITION {
            @Override
            public boolean canFire(Place p, int weight) {
                return p.hasAtLeastTokens(weight);
            }

            @Override
            public void fire(Place p, int weight) {
                p.removeTokens(weight);
            }

        },
        
        TRANSITION_TO_PLACE {
            @Override
            public boolean canFire(Place p, int weight) {
                return ! p.maxTokensReached(weight);
            }

            @Override
            public void fire(Place p, int weight) {
                p.addTokens(weight);
            }

        };

        public abstract boolean canFire(Place p, int weight);

        public abstract void fire(Place p, int weight);
    }
    
    private Arc(String name, Direction d, Place p, Transition t) {
        super(name);
        this.direction = d;
        this.place = p;
        this.transition = t;
        if (d == Direction.PLACE_TO_TRANSITION) {
            this.from = EndType.PLACE;
            this.to = EndType.TRANSITION;
        }
        else if (d == Direction.TRANSITION_TO_PLACE) {
            this.from = EndType.TRANSITION;
            this.to = EndType.PLACE;
        }
        else {
        	System.out.println("The Direction " + d.toString() + " is invalid for an Arc.");
        	System.exit(1);
        }
    }

    protected Arc(String name, Place p, Transition t) {
        this(name, Direction.PLACE_TO_TRANSITION, p, t);
        t.addIncoming(this);
        this.from = EndType.PLACE;
        this.to = EndType.TRANSITION;
    }

    protected Arc(String name, Transition t, Place p) {
        this(name, Direction.TRANSITION_TO_PLACE, p, t);
        t.addOutgoing(this);
        this.from = EndType.TRANSITION;
        this.to = EndType.PLACE;
    }

    public boolean canFire() {
        return direction.canFire(place, weight);
    }
    
    public void fire() {
        this.direction.fire(place, this.weight);
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    public int getWeight() {
        return weight;
    }

	public Place getPlace()
	{
		return place;
	}
	
	public Transition getTransition()
	{
		return transition;
	}
}
