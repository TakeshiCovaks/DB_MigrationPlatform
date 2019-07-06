package back_end.services;

import back_end.utils.Converter;
import back_end.model.ServerContext;
import database.loaders.TypeLoad;
import database.managers.LoadManager;
import org.json.simple.JSONObject;
import structures.TreeNode;

import java.util.ArrayList;

public class Loaders {

    /**
     * Method to populate the node depending on the type of load.
     *
     * @param keyPathNode path to the node that will be loaded.
     * @param typeLoad    type load for node (example TypeLoad.FULL, TypeLoad.LAZY etc)
     * @return
     */
    public JSONObject load(ArrayList<String> keyPathNode, TypeLoad typeLoad, ServerContext context) {
        if (keyPathNode.isEmpty() || context == null) {
            return null;
        }

        TreeNode root = context.getTreeNode();
        LoadManager loadManager = context.getLoadManager();
        if(loadManager == null) {
            loadManager = new LoadManager(context.getDatabase());
            context.setLoadManager(loadManager);
        }

        for (int i = 0; i < keyPathNode.size(); i++) {
            String nameNode = keyPathNode.get(i);
            TreeNode node = root.searchNodeByPath(nameNode.split("\\."));

            loadManager.executeLoad(node, typeLoad);
        }
        return Converter.convertTreeToJson(root);
    }
}
