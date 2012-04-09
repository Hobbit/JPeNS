package petrinet.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NodeForm extends JPanel implements ActionListener {
	public final String types[] = {"", "Place", "Transition", "Arc"};
	private String name = null;
	private JComboBox type;
	private JTextField nameField;
	private JPanel customInputs;
	
	public NodeForm() {
		nameField = new JTextField("Name");
		this.add(nameField);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		type = new JComboBox(types);
		type.addActionListener(this);
		this.add(type);
		
		customInputs = new JPanel();
		customInputs.setLayout(new GridBagLayout());
		this.add(customInputs);
	}

	public String getName() {
		return this.name;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox jBox = (JComboBox)e.getSource();
		String command = (String)jBox.getSelectedItem();

		//Create a GridBagConstraints object for laying out the title and used later for places
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
//		c.ipadx = 10;
//		c.ipady = 10;

		if (command.equals("Place")) {
			customInputs.removeAll();
			
			JLabel tokenFieldName = new JLabel("Number of Tokens:");
			customInputs.add(tokenFieldName, c);
			
			JTextField tokenField = new JTextField();
			tokenField.setColumns(5);
			c.gridx = 1;
			customInputs.add(tokenField, c);
		} 
		else if (command.equals("Transition")) {
			customInputs.removeAll();
		} 
		else if (command.equals("Arc")) {
			customInputs.removeAll();
			
			JLabel FromNodeFieldName = new JLabel("Name of 'From' node:");
			customInputs.add(FromNodeFieldName, c);
			
			JTextField fromNodeField = new JTextField();
			fromNodeField.setColumns(25);
			c.gridx = 1;
			customInputs.add(fromNodeField, c);
			
			c.gridy = 1;
			c.gridx = 0;
			JLabel ToNodeFieldName = new JLabel("Name of 'To' node:");
			customInputs.add(ToNodeFieldName, c);
			
			JTextField toNodeField = new JTextField();
			toNodeField.setColumns(25);
			c.gridx = 1;
			customInputs.add(toNodeField);			
		} 
		else {
			customInputs.removeAll();
		}
		
		//Refresh the panel
		customInputs.updateUI();
	}	


}

