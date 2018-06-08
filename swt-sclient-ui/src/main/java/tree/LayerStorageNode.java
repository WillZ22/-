package tree;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import Icon.IconAnchor;

public class LayerStorageNode extends AbstractTreeNode {
	 
	protected String title;
	protected String type;
	    
	    /**
	     * 
	     * @param title the title of this LayerStorageNode
	     * @param type the type of the associated storage (mostly the service type).
	     */
	    public LayerStorageNode(String title, String type) {
	        super();
	        
	        this.title = title;
	        this.type = type;
	        
	        setIcon(new ImageIcon(IconAnchor.class.getResource("LAYERSTORAGE.gif")));
	        setText(title);
	    }
	    
	    public List<LayerNode> getLayerNodes(){
	        List<LayerNode> res = new ArrayList<LayerNode>();
	        for(int i=0; i<getChildCount(); i++){
	            LayerNode layerNode = (LayerNode)getChildAt(i);
	            res.add(layerNode);
	        }
	        return res;
	    }

	    public String getTitle() {
	        return title;
	    }
	    
	    public String getType() {
	        return type;
	    }
}
