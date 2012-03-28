package petrinet.gui;
import java.util.ArrayList;

import javax.swing.JPanel;

import petrinet.logic.Arc;
import petrinet.logic.Petrinet;
import petrinet.logic.Place;

import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

/**
 * First examples of JGraphX - Creating a simple frame that
 * contains a graph component with two vertices and an edge connecting them.
 * @author vainolo
 */
public class JGraphPanel  extends JPanel {
 
  private static final long serialVersionUID = 196831535599934813L;
 
  public mxGraph graph;
  
  public mxGraphComponent graphComponent;
  
  final int VERTEX_WIDTH = 80;
  final int VERTEX_HEIGHT = 30;
  private ArrayList<Object> places = new ArrayList<Object>();
  private ArrayList<Object> arcs = new ArrayList<Object>();
    
  public JGraphPanel(Petrinet pn) {
	graph = new mxGraph();
	
    Object defaultParent = graph.getDefaultParent();
    graph.getModel().beginUpdate();
//    Object v1 = graph.insertVertex(defaultParent, null, "Hello", 0, 0, 80, 30);
//    Object v2 = graph.insertVertex(defaultParent, null, "World", 0, 0, 80, 30);
//    graph.insertEdge(defaultParent, null, "Edge", v1, v2);

    // For each place in the petrinet, add a vertex
    for (Place p : pn.getPlaces()) {
    	Object vertex = graph.insertVertex(defaultParent, null, p.getName(), 0, 0, VERTEX_WIDTH, VERTEX_HEIGHT);
    	places.add(vertex);
    }
        
    // For each arc, find the place it is going to and from, then make edges.
    
    graph.getModel().endUpdate();
    graphComponent = new mxGraphComponent(graph);
  }
}