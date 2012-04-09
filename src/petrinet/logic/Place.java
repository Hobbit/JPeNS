package petrinet.logic;

import master.Config;

import com.mxgraph.view.mxGraph;
import com.mxgraph.model.mxCell;

public class Place 
extends PetrinetObject {

    // it's a magic number....
    public static final int UNLIMITED = -1;
    
    private int tokens = 0;
    private int maxTokens = UNLIMITED;
    

    protected Place(String name, mxGraph graph) {
    	this(name, 0, graph);
    }

    protected Place(String name, int initial, mxGraph graph) {
        super(name);
        this.tokens = initial;
        String style = this.hasAtLeastTokens(1) ? Config.HAS_TOKENS_STYLE : Config.PLACE_STYLE;

        // Add a vertex to the graph
	    graph.getModel().beginUpdate();            
        this.mxcell = (mxCell)graph.insertVertex(graph.getDefaultParent(), null, name, 0, 0, 
        		Config.PLACE_WIDTH, Config.PLACE_HEIGHT, style);
        graph.getModel().endUpdate();
    }

    /**
     * Checks if it the place has at least a certain number of tokens
     * 
     * @param threshold
     * @return boolean
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
        if(this.hasAtLeastTokens(1)){
        	mxcell.setStyle(Config.HAS_TOKENS_STYLE);
        }
        else{
        	mxcell.setStyle(Config.PLACE_STYLE);
        }
    }

    public void removeTokens(int weight) {
        this.tokens -= weight;
        if(this.hasAtLeastTokens(1)){
        	mxcell.setStyle(Config.HAS_TOKENS_STYLE);
        }
        else{
        	mxcell.setStyle(Config.PLACE_STYLE);
        }
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               " Tokens=" + this.tokens +
               " max=" + (hasUnlimitedMaxTokens()? "unlimited" : this.maxTokens);
    }
}
