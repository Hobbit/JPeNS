package petrinet.gui;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class NodeForm extends JPanel {
	public final String types[] = {"Place", "Transition", "Arc"};
	private JComboBox type = null;
	
	public NodeForm() {
		type = new JComboBox(types);
		this.add(type);
	}	
}
