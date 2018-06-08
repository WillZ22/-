package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HelpMenu extends JMenu implements ActionListener {

	private JMenuItem aboutItem;
	private JFrame owner;

	public HelpMenu(JFrame owner) {
		super("帮助");
		this.owner = owner;
		
		aboutItem = new JMenuItem("关于");
		
		aboutItem.addActionListener(this);
		
		add(aboutItem);
	}
	
	public void actionPerformed(ActionEvent actionEvent) {
		Object message=getMessagePanel();
		JOptionPane.showMessageDialog(owner,message,"平台信息",JOptionPane.PLAIN_MESSAGE);
	}
	
	private JPanel getMessagePanel() {
		JLabel jLabel01 = null;
		JLabel jLabel02 = null;
		JPanel MessagePanel=new JPanel();
		
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.ipady = 10;
		gridBagConstraints1.gridy = 1;
		jLabel02 = new JLabel();
		jLabel02.setText(" ");
		jLabel02.setFont(new Font("YaHei Consolas Hybrid", Font.PLAIN, 12));
		jLabel02.setForeground(Color.black);
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.ipady = 10;
		gridBagConstraints.gridy = 0;
		jLabel01 = new JLabel();
		jLabel01.setText("传感网信息服务平台");
		jLabel01.setFont(new Font("YaHei Consolas Hybrid", Font.PLAIN, 12));
		jLabel01.setForeground(Color.black);
		
		MessagePanel.setLayout(new GridBagLayout());
		MessagePanel.add(jLabel01, gridBagConstraints);
		MessagePanel.add(jLabel02, gridBagConstraints1);
		return MessagePanel;
	}
}
