import petrinet.logic.Petrinet;

public class MainApp
{
    public static void main(String[] args) {
        Petrinet pn = new Petrinet("MyNet");

		String filePath = "src/data.xml";
		// At this point our importer should be modifying the existing pn Petrinet object
		Importer importer = new Importer(filePath, pn);

        // Transition t1 = pn.transition("t1");
        // Transition t2 = pn.transition("t2");
        // 
        // Place p1 = pn.place("p1", 1);
        // Place p2 = pn.place("p2");
        // 
        // Arc a1 = pn.arc("a1", p1, t1);
        // Arc a2 = pn.arc("a2", t1, p2);
        // Arc a3 = pn.arc("a3", p2, t2);
        // Arc a4 = pn.arc("a4", t2, p1);

        System.out.println("\nTransitions: " + pn.getTransitions().toString());
        System.out.println("Places: " + pn.getPlaces().toString());
		System.out.println("Arcs: " + pn.getArcs().toString());

        petrinet.gui.PetrinetGUI.displayPetrinet(pn);
    }
}
