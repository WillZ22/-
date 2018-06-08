package tree;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class ContentTree extends JTree {

	protected RootNode root;
	protected DefaultTreeModel model;
	
	/*
	 * |-rootnode
	 *   |-layerstoragenode
     *     |-layernode
	*/
	
	public ContentTree() {
		super();
		
		root = new RootNode();
		model = new DefaultTreeModel(root);
		TreeSelectionModel treeSelectionModel = new DefaultTreeSelectionModel();
		treeSelectionModel.setSelectionMode(treeSelectionModel.SINGLE_TREE_SELECTION);
		setModel(model);
		setSelectionModel(treeSelectionModel);
		setEditable(false);
		setShowsRootHandles(false);
		setCellRenderer(new ContentTreeRenderer());
		/*TODO 新类
		 * setCellRenderer();
		 * setCellEditor();
		 */
		
	}
	
	public void addLayerNode(LayerNode node) {
		//TODO
	}
	
	public void addLayerStorageNode(LayerStorageNode node) {
		//TODO
	}
	
	public LayerStorageNode getLayerStorageNode(String title, String type) {
		//TODO
		return null;
	}
	
    public int positionOf(LayerNode layerNode) {
		//TODO返回图层节点的位置
    	return (Integer) null;
    }
	
	//TODO getSelectedLayer
	//TODO eventCaught？
	
	public void removeNode(DefaultMutableTreeNode node) {
		TreeNode parent = node.getParent();
        TreeNode[] path = model.getPathToRoot(parent);
        model.removeNodeFromParent(node);
        setSelectionPath(new TreePath(path));//设置删除后选中
	}
	
    public void clearTree() {
        while (root.getChildCount() > 0) {
            removeNode((LayerStorageNode) root.getFirstLeaf());//循环删除第一个子节点
        }
    }
	

}
