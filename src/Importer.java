import java.io.File;
import java.io.IOException;
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

			    // Print root element of the document
			    System.out.println("Root element of the document: " + docEle.getNodeName());

			    NodeList transitions = docEle.getElementsByTagName("transition");

			    // Print total transition elements in document
			    System.out.println("Total transitions: " + transitions.getLength());

			    NodeList places = docEle.getElementsByTagName("place");

			    // Print total place elements in document
			    System.out.println("Total places: " + places.getLength());

			    NodeList arcs = docEle.getElementsByTagName("arc");

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
		
		// BufferedReader reader = new BufferedReader(file);
		// 
		// String str = "";
		// while ((str = file.readLine()) != null)
		// {
		// 	// Add the word to our list of words
		// 	Words.add(str.trim());
		// }
	}
}