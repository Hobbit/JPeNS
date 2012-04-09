package petrinet.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NodeForm extends JPanel implements ActionListener {
	public final String types[] = {"", "Place", "Transition", "Arc"};
	private JComboBox type;
	private JTextField nameField;
	private JPanel customInputs;
	private JButton deleteButton;
	
	public NodeForm() {
		this.setLayout(new GridLayout(2, 1));

		JPanel defaultFields = new JPanel(new FlowLayout());
		
		// Add the Name field
		JLabel nameFieldName = new JLabel("Name");
		defaultFields.add(nameFieldName);

		nameField = new JTextField(15);
		defaultFields.add(nameField);
		
		// Add the Type field
		JLabel typeFieldName = new JLabel("Type");
		defaultFields.add(typeFieldName);
		
		type = new JComboBox(types);
		type.addActionListener(this);
		defaultFields.add(type);

		// Add a delete button
		// We create a new action listener since the other one in this class is for the combo box
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// When the delete button is clicked, find the containing panel and delete it
				JButton delButton = (JButton)e.getSource();
				// The parent of the button is defaultFields, it's parent is a NodeForm, it's parent is a NetBuilder
				JPanel parent = (JPanel)delButton.getParent().getParent().getParent();
				parent.remove(delButton.getParent().getParent());
				parent.updateUI();
			} 
		});		
		defaultFields.add(deleteButton);

		this.add(defaultFields);
		
		// Add the custom fields
		customInputs = new JPanel(new GridBagLayout());
		this.add(customInputs);	
	}

	public String getName() {
		return nameField.getText();
	}
	
	public String getType() {
		return (String)type.getSelectedItem();
	}
	
	/**
	 * When the user selects a type of Petri Net object, load the proper fields for the type
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox jBox = (JComboBox)e.getSource();
		String command = (String)jBox.getSelectedItem();

		//Create a GridBagConstraints object for laying out the title and used later for places
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;

		// Add the Place custom fields
		if (command.equals("Place")) {
			customInputs.removeAll();
			
			JLabel tokenFieldName = new JLabel("Number of Tokens:");
			customInputs.add(tokenFieldName, c);
			
			JTextField tokenField = new JTextField(5);
			c.gridx = 1;
			customInputs.add(tokenField, c);
		} 
		// Add the Transition custom fields
		else if (command.equals("Transition")) {
			customInputs.removeAll();
		} 
		// Add the Arc custom fields
		else if (command.equals("Arc")) {
			customInputs.removeAll();
			
			JLabel FromNodeFieldName = new JLabel("Name of 'From' node:");
			customInputs.add(FromNodeFieldName, c);
			
			JTextField fromNodeField = new JTextField(15);
			c.gridx = 1;
			customInputs.add(fromNodeField, c);
			
			c.gridy = 1;
			c.gridx = 0;
			JLabel ToNodeFieldName = new JLabel("Name of 'To' node:");
			customInputs.add(ToNodeFieldName, c);
			
			JTextField toNodeField = new JTextField(15);
			c.gridx = 1;
			customInputs.add(toNodeField, c);			
		} 
		// Empty out the custom fields
		else {
			customInputs.removeAll();
		}
		
		//Refresh the panel
		customInputs.updateUI();
	}	


}

