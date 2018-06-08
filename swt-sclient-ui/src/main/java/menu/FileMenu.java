package menu;

import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileMenu extends JMenu implements ActionListener{
	private JMenuItem openItem;
	private JMenuItem quitItem;
	private JFrame owner;
	
	public FileMenu(JFrame owner) {
		super("文件");
		this.owner = owner;
		
		openItem=new JMenuItem("打开");
		quitItem=new JMenuItem("退出");
		
		openItem.addActionListener(this);
		quitItem.addActionListener(this);
		
		add(openItem);
		add(quitItem);
	}

	public void actionPerformed(ActionEvent actionEvent) {
		if(actionEvent.getSource().equals(openItem)) {
			JFileChooser jfc=new JFileChooser();
			FileNameExtensionFilter filter=new FileNameExtensionFilter(".project", "txt");
			jfc.setFileFilter(filter);
			jfc.showOpenDialog(owner);
			
		} else if(actionEvent.getSource().equals(quitItem)) {
			System.exit(0);
		}
	}
}
