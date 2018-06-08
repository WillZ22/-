package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import sos.SosClient;
import tree.ContentTree;
import tree.SensorTree;

public class ObsMenu extends JMenu implements ActionListener{

	private JFrame owner;
	
	private ContentTree contentTreel;
	
	private JMenuItem getCap;
	private JMenuItem obs;
	
	public ObsMenu(JFrame owner, ContentTree contentTree) {
		
		super("观测");
		
		this.owner = owner;
		this.contentTreel = contentTree;
		
		getCap = new JMenuItem("连接SOS(GetCapbilities)");
		obs = new JMenuItem("观测");

		
		getCap.addActionListener(this);
		obs.addActionListener(this);
		
		add(getCap);
		add(obs);

	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(obs)) {
			SosClient sosClient = new SosClient(contentTreel, new SensorTree());
		}
		else if (e.getSource().equals(getCap)) {
			// TODO Auto-generated method stub
		}
	}

}
