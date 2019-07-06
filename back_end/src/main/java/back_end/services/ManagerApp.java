package back_end.services;

import back_end.model.ServerClass;
import back_end.model.ServerContext;
import org.json.simple.JSONObject;
import structures.TreeNode;

import javax.servlet.http.HttpServletRequest;

public class ManagerApp {

    /**
     * Method for first connecting to the database and storing the information
     * received in the structure for further access from it.
     *
     * @param state state with data from connection.
     * @return hashCode as ID
     */
    public static ServerContext firstConnection(JSONObject state) {

        ServerClass serverClass = new ServerClass();
        String command = getCommandFromForm(state);

        serverClass.startReceiver(command);
        ServerContext context = serverClass.getContext();
        return context;
    }

    /**
     * Method create String command from from with data.
     *
     * @param state from with input parameters as url, name database, user, etc. for create connection command.
     * @return command for execute in startReceiver.
     */
    private static String getCommandFromForm(JSONObject state) {

        StringBuilder connectionCommand = new StringBuilder();
        connectionCommand
                .append(state.get("typeDb")).append(" ")
                .append(state.get("url")).append(":")
                .append(state.get("port")).append("/")
                .append(state.get("nameDb")).append(" ")
                .append(state.get("user")).append(" ")
                .append(state.get("pass"));

        return connectionCommand.toString();
    }

    public static TreeNode getTreeFromSession(HttpServletRequest request) {
        TreeNode tree = null;
        ServerContext context = (ServerContext) request.getSession().getAttribute("context");
        if (context != null) {
            tree = context.getTreeNode();
        }
        return tree;
    }

    public static ServerContext getContextFromSession(HttpServletRequest request) {
        ServerContext context = (ServerContext) request.getSession().getAttribute("context");
        return context;
    }
}
