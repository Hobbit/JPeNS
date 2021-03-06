package petrinet.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NodeForm extends JPanel implements ActionListener {
	private final String types[] = {"", "Place", "Transition", "Arc"};
	private final String arcDirections[] = {"Place --> Transition", "Transition --> Place"};
	private JComboBox type;
	private JTextField nameField;
	private JPanel customInputs;
	private JButton deleteButton;
	private JTextField tokenField;
	private JTextField fromNodeField;
	private JTextField toNodeField;
	private JComboBox arcDirectionField;
	
	public NodeForm() {
		this.setLayout(new GridLayout(2, 1));
		this.setBorder(BorderFactory.createLineBorder(Color.black));

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
			
			tokenField = new JTextField("0", 5);
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
			
			JLabel arcDirection = new JLabel("Direction of arc:");
			customInputs.add(arcDirection, c);
			
			c.gridx = 1;
			arcDirectionField = new JComboBox(arcDirections);
			customInputs.add(arcDirectionField, c);
			
			c.gridy = 1;
			c.gridx = 0;
			JLabel FromNodeFieldName = new JLabel("Name of 'From' node:");
			customInputs.add(FromNodeFieldName, c);
			
			c.gridx = 1;
			fromNodeField = new JTextField(15);
			customInputs.add(fromNodeField, c);
			
			c.gridy = 2;
			c.gridx = 0;
			JLabel ToNodeFieldName = new JLabel("Name of 'To' node:");
			customInputs.add(ToNodeFieldName, c);
			
			c.gridx = 1;
			toNodeField = new JTextField(15);
			customInputs.add(toNodeField, c);						
		} 
		// Empty out the custom fields
		else {
			customInputs.removeAll();
		}
		
		//Refresh the panel
		customInputs.updateUI();
	}	

	public String getName() {
		return nameField.getText();
	}
	
	public String getType() {
		return (String)type.getSelectedItem();
	}
	
	public int getTokens() {
		String val = tokenField.getText();
		try {
			return Integer.parseInt(val);
		}
		catch (Exception e) {
			return 0;
		}
	}
	
	public String getFromType() {
		if (((String)arcDirectionField.getSelectedItem()).equals("Place --> Transition")) {
			return "place";
		}
		else {
			return "transition";
		}
	}
	
	public String getToType() {
		if (((String)arcDirectionField.getSelectedItem()).equals("Place --> Transition")) {
			return "transition";
		}
		else {
			return "place";
		}
	}
	
	public String getFromName() {
		return fromNodeField.getText();
	}
	
	public String getToName() {
		return toNodeField.getText();
	}
}

