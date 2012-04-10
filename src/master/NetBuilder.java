package master;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
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
	final String ADD_NODE = "Add";
	final String EXPORT_FORM = "Export";
	
	/**
	 * Builds a frame that allows a user to create new nodes for a petrinet graph
	 * @param name what to title the frame
	 */
	public NetBuilder(String name){
		super(name);
		this.setLayout(new BorderLayout());
		
		Container contentPane = getContentPane();
		
		nodesPanel.setLayout(new BoxLayout(nodesPanel, BoxLayout.Y_AXIS));
		contentPane.add(nodesPanel, BorderLayout.CENTER);
		
		//Add an initial form
		InsertForm();

		// A panel to hold the controller buttons
		JPanel adminButtons = new JPanel(new FlowLayout());
		
		JButton addFormButton = new JButton(ADD_NODE);
		addFormButton.addActionListener(this);
		adminButtons.add(addFormButton);
		
		JButton export = new JButton("Export");
		export.addActionListener(this);
		adminButtons.add(export);
		
		contentPane.add(adminButtons, BorderLayout.SOUTH);
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
		String command = e.getActionCommand();
		if (command.equals(ADD_NODE)) {
			InsertForm();
		}
		else if (command.equals(EXPORT_FORM)) {
			JFileChooser chooser = new JFileChooser("Where would you like to save your file?");
			int returnVal = chooser.showSaveDialog(this);
			
			// If the user selected a file, lets export to it
			if (returnVal == JFileChooser.APPROVE_OPTION){
			
				Exporter ex = new Exporter(nodeForms);
				ex.Export(chooser.getSelectedFile());
				this.setVisible(false);
			}
		}
	}
}
