package master;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import petrinet.logic.Petrinet;
import petrinet.logic.Arc;

public class Importer
{
	private final boolean PRINT_DIAG_INFO = false;

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
				NodeList placesElements = docEle.getElementsByTagName("place");

			    // Print total place elements in document
				List<PlaceNode> places = GetPlaces(placesElements);
			    System.out.println("Total places: " + places.size());

				//Find the node containing the arcs
				NodeList arcsElements = docEle.getElementsByTagName("arc");

			    // Print total arc elements in document
				List<ArcNode> arcs = GetArcs(arcsElements);
			    System.out.println("Total arcs: " + arcs.size());

				// Find all of the transitions in the data file
			    if (transitions != null && transitions.size() > 0) 
				{
					for (TransitionNode tran : transitions)
					{
						if (PRINT_DIAG_INFO)
						{
							System.out.println("=====================\nTransition: ");
			                System.out.println("Name: " + tran.Name);
						}
						// Add this transition to our Petrinet
						pn.addTransition(tran.Name);
					}
			    } 
				else 
				{
					System.out.println("The importer failed at transitions.\n");
                	System.exit(1);
                }

				// Find all of the places in the data file
			    if (places != null && places.size() > 0) 
				{
			        for (PlaceNode place : places) 
					{
						if (PRINT_DIAG_INFO)
						{
			                System.out.println("=====================\nPlace: ");
							System.out.println("Name: " + place.Name);
							System.out.println("Tokens: " + place.Tokens);
						}
						// Add this place to our Petrinet
						pn.addPlace(place.Name, place.Tokens);
			        }
			    } 
				else 
				{
					System.out.println("The importer failed at places.\n");
                	System.exit(1);
                }

				// Find all of the arcs in the data file
			    if (arcs != null && arcs.size() > 0) 
				{
			        for (ArcNode arc : arcs) 
					{
						if (arc.FromType.equalsIgnoreCase("place") && arc.ToType.equalsIgnoreCase("transition"))
						{
							// Add this arc to our Petrinet coming from a place to a transition
							pn.addArc(arc.Name, pn.getPlace(arc.FromName), pn.getTransition(arc.ToName));							
						}
						else if (arc.FromType.equalsIgnoreCase("transition") && arc.ToType.equalsIgnoreCase("place"))
						{
							// Add this arc to our Petrinet coming from a transition to a place
							pn.addArc(arc.Name, pn.getTransition(arc.FromName), pn.getPlace(arc.ToName));
						}
						else
                        {
                            System.out.println("This arc " + arc.Name + " is invalid. It must go from a place -> transition or transition -> place.");
                            System.exit(1);
                        }

						if (PRINT_DIAG_INFO)
						{
			                System.out.println("=====================\nArc: ");
							System.out.println("Name: " + arc.Name);
							System.out.println("From: " + arc.FromName);
							System.out.println("To: " + arc.ToName);

							Arc diag = pn.getArc(arc.Name);
							if (diag != null)
							{
								System.out.println("The Arc says it's place is " + diag.getPlace().getName() + " and its transition is " + diag.getTransition().getName() + ".");
							}
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
		List<TransitionNode> transitions = new ArrayList<TransitionNode>();

		// We have to find each Transition element in this NodeList nl
		if(nl != null && nl.getLength() > 0) 
		{
			for(int i = 0; i < nl.getLength(); i++) 
			{
				// Get the Transition element
				Element el = (Element)nl.item(i);

				// Get the name of the transition
				String name = NodeReader.getTextValue(el, "name");
				TransitionNode node = new TransitionNode(name);

				//add it to the list of transition names
				transitions.add(node);
			}
		}

		return transitions;
	}

	// Gets the places from the given nodelist
	private static List<PlaceNode> GetPlaces(NodeList nl)
	{
		List<PlaceNode> places = new ArrayList<PlaceNode>();

		// We have to find each Place element in this NodeList nl
		if(nl != null && nl.getLength() > 0) 
		{
			for(int i = 0; i < nl.getLength(); i++) 
			{
				// Get the Place element
				Element el = (Element)nl.item(i);

				// Get the name of the place
				String name = NodeReader.getTextValue(el, "name");

				// Get the number of tokens
				int tokens = 0;
				String tokenVal = NodeReader.getTextValue(el, "tokens");
				if (tokenVal != null)
				{
					tokens = Integer.parseInt(tokenVal);
				}
				PlaceNode node = new PlaceNode(name, tokens);

				//add it to the list of places 
				places.add(node);
			}
		}

		return places;
	}

	// Gets the arcs from the given nodelist
	private static List<ArcNode> GetArcs(NodeList nl)
	{
		List<ArcNode> arcs = new ArrayList<ArcNode>();

		// We have to find each arc element in this NodeList nl
		if(nl != null && nl.getLength() > 0) 
		{
			for(int i = 0; i < nl.getLength(); i++) 
			{
				// Get the arc element
				Element el = (Element)nl.item(i);

				// Get the name of the arc
				String name = NodeReader.getTextValue(el, "name");

				// Get the FromType
				String fromNode = NodeReader.getTextValue(el, "from");
				String fromType = NodeReader.getNodeAttribute(el, "from", "type");

				//System.out.printf("\nWe just processed a node that's an arc with fromType = %s\n\n", fromType);


				// Get the ToType
				String toNode = NodeReader.getTextValue(el, "to");
				String toType = NodeReader.getNodeAttribute(el, "to", "type");

				ArcNode node = new ArcNode(name, fromNode, fromType, toNode, toType);

				//add it to the list of arcs 
				arcs.add(node);
			}
		}

		return arcs;
	}
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
	public int Tokens;

	public PlaceNode(String name, int tokens)
	{
		Name = name;
		Tokens = tokens;
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

	public ArcNode(String name, String fromName, String fromType, String toName, String toType)
	{
		Name = name;
		ToName = toName;
		ToType = toType;
		FromName = fromName;
		FromType = fromType;
	}
}

// Given an element, it searches for text within the element
class NodeReader
{
    // Given an XML element, searches for the first node with tagName as its name
    // then returns the text inside that node
	public static String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

    // Given an XML element, searches for the first node with tagName as its name
    // then returns the attName attribute value of that node
    public static String getNodeAttribute(Element ele, String tagName, String attName) {
        String attVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element)nl.item(0);
            attVal = el.getAttribute(attName);
        }

        return attVal;
    }
}