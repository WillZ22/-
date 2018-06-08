package sos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.dlib.gui.FlexLayout;
import org.dlib.gui.MultiPanel;
import org.dlib.gui.TPanel;
import org.dom4j.DocumentException;

import sps.MainFrame;
import tree.ContentTree;
import tree.SensorTree;

public class SosClient extends MainFrame implements ItemListener,ActionListener {

	private TPanel serverPanel;
	private TPanel requestPanel;
	public MultiPanel paramsPanel;
	private JPanel communicatePanel;
	private JPanel listPanel;
	
	private JTextField host =  new JTextField("localhost", 15);
	private JTextField port = new JTextField("8080", 4);
	private JTextField addr = new JTextField("52n-sos-webapp/service");
	private JButton btnSend = new JButton("发送");
	private JButton btnConnect = new JButton("连接");
	private JButton btnAddToMa = new JButton("添加到图层");
	private JTextArea txtLog = new JTextArea(20,80);
	private JList list = new JList();
	private JComboBox cmbOperat  = new JComboBox();
	private JComboBox cmbMethod  = new JComboBox();
	
	public GetObeservationPanel getObeservationPanel  = new GetObeservationPanel();
	

	private enum Operation {GetObservation, DescribeSensor, InsertObservation}

	private SosOperation sosOperation;
	
	public SosClient(ContentTree contentTree, SensorTree sensorTree) {
		
		super(contentTree, sensorTree);

		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("SOS query");
		
		serverPanel = new TPanel("Server");
		requestPanel = new TPanel("Request");
		paramsPanel = new MultiPanel();
		
		
		for(Operation oper : Operation.values()) {
			cmbOperat.addItem(oper);
		}
		
		cmbMethod.addItem("POST");
		//cmbMethod.addItem("GET");
		
		btnSend.addActionListener(this);
		btnConnect.addActionListener(this);
		btnAddToMa.addActionListener(this);
		
		getContentPane().add(buildLeftPanel(), BorderLayout.WEST);
		getContentPane().add(buildRightPanel(),  BorderLayout.CENTER);
		
		pack();
		setVisible(true);
		
		cmbOperat.addItemListener(this);
	}
	//---------------------------builder--------------------------------------
	private JPanel buildLeftPanel() {
		JPanel panel = new JPanel();

		FlexLayout flexL = new FlexLayout(1,5);
		flexL.setColProp(0, FlexLayout.EXPAND);
		flexL.setRowProp(4, FlexLayout.EXPAND);
		panel.setLayout(flexL);

		panel.add("0,0,x", buildServerPanel());
		panel.add("0,1,x", btnConnect);
		panel.add("0,2,x", buildRequestPanel());
		panel.add("0,3,x", btnSend);
		panel.add("0,4,x,x", buildMultipanel());

		return panel;

	}

	private JPanel buildRightPanel() {
		JPanel p = new TPanel("Communication log");

		FlexLayout flexL = new FlexLayout(1,2);
		flexL.setColProp(0, FlexLayout.EXPAND);
		flexL.setRowProp(0, FlexLayout.EXPAND);
		p.setLayout(flexL);
		
		Dimension minimumSize = new Dimension(20, 80);
		txtLog.setSize(minimumSize);
		txtLog.setEditable(false);
		
		p.add("0,0,x,x", new JScrollPane(txtLog));
		p.add("0,1,x,x", new JScrollPane(list));
		return p;
	}

	private TPanel buildRequestPanel () {
		
		FlexLayout flexL = new FlexLayout(2,3);
		flexL.setColProp(1, FlexLayout.EXPAND);
		requestPanel.setLayout(flexL);

		requestPanel.add("0,0", new JLabel("Operation"));
		requestPanel.add("0,1", new JLabel("Method"));

		requestPanel.add("1,0,x", cmbOperat);
		requestPanel.add("1,1,x", cmbMethod);

		return requestPanel;
	}
	
	private TPanel buildServerPanel() {
		
		FlexLayout flexL = new FlexLayout(2,3);
		flexL.setColProp(1, FlexLayout.EXPAND);
		serverPanel.setLayout(flexL);

		serverPanel.add("0,0", new JLabel("Host"));
		serverPanel.add("0,1", new JLabel("Port"));
		serverPanel.add("0,2", new JLabel("SOS service"));

		serverPanel.add("1,0,x", host);
		serverPanel.add("1,1,x", port);
		serverPanel.add("1,2,x", addr);

		return serverPanel;
	}

	private MultiPanel buildMultipanel() {
		paramsPanel.add(Operation.GetObservation.toString(), getObeservationPanel);
		return paramsPanel;
	}
	
	//--------------------------API---------------------------
	
	public void setTextField (String response) {
		txtLog.setText(null);
		txtLog.setText(response);
	}
	
	
	//--------------------------Action-------------------------
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(btnConnect)) {
			
			try {	
				String spsUrl = "http://" +host.getText() + ":" + port.getText() + "/" + addr.getText();  
				sosOperation = new SosOperation(spsUrl, this);
				clearLog();
				sosOperation.getCapbilities();
				
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		else if (e.getSource().equals(btnSend)) {
			if ((Operation)cmbOperat.getSelectedItem() == Operation.GetObservation) {
				clearLog();
				try {
					sosOperation.getObservation();
				} catch (DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
			if ((Operation)cmbOperat.getSelectedItem() == Operation.DescribeSensor) {
				txtLog.setText("未支持该操作");
			}
			if ((Operation)cmbOperat.getSelectedItem() == Operation.InsertObservation) {
				txtLog.setText("未支持该操作");
			}
		}	
	}

	public void itemStateChanged(ItemEvent arg0) {
		paramsPanel.show(cmbOperat.getSelectedItem().toString());// TODO Auto-generated method stub	
	}
	
	private void clearLog()
	{
		txtLog.setText("");
	}
	//----------------------------test----------------------------------
	public static void main(String[] args) {
		SosClient spsClient = new SosClient(new ContentTree(), new SensorTree());

	}
}
