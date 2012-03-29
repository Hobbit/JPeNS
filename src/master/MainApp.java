package master;

import petrinet.logic.Petrinet;

public class MainApp
{
    public static void main(String[] args) {
        Petrinet pn = new Petrinet("MyNet");

		//String filePath = "src/data.xml";
        pn.setFilepath("src/data.xml");
		// At this point our importer should be modifying the existing pn Petrinet object
		Importer importer = new Importer(pn.getFilepath(), pn);


        System.out.println("\nTransitions: " + pn.getTransitions().toString());
        System.out.println("Places: " + pn.getPlaces().toString());
		System.out.println("Arcs: " + pn.getArcs().toString());

        petrinet.gui.PetrinetGUI.displayPetrinet(pn);
    }
}