package back_end.services;

import back_end.utils.Converter;
import back_end.model.ServerContext;
import database.managers.db_factory.TypeDatabase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import structures.TreeNode;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class Authorization {

    public JSONArray getTypeDb(){
        JSONArray arrayTypeDb = new JSONArray();
        Arrays.stream(TypeDatabase.values()).forEach(currentType -> arrayTypeDb.add(currentType));

        return arrayTypeDb;
    }

    public JSONObject validateEnter(JSONObject state, HttpSession httpSession) {

        TreeNode root = null;
        ServerContext context = ManagerApp.firstConnection(state);
        httpSession.setAttribute("context", context);
        if(context != null){
            root = context.getTreeNode();
        }
        return Converter.convertTreeToJson(root);
    }
}
