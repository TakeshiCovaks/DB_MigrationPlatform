package back_end.services;

import back_end.model.ServerContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import structures.TreeNode;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class Search {


    public JSONArray executeSearch(JSONObject keyValueForSearch, HttpServletRequest request){

        ServerContext context = ManagerApp.getContextFromSession(request);

        if (keyValueForSearch.isEmpty() || context == null) {
            return new JSONArray();
        }

        TreeNode root = context.getTreeNode();
        HashMap<String, String> keyValAttr = (HashMap) keyValueForSearch;

        String keyAttr = keyValAttr.get("key");
        String valueAttr = keyValAttr.get("value");

        Predicate<TreeNode> predicateForSearch = node -> {
            String attrValue = node.getAttributes().get(keyAttr);
            return attrValue != null && attrValue.equals(valueAttr);
        };

        ArrayList<TreeNode> foundNodes = root.searchWidth(predicateForSearch);
        JSONArray resultArr = new JSONArray();
        foundNodes.stream().forEach(searchNode -> resultArr.add(searchNode.getPath()));

        return resultArr;
    }
}
