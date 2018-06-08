package tree;

import javax.swing.ImageIcon;

import Icon.IconAnchor;

public class RootNode extends AbstractTreeNode {

	public RootNode() {
		super();
		
		setIcon(new ImageIcon(IconAnchor.class.getResource("ROOT.gif")));
		
		setText("图层管理");
		
	}
}
