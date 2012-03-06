import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import petrinet.logic.Petrinet;

class Importer
{
	public Importer(String filepath, Petrinet pn)
	{
		try
		{
			// Open the file via its filepath
	        File file = new File(filepath);
			
			if (file.isFile())
			{
				ParseFile(file, pn);
			}
			else
			{
				throw new IOException();
			}
		}
		
		catch (IOException e)
		{
			System.out.println("There was an error finding the file");
		}
	}

	private void ParseFile(File file, Petrinet pn)
	{
		try 
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			if (file.isFile()) 
			{
			    Document doc = db.parse(file);
			    Element docEle = doc.getDocumentElement();

				// Find the nodes containing the transitions
			    NodeList transitionElements = docEle.getElementsByTagName("transition");
			
			    // Print total transition elements in document
				List<TransitionNode> transitions = GetTransitions(transitionElements);
			    System.out.println("Total transitions: " + transitions.size());

				// Find the node containing the places
				Node placesBlock = docEle.getElementsByTagName("places").item(0);
				
			    NodeList places = placesBlock.getElementsByTagName("place");

			    // Print total place elements in document
			    System.out.println("Total places: " + places.getLength());

				//Find the node containing the arcs
				Node arcsBlock = docEle.getElementsByTagName("arcs").item(0);
				
			    NodeList arcs = arcsBlock.getElementsByTagName("arc");

			    // Print total arc elements in document
			    System.out.println("Total arcs: " + arcs.getLength());
			
				// Find all of the transitions in the data file
			    if (transitions != null && transitions.getLength() > 0) 
				{
			        for (int i = 0; i < transitions.getLength(); i++) 
					{

			            Node node = transitions.item(i);

			            if (node.getNodeType() == Node.ELEMENT_NODE) 
						{
			                System.out.println("=====================\nTransition: ");

			                Element e = (Element) node;
			                NodeList nodeList = e.getElementsByTagName("name");
			                System.out.println("Name: " + nodeList.item(0).getChildNodes().item(0).getNodeValue());
				
							// Add this transition to our Petrinet
							pn.transition(nodeList.item(0).getChildNodes().item(0).getNodeValue());
			            }
			        }
			    } 
				else 
				{
					System.out.println("The importer failed at transitions.\n");
                	System.exit(1);
                }

				// Find all of the places in the data file
			    if (places != null && places.getLength() > 0) 
				{
			        for (int i = 0; i < places.getLength(); i++) 
					{

			            Node node = places.item(i);

			            if (node.getNodeType() == Node.ELEMENT_NODE) 
						{
			                System.out.println("=====================\nPlace: ");

			                Element e = (Element) node;
			
			                NodeList nodeList = e.getElementsByTagName("name");
							String name = nodeList.item(0).getChildNodes().item(0).getNodeValue();
							System.out.println("Name: " + name);
							
			                NodeList tokenList = e.getElementsByTagName("tokens");
							int tokens = Integer.parseInt(nodeList.item(0).getChildNodes().item(0).getNodeValue());
							System.out.println("Tokens: " + tokens);
							
							// Add this place to our Petrinet
							pn.place(name, tokens);
			            }
			        }
			    } 
				else 
				{
					System.out.println("The importer failed at places.\n");
                	System.exit(1);
                }

				// Find all of the arcs in the data file
			    if (arcs != null && arcs.getLength() > 0) 
				{
			        for (int i = 0; i < arcs.getLength(); i++) 
					{

			            Node node = arcs.item(i);

			            if (node.getNodeType() == Node.ELEMENT_NODE) 
						{
			                System.out.println("=====================\nArc: ");

			                Element e = (Element) node;
			
			                NodeList nodeList = e.getElementsByTagName("name");
							String name = nodeList.item(0).getChildNodes().item(0).getNodeValue();
							System.out.println("Name: " + name);
							
			                NodeList fromList = e.getElementsByTagName("from");
						
							// TODO:  Check if we are coming from a transition or a place
							//NodeList placeList = f
							String from = nodeList.item(0).getChildNodes().item(0).getNodeValue();
							System.out.println("From: " + from);

			                NodeList toList = e.getElementsByTagName("to");
							String to = nodeList.item(0).getChildNodes().item(0).getNodeValue();
							System.out.println("To: " + to);
							
							// Add this arc to our Petrinet assuming its from a place to a transition
							pn.arc(name, pn.getPlace(from), pn.getTransition(to));
			            }
			        }
			    } 

				else 
				{
					System.out.println("The importer failed at arcs.\n");
                	System.exit(1);
                }
           	}
        } 
		catch (Exception e) 
		{
	        System.out.println(e);
        }
	}
	
	// Gets the names of all the transitions in the given nodelist
	private static List<TransitionNode> GetTransitions(NodeList nl)
	{
		List<TransitionNode> transitions = new List<String>();
		
		// We have to find each Transition element in this NodeList nl
		if(nl != null && nl.getLength() > 0) 
		{
			for(int i = 0; i < nl.getLength(); i++) 
			{
				// Get the Transition element
				Element el = (Element)nl.item(i);
				
				// Get the name of the transition
				String name = getTextValue(el,"Name");
				TransitionNode node = new TransitionNode(name);
				
				//add it to the list of transition names
				transitions.add(node);
			}
		}
		
		return transitions;
	}
	
	// Gets the places from the given nodelist
	private static List<
}

// Holds all of the properties that a transition has, but without any of the other logic that petrinet.logic.Transition has
class TransitionNode
{
	public String Name;
	
	public TransitionNode(String name)
	{
		Name = name;
	}
}

// Holds all of the properties that a Place has, but without any of the other logic that petrinet.logic.Place has
class PlaceNode
{
	public String Name;
	public int NumTokens;
	
	public PlaceNode(String name, int tokens)
	{
		Name = name;
		NumTokens = tokens;
	}
}

// Holds all of the properties that an Arc has, but without any of the other logic that petrinet.logic.Arc has
class ArcNode
{
	public String Name;
	public String ToName;
	public String ToType;
	public String FromName;
	public String FromType;
	
	public PlaceNode(String name, String toName, String toType, String fromName, String fromType)
	{
		Name = name;
		ToName = toName;
		ToType = toType;
		FromName = fromName;
		FromType = fromType;
	}
}



