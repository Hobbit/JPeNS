package petrinet.logic;

public class InhibitorArc
extends Arc {

    
    protected InhibitorArc(String name, Place p, Transition t) {
        super(name, p, t);
    }

    /**
     * It may only be fired if fewer tokens are in the place than the weight of the inhibiter
     */
    
    @Override
    public boolean canFire() {
        return (place.getTokens() < this.getWeight());
    }
    
    /**
     * The firing of an inhibiter edge does not do anything
     */
    @Override
    public void fire() {
        // do nothing
    }

    
}
