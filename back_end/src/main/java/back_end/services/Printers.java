package back_end.services;

import back_end.model.ServerContext;
import database.managers.PrinterManager;
import structures.TreeNode;

import javax.servlet.http.HttpServletRequest;

public class Printers {

    public String createDdl(String pathNode, HttpServletRequest request){

        ServerContext context = ManagerApp.getContextFromSession(request);
        if (pathNode.isEmpty() || context == null) {
            return "";
        }
        PrinterManager printerManager = context.getPrinterManager();
        if(printerManager == null) {
            printerManager = new PrinterManager(context.getDatabase());
            context.setPrinterManager(printerManager);
        }
        TreeNode root = context.getTreeNode();
        TreeNode node = root.searchNodeByPath(pathNode.split("\\."));

        return printerManager.executePrinter(node);
    }
}
