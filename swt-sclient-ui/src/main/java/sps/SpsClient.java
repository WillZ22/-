package sps;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import java.security.acl.Owner;
import java.util.ArrayList;

import javax.naming.CommunicationException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.dlib.gui.FlexLayout;
import org.dlib.gui.MultiPanel;
import org.dlib.gui.TPanel;
import org.dom4j.DocumentException;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.PortableInterceptor.SUCCESSFUL;

import tree.ContentTree;
import tree.SensorTree;

public class SpsClient extends MainFrame implements ActionListener, ItemListener {
	
	private TPanel serverPanel;
	private TPanel requestPanel;
	public MultiPanel paramsPanel;
	private JPanel communicatePanel;
	private JPanel listPanel;
	
	private JTextField host =  new JTextField("localhost", 15);
	private JTextField port = new JTextField("8080", 4);
	private JTextField addr = new JTextField("52n-sps/sps");
	private JButton btnSend = new JButton("发送");
	private JButton btnConnect = new JButton("连接");
	private JButton btnAddToMa = new JButton("添加到图层");
	private JTextArea txtLog = new JTextArea(20,80);
	private JList list = new JList();
	private JComboBox cmbOperat  = new JComboBox();
	private JComboBox cmbMethod  = new JComboBox();
	
	public DescribeSensorPanel describeSensorPanel = new DescribeSensorPanel();
	public SubmitPanel submitPanel = new SubmitPanel();
	public GetStatuePanel getStatuePanel = new GetStatuePanel();
	public GetAccessPanel getAccessPanel = new GetAccessPanel();
	
	private enum Operation {DescribeSensor, Submit, GetStatus, GetResultAccess}

	private SpsOperation spsOperation;
	
	
	
	
	public SpsClient(ContentTree contentTree, SensorTree sensorTree) {
		super(contentTree, sensorTree);
		
		
//	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("SPS query");
		
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
		serverPanel.add("0,2", new JLabel("SPS service"));

		serverPanel.add("1,0,x", host);
		serverPanel.add("1,1,x", port);
		serverPanel.add("1,2,x", addr);

		return serverPanel;
	}

	private MultiPanel buildMultipanel() {
		paramsPanel.add(Operation.DescribeSensor.toString(), describeSensorPanel);
		paramsPanel.add(Operation.Submit.toString(), submitPanel);
		paramsPanel.add(Operation.GetStatus.toString(), getStatuePanel);
		paramsPanel.add(Operation.GetResultAccess.toString(), getAccessPanel);
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
				System.out.println(spsUrl);
				spsOperation = new SpsOperation(spsUrl, this);
				clearLog();
				spsOperation.getCapbilities();
				
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		else if (e.getSource().equals(btnSend)) {
			
			try{
				if ((Operation)cmbOperat.getSelectedItem() == Operation.DescribeSensor) {
						clearLog();
						spsOperation.describeSensor();	
				}
				else if ((Operation)cmbOperat.getSelectedItem() == Operation.Submit) {
						clearLog();
						spsOperation.submit();
				}
				else if ((Operation)cmbOperat.getSelectedItem() == Operation.GetStatus) {
					clearLog();
					spsOperation.getStatus();
					
				}
				else if ((Operation)cmbOperat.getSelectedItem() == Operation.GetResultAccess) {
					clearLog();
					spsOperation.getAccess();
				}
			}
			
			catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
		SpsClient spsClient = new SpsClient(new ContentTree(), new SensorTree());

	}
}
