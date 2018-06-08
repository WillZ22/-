package tree;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import javafx.scene.Node;

public class SensorTree extends JTree implements MouseListener {

	private DefaultMutableTreeNode root;
	private DefaultTreeModel treeModel;
	
	public SensorTree() {
		root = new DefaultMutableTreeNode("传感器");
		initTreeModel(root);	
		setModel(treeModel);
	}
	
	private void initTreeModel(DefaultMutableTreeNode root) {
		treeModel = new DefaultTreeModel(root);
		//TODO 暂时这样写树
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("交通");
		DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("Taxi");
		node1.add(node2);
		root.add(node1);
	}
	//test
	public static void main(String[] args) {
		JFrame frame = new JFrame("ss");
		SensorTree sensorTree  = new SensorTree();
		JScrollPane jScrollPane = new JScrollPane(sensorTree);
		
		jScrollPane.setToolTipText("ss");
		jScrollPane.setAutoscrolls(true);
		
		frame.setSize(200, 500);
		frame.add(jScrollPane);
		frame.setVisible(true);
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
