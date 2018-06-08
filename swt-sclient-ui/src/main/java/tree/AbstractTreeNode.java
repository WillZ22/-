package tree;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

public abstract class AbstractTreeNode extends DefaultMutableTreeNode{

	private Icon icon = null;
    private String text = "";
    private String toolTip = "";

    public AbstractTreeNode() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getText() {
        return text;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getToolTip() {
        return toolTip;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }
}
