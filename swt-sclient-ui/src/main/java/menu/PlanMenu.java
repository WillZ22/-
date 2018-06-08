package menu;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import sps.SpsClient;
import tree.ContentTree;
import tree.SensorTree;

public class PlanMenu extends JMenu implements ActionListener{

	private JFrame owner;
	
	private JMenuItem getCapbilities;
	private JMenuItem plan;
	private SensorTree sensorTree;
	
	
	public PlanMenu(JFrame owner, SensorTree senTree){
		
		super("规划");
		this.sensorTree = senTree;
		this.owner = owner;
		
		getCapbilities = new JMenuItem("连接SPS(GetCapbilities)");
		plan = new JMenuItem("规划");

		
		getCapbilities.addActionListener(this);//把this添加为改组件的监听器，在this中实现监听方法
		plan.addActionListener(this);
		
		this.add(getCapbilities);
		add(plan);
		//add(taskList);
	}
	
	public void actionPerformed(ActionEvent e) {
		//DefaultMutableTreeNode node = (DefaultMutableTreeNode) sensorTree.getLastSelectedPathComponent();
		//if (node == null) {
		//	JOptionPane.showMessageDialog(owner, "请选择传感器", "Error", JOptionPane.ERROR_MESSAGE);
		//}
		//else {
			if (e.getSource().equals(getCapbilities)) {
			}
			else if (e.getSource().equals(plan)) {
				SpsClient spsClient = new SpsClient(new ContentTree(), sensorTree);			
			}
	//	}

		// TODO Auto-generated method stub
		
	}
	
	

}
