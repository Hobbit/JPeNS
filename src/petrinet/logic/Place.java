package petrinet.logic;

import com.mxgraph.view.mxGraph;
import com.mxgraph.model.mxCell;

public class Place 
extends PetrinetObject {

    // it's a magic number....
    public static final int UNLIMITED = -1;
    
    private int tokens = 0;
    private int maxTokens = UNLIMITED;
    

    protected Place(String name, mxGraph graph) {
        super(name);
        
        // Add a vertex to the graph
	    graph.getModel().beginUpdate();            
        this.mxcell = (mxCell)graph.insertVertex(graph.getDefaultParent(), null, name, 0, 0, 
        		PLACE_WIDTH, PLACE_HEIGHT, PLACE_STYLE);
        graph.getModel().endUpdate();
    }

    protected Place(String name, int initial, mxGraph graph) {
        this(name, graph);
        this.tokens = initial;
    }

    /**
     * Checks if it the place has at least a certain number of tokens
     * 
     * @param threshold
     * @return
     */
    public boolean hasAtLeastTokens(int threshold) {
        return (tokens >= threshold);
    }

    /**
     * Checks if the place has room for more tokens
     * 
     * @param newTokens
     * @return
     */
    public boolean maxTokensReached(int newTokens) {
        if (hasUnlimitedMaxTokens()) {
            return false;
        }
        
        return (tokens+newTokens > maxTokens);
    }

    private boolean hasUnlimitedMaxTokens() {
        return maxTokens == UNLIMITED;
    }

    
    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public void setMaxTokens(int max) {
        this.maxTokens = max;
    }

    public void addTokens(int weight) {
        this.tokens += weight;
    }

    public void removeTokens(int weight) {
        this.tokens -= weight;
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               " Tokens=" + this.tokens +
               " max=" + (hasUnlimitedMaxTokens()? "unlimited" : this.maxTokens);
    }
}
