package back_end.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import structures.TreeNode;

public class Converter {

    /**
     * Method for convert node to JSON.
     *
     * @param node node for convert
     * @return JSON structure.
     */
    public static JSONObject convertTreeToJson(TreeNode node) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("label", node.getNameElement());
        jsonObject.put("value", node.getPath());
        jsonObject.put("checked", "false");
        jsonObject.put("expanded", "false");
        jsonObject.put("title", node.getData());

        JSONObject attributes = new JSONObject();
        node.getAttributes().forEach((key, value) -> attributes.put(key, value));
        jsonObject.put("attributes", attributes);

        JSONArray jsonArray = new JSONArray();
        node.getListChild().stream().forEach(curnode -> jsonArray.add(convertTreeToJson(curnode)));
        jsonObject.put("children", jsonArray);
        return jsonObject;
    }
}
