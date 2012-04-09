package master;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import petrinet.gui.NodeForm;

@SuppressWarnings("serial")
public class NetBuilder extends JFrame implements ActionListener {
	//static JFrame window = null;
	ArrayList<NodeForm> nodeForms = new ArrayList<NodeForm>();
	JPanel nodesPanel = new JPanel();
	JButton addFormButton = new JButton("Add");
	
	public NetBuilder(String name){
		super(name);
		this.setLayout(new BorderLayout());
		
		Container contentPane = getContentPane();
		
		nodesPanel.setLayout(new BoxLayout(nodesPanel, BoxLayout.Y_AXIS));
		contentPane.add(nodesPanel, BorderLayout.CENTER);
		
		//Add an initial form
		InsertForm();

		addFormButton.addActionListener(this);
		contentPane.add(addFormButton, BorderLayout.SOUTH);
}
	
	// A JPanel that contains the inputs needed for one node in the network
	private void InsertForm() {
		NodeForm form = new NodeForm();
		
		// Add the new form to our list of forms
		nodeForms.add(form);
		
		// Add the new form to the panel
		nodesPanel.add(form);
		nodesPanel.updateUI();
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		InsertForm();
	}
}
