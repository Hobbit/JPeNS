package petrinet.logic;

/**
 * An edge goes from one place to a transition or vice versa.
 * Overloaded constructors determine if it is a 'to' or 'from' arc
 */
public class Arc
extends PetrinetObject {

    Place place;
    Transition transition;
    Direction direction;
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
    }

    protected Arc(String name, Place p, Transition t) {
        this(name, Direction.PLACE_TO_TRANSITION, p, t);
        t.addIncoming(this);
    }

    protected Arc(String name, Transition t, Place p) {
        this(name, Direction.TRANSITION_TO_PLACE, p, t);
        t.addOutgoing(this);
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
