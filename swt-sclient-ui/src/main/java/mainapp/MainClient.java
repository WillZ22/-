package mainapp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicTabbedPaneUI.TabbedPaneLayout;

import javafx.scene.control.SplitPane;
import menu.FileMenu;
import menu.HelpMenu;
import menu.ObsMenu;
import menu.PlanMenu;
import tree.ContentTree;
import tree.SensorTree;


public class MainClient extends JFrame {

	protected JPanel topPanel;
	protected JToolBar layerToolBar; 
	
	protected JPanel rightPane;
	protected JTabbedPane leftPane;
	
	protected JPanel leftSensorPane;
	protected JPanel rightLayerPane;
	protected JScrollPane sensorPaneView;
	protected JScrollPane layerPaneView;
	protected ContentTree layerTree;
	protected SensorTree sensorTree;
	
	protected JMenuBar menuBar;
	protected FileMenu fileMenu;
	protected HelpMenu helpMenu;
	protected PlanMenu planMenu;
	protected ObsMenu obsMenu;
	
	
	public MainClient() {
		super("SClient");
		
		setSize(1000, 600);
		setLocation(100, 100);
		setLayout(new BorderLayout());
		
		leftPane = new JTabbedPane();
		rightPane = new JPanel();
		
		//leftPane
		layerTree = new ContentTree();
		sensorTree = new SensorTree();
		//menu
		fileMenu = new FileMenu(this);
		helpMenu = new HelpMenu(this);
		planMenu = new PlanMenu(this, sensorTree);
		obsMenu = new ObsMenu(this, layerTree);
				
		menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(planMenu);
		menuBar.add(obsMenu);
		menuBar.add(helpMenu);

		setJMenuBar(menuBar);

		leftSensorPane = new JPanel();
		rightLayerPane = new JPanel();
		
		sensorPaneView = new JScrollPane(sensorTree);
		layerPaneView = new JScrollPane(layerTree);
		
		leftPane.add("传感器", sensorPaneView);
		leftPane.add("图层", layerPaneView);
		
		Dimension minimumSize = new Dimension(200, 600);
		Dimension minimumSize1 = new Dimension(500, 600);
		leftPane.setMinimumSize(minimumSize);
		rightPane.setMinimumSize(minimumSize1);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPane,rightPane);
		
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//设置全局字体
		Font font = new Font("YaHei Consolas Hybrid", Font.PLAIN, 12);
		UIManager.put("Button.font", font);
		UIManager.put("ToggleButton.font", font);
		UIManager.put("RadioButton.font", font);
		UIManager.put("CheckBox.font", font);
		UIManager.put("ColorChooser.font", font);
		UIManager.put("ToggleButton.font", font);
		UIManager.put("ComboBox.font", font);
		UIManager.put("ComboBoxItem.font", font);
		UIManager.put("InternalFrame.titleFont", font);
		UIManager.put("Label.font", font);
		UIManager.put("List.font", font);
		UIManager.put("MenuBar.font", font);
		UIManager.put("Menu.font", font);
		UIManager.put("MenuItem.font", font);
		UIManager.put("RadioButtonMenuItem.font", font);
		UIManager.put("CheckBoxMenuItem.font", font);
		UIManager.put("PopupMenu.font", font);
		UIManager.put("OptionPane.font", font);
		UIManager.put("Panel.font", font);
		UIManager.put("ProgressBar.font", font);
		UIManager.put("ScrollPane.font", font);
		UIManager.put("Viewport", font);
		UIManager.put("TabbedPane.font", font);
		UIManager.put("TableHeader.font", font);
		UIManager.put("TextField.font", font);
		UIManager.put("PasswordFiled.font", font);
		UIManager.put("TextArea.font", font);
		UIManager.put("TextPane.font", font);
		UIManager.put("EditorPane.font", font);
		UIManager.put("TitledBorder.font", font);
		UIManager.put("ToolBar.font", font);
		UIManager.put("ToolTip.font", font);
		UIManager.put("Tree.font", font);
		
		MainClient client=new MainClient();
		client.dispose();
		client.setVisible(true);
	}
	
	public void setView(int i) {
		switch (i) {
		case 1:
			leftPane.setSelectedComponent(sensorPaneView);
			break;
		case 2:
			leftPane.setSelectedComponent(layerPaneView);
		default:
			break;
		}
	}
	
}
