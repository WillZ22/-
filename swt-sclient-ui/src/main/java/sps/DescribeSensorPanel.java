package sps;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.dlib.gui.FlexLayout;
import org.dlib.gui.TPanel;

public class DescribeSensorPanel extends TPanel {

	public JComboBox procedure = new JComboBox();
	
	public DescribeSensorPanel() {
		
		super("DescribeSensor Parameters");
		
		FlexLayout fl = new FlexLayout(2,2);
		fl.setColProp(1, FlexLayout.EXPAND);
		setLayout(fl);
		
		add("0,0", new JLabel("Sensor Procedure"));
		
		add("1,0,x", procedure);
	}
	public void freshItem (ArrayList<String> procedure) {
		this.procedure.removeAllItems();
		for (int i = 0; i < procedure.size(); i++) {
			this.procedure.addItem(procedure.get(i));
		}
	}
}
