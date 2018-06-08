package sps;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.File;

import javax.imageio.plugins.jpeg.JPEGQTable;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.dlib.gui.FlexLayout;
import org.dlib.gui.MultiPanel;
import org.dlib.gui.TPanel;

import tree.ContentTree;
import tree.SensorTree;


public abstract class MainFrame extends JFrame {
	
	/*
	protected JPanel serverPanel;
	
	protected JPanel requestPanel;
	
	protected MultiPanel paramsPanel;
	
	protected JTable list;
	
	private JButton btnSend = new JButton("Send");
	private JTextArea txaLog = new JTextArea(40,100);
*/
    protected ContentTree contentTree;
    protected SensorTree sensorTree;
    //protected map map;
	
    public MainFrame(/*map map,*/ ContentTree contentTree, SensorTree sensorTree) {
    	this.contentTree = contentTree;
    	this.sensorTree = sensorTree;
    	//this.map = map;
    }

}
