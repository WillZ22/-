package sps;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.dlib.gui.FlexLayout;
import org.dlib.gui.TPanel;

public class GetStatuePanel extends TPanel{
	
	public JComboBox task = new JComboBox();
		
		public GetStatuePanel() {
			
			super("GetStatus Parameters");
			
			FlexLayout fl = new FlexLayout(2,2);
			fl.setColProp(1, FlexLayout.EXPAND);
			setLayout(fl);
			
			add("0,0", new JLabel("Sensor Task"));
			
			add("1,0,x", task);
		}
	public void freshItem (ArrayList<String> task) {
		this.task.removeAllItems();
		for (int i = 0; i < task.size(); i++) {
			this.task.addItem(task.get(i));
		}
	}
}
