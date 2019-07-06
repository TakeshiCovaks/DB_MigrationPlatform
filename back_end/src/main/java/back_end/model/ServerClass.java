package back_end.model;

import database.loaders.mysql.TypeMeta;
import database.managers.db_factory.DBFactory;
import database.managers.db_factory.Database;
import org.apache.log4j.Logger;
import structures.TreeNode;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class for saving context with tree and other data for work app.
 */
public class ServerClass {
    private ServerContext context = new ServerContext();
    private static final Logger LOG = Logger.getLogger(ServerClass.class);
    private static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.US);

    public void startReceiver(String commandLine) {

        DBFactory dbFactory = new DBFactory();
        Database database = dbFactory.getDatabase(commandLine);
        Connection connection = database.getConnection();

        if (connection == null) {
            LOG.info("Connection failed");
            throw new RuntimeException(bundle.getString("connectFailed"));
        }
        String nameDB = "";
        try {
            nameDB = database.getConnection().getCatalog();
        } catch (SQLException e) {
            throw new RuntimeException(bundle.getString("connectFailed"), e);
        }
        context.setTreeNode(new TreeNode(nameDB, TypeMeta.DATABASE));
        context.setDatabase(database);
    }

    public ServerContext getContext() {
        return context;
    }
}
