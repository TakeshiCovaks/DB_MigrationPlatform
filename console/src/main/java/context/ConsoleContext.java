package context;

import database.managers.LoadManager;
import database.managers.PrinterManager;
import database.managers.db_factory.Database;
import structures.TreeNode;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A class context containing the root node of the tree the console works with.
 */
public class ConsoleContext {
    TreeNode treeNode;
    private Database database;
    private PrinterManager printerManager;
    private LoadManager loadManager;

    private ResourceBundle bundle = ResourceBundle.getBundle(strategy.Constants.MESSAGES_FILE, Locale.US);


    public TreeNode getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(TreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public PrinterManager getPrinterManager() {
        return printerManager;
    }

    public void setPrinterManager(PrinterManager printerManager) {
        this.printerManager = printerManager;
    }

    public LoadManager getLoadManager() {
        return loadManager;
    }

    public void setLoadManager(LoadManager loadManager) {
        this.loadManager = loadManager;
    }
}
