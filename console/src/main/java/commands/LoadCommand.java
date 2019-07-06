package commands;

import context.ConsoleContext;
import database.loaders.TypeLoad;
import database.managers.LoadManager;
import exception.LoadException;
import org.apache.log4j.Logger;
import strategy.Constants;
import structures.TreeNode;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The class is designed to execute the load command by passing the necessary parameters to the boot manager
 */
public class LoadCommand implements Command {
    private String[] nodePath;
    private TypeLoad typeLoad;
    private LoadManager loadManager;

    public static final Logger LOG = Logger.getLogger(LoadCommand.class);
    private ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);


    public LoadCommand(TypeLoad typeLoad, String nodePath) {
        this.typeLoad = typeLoad;
        this.nodePath = nodePath.split("\\.");
    }

    @Override
    public void executeCommand(ConsoleContext context) {
        LOG.info("LoadCommand");
        TreeNode node = null;

        if (context.getLoadManager() == null) {
            context.setLoadManager(new LoadManager(context.getDatabase()));
        }

        try {
            loadManager = context.getLoadManager();
            node = context.getTreeNode().searchNodeByPath(nodePath);

            loadManager.executeLoad(node, typeLoad);
        } catch (Exception e) {
            LOG.warn(bundle.getString("cantLoad"));
            throw new LoadException(bundle.getString("cantLoad"), e);
        }
        LOG.info(bundle.getString("loadSuccessfully") + node.getNameElement());

    }

    @Override
    public int getPriority() {
        return 2;
    }
}
