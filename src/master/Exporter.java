package master;

import java.io.File;
import java.util.ArrayList;

import petrinet.gui.NodeForm;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Exporter {
	private ArrayList<NodeForm> placeNodes = new ArrayList<NodeForm>();
	private ArrayList<NodeForm> transNodes = new ArrayList<NodeForm>();
	private ArrayList<NodeForm> arcNodes = new ArrayList<NodeForm>();
	
	public Exporter(ArrayList<NodeForm> nodes) {
		// Find all the various types of nodes that were passed in and organize them
		for (NodeForm node : nodes) {
			if (node.getType().equals("Place")) {
				placeNodes.add(node);
			}
			else if (node.getType().equals("Arc")) {
				arcNodes.add(node);
			}
			else if (node.getType().equals("Transition")) {
				transNodes.add(node);
			}
			else {
				// do nothing...
			}
		}
		
	}
	
	public void Export(File file) {
		try {
			 
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root element
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("petrinet");
			doc.appendChild(rootElement);
	 
			// transition elements
			for (NodeForm node : transNodes) {
				Element transition = doc.createElement("transition");
				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(node.getName()));
				transition.appendChild(name);
				rootElement.appendChild(transition);
			}
	 
			// place elements
			for (NodeForm node : placeNodes) {
				Element place = doc.createElement("place");
				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(node.getName()));
				place.appendChild(name);
				rootElement.appendChild(doc.createTextNode(Integer.toString(node.getTokens())));
			}
			
			// arc elements
			for (NodeForm node : arcNodes) {
				Element arc = doc.createElement("place");
				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(node.getName()));
				arc.appendChild(name);
				rootElement.appendChild(doc.createTextNode(Integer.toString(node.getTokens())));
			}
			
			Element arc = doc.createElement("Arc");
			rootElement.appendChild(arc);

			// set attribute to Arc element
			Attr attr = doc.createAttribute("");
			attr.setValue("1");
			arc.setAttributeNode(attr);
	 
			// shorten way
			// staff.setAttribute("id", "1");
	 
			// firstname elements
			Element firstname = doc.createElement("firstname");
			firstname.appendChild(doc.createTextNode("yong"));
			//transition.appendChild(firstname);
	 	 
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
	 
			System.out.println("File saved!");
	 
		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
	}
}
