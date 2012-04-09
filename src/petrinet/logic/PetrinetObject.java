package petrinet.logic;

import com.mxgraph.model.mxCell;

public class PetrinetObject {
	
	protected mxCell mxcell; 
	
    private String name;
    
    public PetrinetObject(String name) {
        super();      	
        this.name = name;
        mxcell = new mxCell();
    }
    
    public mxCell getMxCell() {
    	return mxcell;
    }
    
    public void setMxCell(mxCell newCell) {
    	this.mxcell = newCell;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return /*getClass().getSimpleName() + " " + */ name;
    }
}
