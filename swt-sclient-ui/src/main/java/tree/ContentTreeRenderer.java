package tree;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

public class ContentTreeRenderer implements TreeCellRenderer{

	
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		// TODO Auto-generated method stub
		if (value != null && value instanceof AbstractTreeNode) {
			return renderDefualtNode(tree, (AbstractTreeNode)value, selected, expanded, leaf, row, hasFocus);
		}
		else if (value != null && value instanceof LayerNode) {
			//TODO layernode的样式
		}
		return null;
	}

	private DefaultTreeCellRenderer renderDefualtNode (JTree tree, AbstractTreeNode node, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		DefaultTreeCellRenderer defaultRenderer = new DefaultTreeCellRenderer();
        
        defaultRenderer.getTreeCellRendererComponent(tree,
                                                     node,
                                                     selected,
                                                     expanded,
                                                     leaf,
                                                     row,
                                                     hasFocus);
		        
        defaultRenderer.setIcon(node.getIcon());
        defaultRenderer.setToolTipText(node.getToolTip());
        defaultRenderer.setText(node.getText());
		        
        return defaultRenderer;
		
	}
	
}
