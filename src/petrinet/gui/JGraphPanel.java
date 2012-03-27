package petrinet.gui;
import javax.swing.JPanel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

/**
 * First examples of JGraphX - Creating a simple frame that
 * contains a graph component with two vertices and an edge connecting them.
 * @author vainolo
 */
public class JGraphPanel  extends JPanel {
 
  private static final long serialVersionUID = 196831535599934813L;
 
  private mxGraph graph;
  
  public mxGraphComponent graphPanel;
  
  public JGraphPanel(mxGraph aGraph) {
    graph = aGraph;
//    Object defaultParent = graph.getDefaultParent();
//    graph.getModel().beginUpdate();
//    Object v1 = graph.insertVertex(defaultParent, null, "Hello", 20, 20, 80, 30);
//    Object v2 = graph.insertVertex(defaultParent, null, "World", 240, 150, 80, 30);
//    graph.insertEdge(defaultParent, null, "Edge", v1, v2);
//    graph.getModel().endUpdate();
    graphPanel = new mxGraphComponent(graph);
    //getContentPane().add(graphComponent);
  }
}