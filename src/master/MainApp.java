package master;

import petrinet.logic.Petrinet;

public class MainApp
{
    public static void main(String[] args) {
        Petrinet pn = new Petrinet("My Net");

		//larger.xml is the default petrinet file to load on start
        pn.setFilepath("src/larger.xml");
		// At this point our importer should be modifying the existing pn Petrinet object
		
        Importer.Import(pn.getFilepath(), pn);	
        
        petrinet.gui.PetrinetGUI.displayPetrinet(pn);
    }
}