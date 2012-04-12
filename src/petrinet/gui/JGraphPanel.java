package petrinet.gui;

import javax.swing.JPanel;

import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

@SuppressWarnings("serial")

/**
 * Creates an MXGraph which can then be used by a layout mechanism in the calling class
 */
public class JGraphPanel extends JPanel {
		
	public mxGraph graph;
	public mxGraphComponent graphComponent;
	      
	private final Object defaultParent;
	
	public JGraphPanel(mxGraph graph) {
		this.graph = graph;
	    defaultParent = graph.getDefaultParent();
	    graph.setAllowDanglingEdges(false);
	    graph.setCellsEditable(false);

	    DrawGraph();
	}
	
	/**
	 * Draws the graph from the petrinet
	 */
	private void DrawGraph() {
	    graphComponent = new mxGraphComponent(graph);
	    graphComponent.setConnectable(false);
	    
	    mxOrganicLayout organic = new mxOrganicLayout(graph);
	    organic.setAverageNodeArea(40000);
	    organic.execute(defaultParent);
	}
	
	public void RefreshGraph() {
		graphComponent.refresh();
	}
}