package sos;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.dlib.gui.FlexLayout;
import org.dlib.gui.TPanel;

import sps.MainFrame;
import tree.ContentTree;
import tree.SensorTree;

public class GetObeservationPanel extends TPanel {

	public JComboBox procedure = new JComboBox();
	public JComboBox offering = new JComboBox();
	public JComboBox timePosition =  new JComboBox();
	
	public GetObeservationPanel() {
		
		super("GetObeservation Parameters");
		FlexLayout fl = new FlexLayout(2,2);
		fl.setColProp(1, FlexLayout.EXPAND);
		setLayout(fl);
		
		add("0,0", new JLabel("Procedure"));
		add("1,0,x", procedure );
		
	}
	public void freshItem (ArrayList<String> procedure) {
		
		this.procedure.removeAllItems();
		for (int i = 0; i < procedure.size(); i++) {
			this.procedure.addItem(procedure.get(i));
		}
	}
}
