package structures;

import structures.TreeNode;

import java.util.HashMap;

public class Methods {

    public static TreeNode createExpectedNode() {

        TreeNode node = new TreeNode("project", "");
        TreeNode nodeComponent1 = new TreeNode("component", "1.55");
        TreeNode nodeEntry = new TreeNode("entry", "text");
        TreeNode nodeComponent2 = new TreeNode("component", "");

        node.addChild(nodeComponent1);
        node.addChild(nodeComponent2);
        nodeComponent1.addChild(nodeEntry);

        HashMap<String, String> attr = new HashMap<>();
        attr.put("version", "4");
        node.setAttributes(attr);

        HashMap<String, String> attrComponent1 = new HashMap<>();
        attrComponent1.put("name", "editorHistoryManager");
        nodeComponent1.setAttributes(attrComponent1);

        HashMap<String, String> attrEntry = new HashMap<>();
        attrEntry.put("file", "file://$PROJECT_DIR$/src/TreeNode.java");
        nodeEntry.setAttributes(attrEntry);

        HashMap<String, String> attrComponent2 = new HashMap<>();
        attrComponent2.put("name", "masterDetails");
        nodeComponent2.setAttributes(attrComponent2);

        return node;
    }

}
