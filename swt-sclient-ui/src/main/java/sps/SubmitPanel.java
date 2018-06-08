package sps;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.dlib.gui.FlexLayout;
import org.dlib.gui.TPanel;

public class SubmitPanel extends TPanel{

	public JComboBox frequence = new JComboBox();
	public JComboBox count = new JComboBox();
	public JComboBox procedure = new JComboBox();
	
	public SubmitPanel() {

	super("Submit parameters");
	
	FlexLayout fl = new FlexLayout(2,3);
	fl.setColProp(1, FlexLayout.EXPAND);
	setLayout(fl);
	
	for (int i = 1; i <= 10.; i++) {
		frequence.addItem(i);
		count.addItem(i);
	}
	
	add("0,0", new JLabel("Measurement Frequency"));
	add("0,1", new JLabel("Measurement Count"));
	add("0,2", new JLabel("Procedure"));
	
	add("1,0,x", frequence);
	add("1,1,x", count);
	add("1,2,x", procedure);
	
	}
	
	public void freshItem(ArrayList<String> sprocedure) {
		procedure.removeAllItems();
		for (int i = 0; i < sprocedure.size(); i++) {
			procedure.addItem(sprocedure.get(i));
		}
	}
}
